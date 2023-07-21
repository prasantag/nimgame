package com.game.nimgam.service;

import com.game.nimgam.entity.NimBoard;
import com.game.nimgam.model.NimBoardDTO;
import com.game.nimgam.repository.NimBoardRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.Instant;

@Service
@RequiredArgsConstructor
public class NimBoardService {
    private final NimBoardRepository nimBoardRepository;

    public NimBoardDTO createBoard(NimBoardDTO nimBoardDTO) {
        NimBoard nimBoard = nimBoardRepository.save(
                NimBoard.builder().playerName(nimBoardDTO.getPlayerName()).boardName(nimBoardDTO.getBoardName()).boardCreated(Instant.now()).build());
        return NimBoardDTO.builder().boardId(nimBoard.getId()).boardName(nimBoard.getBoardName()).playerName(nimBoard.getPlayerName()).build();
    }
}
