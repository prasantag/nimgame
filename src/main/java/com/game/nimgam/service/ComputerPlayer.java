package com.game.nimgam.service;

import com.game.nimgam.constants.Player;
import com.game.nimgam.core.GameInstance;
import com.game.nimgam.core.exceptions.InsufficientMatchesException;
import com.game.nimgam.core.exceptions.InvalidMatchesRemovalException;
import com.game.nimgam.model.ActionItem;
import com.game.nimgam.model.GameRequestDTO;
import com.game.nimgam.util.GameUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ComputerPlayer {
    private final GameUtil gameUtil;
    private final GameDataHandler gameDataHandler;
    public GameRequestDTO generateActionAsComputer(GameInstance gameInstance) {
        Integer removeMatches = gameUtil.generateValidRandomNumber(gameInstance.getMatchesRemovalSetOfOptions(), gameInstance.matchesLeft());
        return GameRequestDTO.builder().gameId(gameInstance.getGameId())
                .playerAction(ActionItem.builder().matchesRemoved(removeMatches).actionBy(Player.Computer)
                        .matchesBeforeAction(gameInstance.matchesLeft()).matchesAfterAction(gameInstance.matchesLeft() - removeMatches).build())
                .build();
    }

    public void takesTurn(GameInstance gameInstance) throws InvalidMatchesRemovalException, InsufficientMatchesException {
        processComputerAction(generateActionAsComputer(gameInstance));
    }

    public void processComputerAction(GameRequestDTO gameRequest) throws InvalidMatchesRemovalException, InsufficientMatchesException {
        GameInstance gameInstance = gameDataHandler.get(gameRequest.getGameId());
        gameInstance.setCurrentPlayer(Player.Computer);
        gameInstance = processAction(gameRequest);
        if(gameInstance.isGameOver()) {
            gameInstance.declareWinner(Player.Human);
        }
        return;
    }

    private GameInstance processAction(final GameRequestDTO gameRequest) throws InvalidMatchesRemovalException, InsufficientMatchesException {
        GameInstance gameInstance = gameDataHandler.get(gameRequest.getGameId());
        gameInstance.validateAction(gameRequest.getPlayerAction().getMatchesRemoved());
        gameInstance.removeMatches(gameRequest.getPlayerAction().getMatchesRemoved());
        gameInstance.addAction(gameRequest.getPlayerAction());
        return gameDataHandler.update(gameInstance);
    }
}
