package com.game.nimgam.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Set;

@Component
@Slf4j
public class GameUtil {
    public Integer generateValidRandomNumber(Set<Integer> matchesRemovalAllowed, Integer matchesLeft) {
        int allowedRandomSize = (int)matchesRemovalAllowed.stream().filter(e -> e <= matchesLeft).count();
        log.debug("matches Left:{} and count:{}", matchesLeft, allowedRandomSize);
        int randomIndex = (int) (Math.random() * (allowedRandomSize - 1) + 1);
        return (Integer) matchesRemovalAllowed.toArray()[randomIndex - 1];
    }

    public int matchesRemovalEntries(Set<Integer> matchesRemovalAllowedSet, Integer matchesRemoved) {
        return (int) matchesRemovalAllowedSet.stream().filter(r -> r == matchesRemoved).count();
    }
}
