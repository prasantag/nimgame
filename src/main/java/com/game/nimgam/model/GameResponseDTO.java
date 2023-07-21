package com.game.nimgam.model;

import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class GameResponseDTO {
    private Long gameId;
    private Action computerAction;
    private Action humanAction;
    private Boolean computerActed = Boolean.FALSE;
    private Integer initialMatches;
    private Set<Integer> matchesRemovalAllowedSet;
    private String winner;
}
