package com.game.nimgam.service;

import com.game.nimgam.config.GameConfiguration;
import com.game.nimgam.core.GameInstance;
import com.game.nimgam.entity.Action;
import com.game.nimgam.entity.Game;
import com.game.nimgam.entity.NimBoard;
import com.game.nimgam.model.ActionItem;
import com.game.nimgam.model.GameResponseDTO;
import com.game.nimgam.repository.GameRepository;
import com.game.nimgam.util.GameUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class GameDataHandler {
    private final GameRepository gameRepository;
    private final GameConfiguration gameConfiguration;
    private final GameUtil gameUtil;

    public GameResponseDTO createGame(Long boardId) {
        Game gameEntity = gameRepository.save(Game.builder().numberOfMatches(gameConfiguration.getMatches())
                .matchesRemovalOptions(gameConfiguration.getMatchesRemovalOptions())
                .created(Instant.now())
                .nimBoard(NimBoard.builder().id(boardId).build()).build());
        GameInstance gameInstance = new GameInstance(gameEntity.getId(),
                gameUtil.getMatchesRemovalOptionSet(gameEntity.getMatchesRemovalOptions()));
        gameInstance.init(gameConfiguration.getMatches());
        update(gameInstance);
        return GameResponseDTO.builder()
                .gameId(gameInstance.getGameId())
                .matchesRemovalAllowedSet(gameInstance.getMatchesRemovalSetOfOptions())
                .initialMatches(gameInstance.getMatches())
                .build();
    }

    @Cacheable(value = "nimGames")
    public GameInstance get(Long gameId) {
        return entityToGameInstance(getGameEntity(gameId)
                .orElseThrow(() -> new IllegalStateException("car not found")));
    }

    @CachePut(value = "nimGames", key = "#gameInstance.gameId")
    public GameInstance update(GameInstance gameInstance) {
        Optional<Game> gameEntityOptional = getGameEntity(gameInstance.getGameId());
        if (gameEntityOptional.isPresent()) {
            gameEntityOptional.get().setNumberOfMatches(gameInstance.getMatches());
            gameEntityOptional.get().setUpdated(Instant.now());
            gameEntityOptional.get().setWinner(gameInstance.getWinner());
            gameRepository.save(gameEntityOptional.get());
            return gameInstance;
        }
        throw new IllegalArgumentException("A game must have an existing id to update");
    }

    private Optional<Game> getGameEntity(Long gameId) {
        return gameRepository.findById(gameId);
    }

    private GameInstance entityToGameInstance(Game gameEntity) {
        return GameInstance.builder().gameId(gameEntity.getId())
                        .matches(gameEntity.getNumberOfMatches()).matchesRemovalSetOfOptions(
                gameUtil.getMatchesRemovalOptionSet(gameEntity.getMatchesRemovalOptions()))
                .actions(entityToActions(gameEntity.getActions()))
                .winner(gameEntity.getWinner()).build();
    }

    private List<ActionItem> entityToActions(List<Action> actions) {
        return actions.stream().map(one -> {
            return ActionItem.builder().actionBy(one.getActionBy()).matchesBeforeAction(one.getMatchesBeforeAction())
                    .matchesAfterAction(one.getMatchesAfterAction()).matchesRemoved(one.getMatchesRemoved()).build();
        }).collect(Collectors.toList());
    }


}
