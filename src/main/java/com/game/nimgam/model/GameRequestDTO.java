package com.game.nimgam.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class GameRequestDTO {
    private Long boardId;
    private Long gameId;
    private ActionItem playerAction;
}
