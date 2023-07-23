package com.game.nimgam.entity;

import com.game.nimgam.constants.Player;
import lombok.Builder;
import lombok.Data;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table
@Builder
@Data
public class Action {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private Game game;
    private Player actionBy;
    private Integer matchesRemoved;
    private Integer matchesBeforeAction;
    private Integer matchesAfterAction;
    private Instant created;
    private Instant updated;
}
