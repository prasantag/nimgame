package com.game.nimgam.repository;

import com.game.nimgam.entity.NimBoard;
import org.springframework.data.jpa.repository.JpaRepository;

public interface NimBoardRepository extends JpaRepository<NimBoard, Long> {
}
