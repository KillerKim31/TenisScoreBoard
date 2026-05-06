package Service.Scrore;

import java.util.List;
import java.util.ArrayList;

public abstract class Score<T> {

    private final List<T> playerScores = new ArrayList<>();

    public Score() {
        playerScores.add(getZeroScore());
        playerScores.add(getZeroScore());
    }

    public T getPlayerScore(int playerNumber) {
        return playerScores.get(playerNumber);
    }

    public T getOppositePlayerScore(int playerNumber) {
        return playerScores.get(playerNumber == 0 ? 1 : 0);
    }

    public void setPlayerScore(int playerNumber, T score) {
        playerScores.set(playerNumber, score);
    }

    public void setOppositePlayerScore(int playerNumber, T score) {
        playerScores.set(playerNumber == 0 ? 1 : 0, score);
    }

    protected abstract T getZeroScore();

    abstract StateScore pointWon(int playerNumber);

}
