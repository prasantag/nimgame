package com.game.nimgam.core.exceptions;

public class InsufficientMatchesException extends Throwable {
    public InsufficientMatchesException(String sufficientMatchesAreNotAvailable) {
        super(sufficientMatchesAreNotAvailable);
    }
}
