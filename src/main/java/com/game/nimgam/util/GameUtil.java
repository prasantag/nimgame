package com.game.nimgam.util;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component
@Slf4j
public class GameUtil {
    public Integer generateValidRandomNumber(Set<Integer> matchesRemovalAllowed, Integer matchesLeft) {
        int allowedRandomSize = (int)matchesRemovalAllowed.stream().filter(e -> e <= matchesLeft).count();
        log.debug("matches Left:{} and count:{}", matchesLeft, allowedRandomSize);
        int randomIndex = (int) (Math.random() * (allowedRandomSize - 1) + 1);
        return (Integer) matchesRemovalAllowed.toArray()[randomIndex - 1];
    }

    public Set<Integer> getMatchesRemovalOptionSet(final String matchesRemovalOptions) {
        return Arrays.stream(matchesRemovalOptions.split(","))
                .map(Integer::valueOf).collect(Collectors.toSet());
    }
}
