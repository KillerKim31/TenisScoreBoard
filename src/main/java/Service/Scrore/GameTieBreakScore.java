package Service.Scrore;

public class GameTieBreakScore extends GameScore<Integer> {

    @Override
    protected Integer getZeroScore() {
        return 0;
    }

    @Override
    StateScore pointWon(int playerNumber) {
        setPlayerScore(playerNumber, getPlayerScore(playerNumber) + 1);

        if (getPlayerScore(playerNumber) > 6 && (getPlayerScore(playerNumber) - getOppositePlayerScore(playerNumber)) > 1) {
            if (playerNumber == 0) {
                return StateScore.PLAYER_ONE_WON;
            }
            if (playerNumber == 1) {
                return StateScore.PLAYER_TWO_WON;
            }
        }

        return StateScore.ONGOING;
    }

}
