package com.game.nimgam.core;

import com.game.nimgam.core.exceptions.InsufficientMatchesException;

public interface IGame {
    void init(Integer matches);
    Integer matchesLeft();
    Integer matchesRemoved(Integer removeMatches) throws InsufficientMatchesException;
    Boolean isValidMatchesRemoval(Integer removeMatches);
}
