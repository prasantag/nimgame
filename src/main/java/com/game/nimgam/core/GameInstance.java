package com.game.nimgam.core;

import com.game.nimgam.core.exceptions.InsufficientMatchesException;

public class GameInstance implements IGame {
    private Integer matches;
    @Override
    public void init(Integer matches) {
        this.matches = matches;
    }

    @Override
    public Integer matchesLeft() {
        return matches;
    }

    @Override
    public Integer matchesRemoved(Integer removeMatches) throws InsufficientMatchesException {
        if(!isValidMatchesRemoval(removeMatches)) throw new InsufficientMatchesException("Sufficient matches are not available");
        matches -= removeMatches;
        return matchesLeft();
    }

    @Override
    public Boolean isValidMatchesRemoval(Integer removeMatches) {
        return matches - removeMatches >= 0;
    }
}
