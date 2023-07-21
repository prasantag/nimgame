package com.game.nimgam.service;

import com.game.nimgam.core.exceptions.InvalidMatchesRemovalException;
import com.game.nimgam.entity.Game;
import com.game.nimgam.entity.NimBoard;
import com.game.nimgam.model.GameRequestDTO;
import com.game.nimgam.model.GameResponseDTO;
import com.game.nimgam.repository.GameRepository;
import com.game.nimgam.util.GameUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameService {
    @Value("${game.matches.initial:13}")
    private Integer matches;

    @Value("${game.matches.removal.allowed:1,2,3}")
    private String matchesRemovals;

    private Set<Integer> matchesRemovalAllowedSet;

    private final ComputerPlayer computerPlayer;
    private final GameRepository gameRepository;
    private final GameUtil gameUtil;
    public GameResponseDTO createGame(Long boardId) {
        Game gameObj = gameRepository.save(Game.builder().numberOfMatches(matches)
                .created(Instant.now())
                .nimBoard(NimBoard.builder().id(boardId).build()).build());
        return GameResponseDTO.builder()
                .gameId(gameObj.getId())
                .matchesRemovalAllowedSet(getMatchesRemovals())
                .initialMatches(matches)
                .build();
    }

    private Set<Integer> getMatchesRemovals() {
        if(matchesRemovalAllowedSet != null) return matchesRemovalAllowedSet;
        matchesRemovalAllowedSet = Arrays.stream(matchesRemovals.split(",")).map(s -> Integer.valueOf(s)).collect(Collectors.toSet());
        return matchesRemovalAllowedSet;
    }

    public GameResponseDTO processHumanAction(GameRequestDTO gameRequest) throws InvalidMatchesRemovalException {
        log.info("game id:{}", gameRequest.getGameId());
        Game gameEntity = processAction(gameRequest);
        GameResponseDTO.GameResponseDTOBuilder responseDTOBuilder = GameResponseDTO.builder().humanAction(
                gameRequest.getPlayerAction())
                .gameId(gameRequest.getGameId());
        if(gameEntity.getNumberOfMatches() == 0) {
            responseDTOBuilder.winner("Computer");
            return responseDTOBuilder.build();
        }
        //Computer action

        GameRequestDTO computerRequestDTO = computerPlayer.generateActionAsComputer(gameEntity, getMatchesRemovals());
        gameEntity = processAction(computerRequestDTO);
        if(gameEntity.getNumberOfMatches() == 0) {
            responseDTOBuilder.winner("Human");
        }
        responseDTOBuilder.computerActed(Boolean.TRUE).computerAction(computerRequestDTO.getPlayerAction());
        return responseDTOBuilder.build();
    }

    private Game processAction(final GameRequestDTO gameRequest) throws InvalidMatchesRemovalException {
        Game gameObj = gameRepository.findById(gameRequest.getGameId()).get();
        validateAction(gameObj.getNumberOfMatches(), gameRequest.getPlayerAction().getMatchesRemoved());
        Integer matchesAfterAction = gameObj.getNumberOfMatches() - gameRequest.getPlayerAction().getMatchesRemoved();
        gameObj.setNumberOfMatches(matchesAfterAction);
        return gameRepository.save(gameObj);
    }

    private void validateAction(Integer numberOfMatches, Integer matchesRemoved) throws InvalidMatchesRemovalException {
        //validate game rules, allowed matches removal
        if(gameUtil.matchesRemovalEntries(matchesRemovalAllowedSet, matchesRemoved) != 1) throw new InvalidMatchesRemovalException("Not a valid match removal");
        if(numberOfMatches < matchesRemoved) throw new InvalidMatchesRemovalException("Removed matches exceed available matches");
    }

    public GameResponseDTO getGame(Long gameId) {
        Game gameEntity = gameRepository.findById(gameId).get();
        log.debug("game id: {}", gameEntity.getId());
        return GameResponseDTO.builder().gameId(gameEntity.getId()).build();
    }
}
