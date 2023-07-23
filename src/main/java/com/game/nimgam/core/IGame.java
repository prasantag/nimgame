package com.game.nimgam.core;

import com.game.nimgam.core.exceptions.InsufficientMatchesException;
import com.game.nimgam.model.ActionItem;

public interface IGame {
    void init(Integer initialMatches);
    Integer matchesLeft();
    Integer removeMatches(Integer matchesRemoved) throws InsufficientMatchesException;
    boolean isGameOver();

    void addAction(ActionItem playerAction);

    ActionItem getLastAction();
}
