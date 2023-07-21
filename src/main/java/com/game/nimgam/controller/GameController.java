package com.game.nimgam.controller;

import com.game.nimgam.core.exceptions.InvalidMatchesRemovalException;
import com.game.nimgam.model.GameRequestDTO;
import com.game.nimgam.model.NimBoardDTO;
import com.game.nimgam.service.GameService;
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
    private final GameService gameService;
    private final NimBoardService nimBoardService;

    @PostMapping("/create-board")
    public ResponseEntity createNimBoard(@RequestBody NimBoardDTO nimBoardDTO) {
        return new ResponseEntity(nimBoardService.createBoard(nimBoardDTO), HttpStatus.CREATED);
    }
    @PostMapping("/{boardId}/create-game")
    public ResponseEntity createGame(@PathVariable Long boardId) {
        return new ResponseEntity(gameService.createGame(boardId), HttpStatus.CREATED);
    }

    @GetMapping("/{gameId}/game-details")
    public ResponseEntity getGameDetails(@PathVariable Long gameId) {
        return  new ResponseEntity(gameService.getGame(gameId), HttpStatus.OK);
    }

    @PutMapping("/player-action")
    public ResponseEntity playerAction(@RequestBody GameRequestDTO gameRequest) throws InvalidMatchesRemovalException {
        log.info("gameRequest: {}", gameRequest);
        return new ResponseEntity(gameService.processHumanAction(gameRequest), HttpStatus.OK);
    }
}
