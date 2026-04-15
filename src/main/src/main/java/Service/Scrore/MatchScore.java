package Service.Scrore;

import lombok.Getter;

import java.util.*;

public class MatchScore extends Score<Integer> {

    private final Map<Integer, List<Integer>> gameResultsInSet;

    @Getter
    private SetScore currentSet;
    private final int setsForWin;
    @Getter
    private int serve;

    public MatchScore(int setsForWin) {
        this.gameResultsInSet = new HashMap<>();
        this.setsForWin       = setsForWin;
        this.currentSet       = new SetScore();
        this.serve            = new Random().nextInt(2);
    }

    @Override
    protected Integer getZeroScore() {
        return 0;
    }

    @Override
    public StateScore pointWon(int playerNumber) {
        StateScore setState = currentSet.pointWon(playerNumber);

        if (setState == StateScore.PLAYER_ONE_WON) {
            return setWon(playerNumber);
        }
        if (setState == StateScore.PLAYER_TWO_WON) {
            return setWon(playerNumber);
        }

        return StateScore.ONGOING;
    }

    private StateScore setWon(int playerNumber) {

        setPlayerScore(playerNumber, getPlayerScore(playerNumber) + 1);
        List<Integer> gameScore = new ArrayList<>();
        gameScore.add(currentSet.getPlayerScore(0));
        gameScore.add(currentSet.getPlayerScore(1));
        gameResultsInSet.put(getPlayerScore(0) + getPlayerScore(1), gameScore);

        if (getPlayerScore(playerNumber) == setsForWin) {
            if (playerNumber == 0) {
                return StateScore.PLAYER_ONE_WON;
            }
            if (playerNumber == 1) {
                return StateScore.PLAYER_TWO_WON;
            }
        }

        this.currentSet = new SetScore();
        return StateScore.ONGOING;

    }

    public Integer getGameResultsInSet(int setNumber, int playerNumber) {
        try {
            return gameResultsInSet.get(setNumber).get(playerNumber);
        } catch (NullPointerException e) {
            return -1;
        }
    }

    public String getCurrentGameScore(int playerNumber) {
        return getCurrentSet().getCurrentGame().getPlayerScore(playerNumber) instanceof GameRegularPlayerPoints
                ? ((GameRegularPlayerPoints) getCurrentSet().getCurrentGame().getPlayerScore(playerNumber)).getPointCode()
                : getCurrentSet().getCurrentGame().getPlayerScore(playerNumber).toString();
    }

}
