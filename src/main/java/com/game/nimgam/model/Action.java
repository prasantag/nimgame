package com.game.nimgam.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Action {
    private Integer matchesRemoved;
    private Integer matchesBeforeAction;
    private Integer matchesAfterAction;
}
