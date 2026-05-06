package Service.Scrore;

public class SetScore extends Score<Integer> {

    private GameScore<?> currentGame;   // Экземпляр счета (идет по геймам и может становиться тай брейком)

    public GameScore<?> getCurrentGame() {
        return currentGame;
    }

    public SetScore() {
        this.currentGame = new GameRegularScore();
    }

    @Override
    protected Integer getZeroScore() {
        return 0;
    }

    @Override
    StateScore pointWon(int playerNumber) {
        StateScore gameState = currentGame.pointWon(playerNumber);

        if (gameState == StateScore.PLAYER_ONE_WON) {
            return gameWon(playerNumber);
        } else if (gameState == StateScore.PLAYER_TWO_WON) {
            return gameWon(playerNumber);
        }

        return StateScore.ONGOING;
    }

    private StateScore gameWon(int playerNumber) {

        setPlayerScore(playerNumber, getPlayerScore(playerNumber) + 1);
        this.currentGame = new GameRegularScore();

        if (getPlayerScore(playerNumber) == 6 || getPlayerScore(playerNumber) == 7) {

            // Победа при отрыве минимум в 2 очка
            if (getPlayerScore(playerNumber) - getOppositePlayerScore(playerNumber) > 1) {
                if (playerNumber == 0) {
                    return StateScore.PLAYER_ONE_WON;
                }
                if (playerNumber == 1) {
                    return StateScore.PLAYER_TWO_WON;
                }
            }

            // Розыгрыш тай брейка при равном счете 6:6
            if (getPlayerScore(playerNumber) == 6 && getOppositePlayerScore(playerNumber) == 6) {
                this.currentGame = new GameTieBreakScore();
                return StateScore.ONGOING;
            }

            // Победа в тай брейке
            if (getPlayerScore(playerNumber) == 7 && getOppositePlayerScore(playerNumber) == 6) {
                this.currentGame = new GameRegularScore();
                if (playerNumber == 0) {
                    return StateScore.PLAYER_ONE_WON;
                }
                if (playerNumber == 1) {
                    return StateScore.PLAYER_TWO_WON;
                }
            }
        }

        return StateScore.ONGOING;
    }

}
