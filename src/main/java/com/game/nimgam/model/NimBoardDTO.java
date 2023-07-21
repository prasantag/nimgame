package com.game.nimgam.model;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class NimBoardDTO {
    private Long boardId;
    private String boardName;
    private String playerName;
}
