package com.game.nimgam.config;

import lombok.Getter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
@Getter
public class GameConfiguration {
    @Value("${game.matches.initial:13}")
    private Integer matches;
    @Value("${game.matches.removal.allowed:1,2,3}")
    private String matchesRemovalOptions;
}
