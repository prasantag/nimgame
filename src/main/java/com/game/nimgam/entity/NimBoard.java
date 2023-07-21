package com.game.nimgam.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NimBoard {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String boardName;
    private String playerName;
    private Instant boardCreated;
    private Instant boardUpdated;

}
