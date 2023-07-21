package com.game.nimgam.service;

import com.game.nimgam.entity.Game;
import com.game.nimgam.model.Action;
import com.game.nimgam.model.GameRequestDTO;
import com.game.nimgam.util.GameUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
@RequiredArgsConstructor
public class ComputerPlayer {
    private final GameUtil gameUtil;
    public GameRequestDTO generateActionAsComputer(Game gameObj, Set<Integer> matchesRemovalAllowed) {
        Integer remainingMatches = gameObj.getNumberOfMatches();
        Integer removeMatches = gameUtil.generateValidRandomNumber(matchesRemovalAllowed, remainingMatches);
        return GameRequestDTO.builder().gameId(gameObj.getId())
                .playerAction(Action.builder().matchesRemoved(removeMatches)
                        .matchesBeforeAction(remainingMatches).matchesAfterAction(remainingMatches - removeMatches).build())
                .build();
    }

}
