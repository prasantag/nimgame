package com.game.nimgam.service;

import com.game.nimgam.core.GameInstance;
import com.game.nimgam.core.exceptions.InsufficientMatchesException;
import com.game.nimgam.core.exceptions.InvalidMatchesRemovalException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class ComputerPlayerTaskExecutor {
    private final ComputerPlayer computerPlayer;

    @Async
    public CompletableFuture<String> executeComputerTurn(GameInstance gameInstance) throws InvalidMatchesRemovalException, InsufficientMatchesException {
        CompletableFuture<String> completableFuture = new CompletableFuture<>();
        try {
            computerPlayer.takesTurn(gameInstance);
            completableFuture.complete("SUCCESS");
        } catch (Exception ex) {
            log.error("Failed to execute computer's turn, game id: {}", gameInstance.getGameId());
            completableFuture.complete("FAILED");
        }
        return completableFuture;
    }
}
