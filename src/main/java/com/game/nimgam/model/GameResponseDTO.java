package com.game.nimgam.model;

import com.game.nimgam.constants.Player;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class GameResponseDTO {
    private Long gameId;
    private ActionItem playerAction;
    private Boolean computerActed;
    private Integer initialMatches;
    private Set<Integer> matchesRemovalAllowedSet;
    private Player winner;
}
