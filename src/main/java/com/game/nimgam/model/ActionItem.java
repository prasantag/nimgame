package com.game.nimgam.model;

import com.game.nimgam.constants.Player;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ActionItem {
    private Player actionBy;
    private Integer matchesRemoved;
    private Integer matchesBeforeAction;
    private Integer matchesAfterAction;
}
