package com.game.nimgam.service;

import com.game.nimgam.constants.Player;
import com.game.nimgam.core.GameInstance;
import com.game.nimgam.core.exceptions.InsufficientMatchesException;
import com.game.nimgam.core.exceptions.InvalidMatchesRemovalException;
import com.game.nimgam.model.GameRequestDTO;
import com.game.nimgam.model.GameResponseDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;

@Service
@Slf4j
@RequiredArgsConstructor
public class GameExecutor {
    private final ComputerPlayerTaskExecutor computerPlayerTaskExecutor;
    private final GameDataHandler gameDataHandler;

    public GameResponseDTO processHumanAction(GameRequestDTO gameRequest) throws InvalidMatchesRemovalException, InsufficientMatchesException {
        //Process human action
        log.debug("game id:{}", gameRequest.getGameId());
        gameRequest.getPlayerAction().setActionBy(Player.Human);
        GameInstance gameInstance = gameDataHandler.get(gameRequest.getGameId());
        gameInstance.setCurrentPlayer(Player.Human);
        gameInstance = processAction(gameRequest);
        if(gameInstance.isGameOver()) {
            gameInstance.declareWinner(Player.Computer);
            return buildGameResponse(gameInstance);
        }

        //Over to Computer for action
        CompletableFuture<String> completableFuture = computerPlayerTaskExecutor.executeComputerTurn(gameInstance);
        try {
            String result = completableFuture.get();
            if("FAILED".equals(result)) {
                throw new RuntimeException("Failed in Computer's turn.");
            }
        } catch (InterruptedException | ExecutionException e) {
            log.error("Error occurred while executing computer's turn for game id:{}", gameInstance.getGameId());
            throw new RuntimeException(e);
        }

        return buildGameResponse(gameInstance);
    }

    private GameInstance processAction(final GameRequestDTO gameRequest) throws InvalidMatchesRemovalException, InsufficientMatchesException {
        //Game gameObj = gameRepository.findById(gameRequest.getGameId()).get();
        GameInstance gameInstance = gameDataHandler.get(gameRequest.getGameId());
        gameInstance.validateAction(gameRequest.getPlayerAction().getMatchesRemoved());
        gameInstance.removeMatches(gameRequest.getPlayerAction().getMatchesRemoved());
        gameInstance.addAction(gameRequest.getPlayerAction());
        return gameDataHandler.update(gameInstance);
    }

    private GameResponseDTO buildGameResponse(GameInstance gameInstance) {
        GameResponseDTO.GameResponseDTOBuilder responseDTOBuilder = GameResponseDTO.builder()
                .winner(gameInstance.getWinner())
                .playerAction(gameInstance.getLastAction())
                .computerActed(gameInstance.getCurrentPlayer() == Player.Computer)
                .gameId(gameInstance.getGameId());
        return responseDTOBuilder.build();
    }
}
