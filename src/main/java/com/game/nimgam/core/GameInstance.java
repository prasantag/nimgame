package com.game.nimgam.core;

import com.game.nimgam.constants.Player;
import com.game.nimgam.core.exceptions.InsufficientMatchesException;
import com.game.nimgam.core.exceptions.InvalidMatchesRemovalException;
import com.game.nimgam.model.ActionItem;
import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Getter
@Builder
@RequiredArgsConstructor
@AllArgsConstructor
public class GameInstance implements IGame {
    private final Long gameId;
    private Integer matches;
    private final Set<Integer> matchesRemovalSetOfOptions;
    private Player winner;
    private List<ActionItem> actions;
    @Setter
    private Player currentPlayer;

//    public GameInstance(Long gameId, Set<Integer> matchesRemovalSetOfOptions) {
//        this.gameId = gameId;
//        this.matchesRemovalSetOfOptions = matchesRemovalSetOfOptions;
//    }
    @Override
    public void init(Integer initialMatches) {
        this.matches = initialMatches;
        actions = new ArrayList<>();
    }

    @Override
    public Integer matchesLeft() {
        return matches;
    }

    @Override
    public Integer removeMatches(Integer matchesRemoved) {
        matches -= matchesRemoved;
        return matchesRemoved;
    }

    public void declareWinner(Player player) {
        winner = player;
    }

    @Override
    public boolean isGameOver() {
        return matches == 0;
    }

    @Override
    public void addAction(ActionItem playerAction) {
        actions.add(playerAction);
    }

    @Override
    public ActionItem getLastAction() {
        return actions.size() > 0 ? actions.get(actions.size() - 1) : null;
    }

    public void validateAction(Integer matchesRemoved)
            throws InvalidMatchesRemovalException, InsufficientMatchesException {
        //validate game rules, allowed matches removal
        if(!matchesRemovalSetOfOptions.contains(matchesRemoved))
            throw new InvalidMatchesRemovalException("Not a valid match removal");
        if(matches < matchesRemoved)
            throw new InsufficientMatchesException("Insufficient matches for removal");
    }
}
