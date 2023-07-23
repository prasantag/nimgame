package com.game.nimgam.controller;

import com.game.nimgam.core.exceptions.InsufficientMatchesException;
import com.game.nimgam.core.exceptions.InvalidMatchesRemovalException;
import com.game.nimgam.model.GameRequestDTO;
import com.game.nimgam.model.NimBoardDTO;
import com.game.nimgam.service.GameDataHandler;
import com.game.nimgam.service.GameExecutor;
import com.game.nimgam.service.NimBoardService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/nimgame")
@RequiredArgsConstructor
public class GameController {
    private final GameExecutor gameService;
    private final NimBoardService nimBoardService;
    private final GameDataHandler gameDataHandler;

    @PostMapping("/create-board")
    public ResponseEntity createNimBoard(@RequestBody NimBoardDTO nimBoardDTO) {
        return new ResponseEntity(nimBoardService.createBoard(nimBoardDTO), HttpStatus.CREATED);
    }
    @PostMapping("/{boardId}/create-game")
    public ResponseEntity createGame(@PathVariable Long boardId) {
        return new ResponseEntity(gameDataHandler.createGame(boardId), HttpStatus.CREATED);
    }

    @PutMapping("/player-action")
    public ResponseEntity playerAction(@RequestBody GameRequestDTO gameRequest) throws InvalidMatchesRemovalException, InsufficientMatchesException {
        log.debug("gameRequest: {}", gameRequest);
        return new ResponseEntity(gameService.processHumanAction(gameRequest), HttpStatus.OK);
    }
}
