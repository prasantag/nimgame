package com.game.nimgam.entity;

import com.game.nimgam.constants.Player;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.Instant;
import java.util.List;

@Table
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Game {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @ManyToOne
    private NimBoard nimBoard;
    private Integer numberOfMatches;
    private String matchesRemovalOptions;
    private Player winner;
    @OneToMany(fetch = FetchType.EAGER)
    private List<Action> actions;
    private Instant created;
    private Instant updated;
}
