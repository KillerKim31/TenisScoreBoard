package Service;

import Service.Scrore.MatchScore;
import models.Player;

import java.util.UUID;

public class CurrentMatch {

    private final UUID   uuid;
    private final Player firstPlayer;
    private final Player secondPlayer;

    private Player       winner;
    private MatchScore   matchScore;
    private int          setsInMatch;
    private int          setForWin;

    public CurrentMatch(UUID uuid, Player firstPlayer, Player secondPlayer, int setsInMatch) {
        this.uuid         = uuid;
        this.firstPlayer  = firstPlayer;
        this.secondPlayer = secondPlayer;
        this.setsInMatch  = setsInMatch;
        this.setForWin    = (setsInMatch + 1) / 2;
        this.matchScore   = new MatchScore(setForWin);
    }

    public UUID getUuid() { return uuid; }
    public Player getFirstPlayer() { return firstPlayer; }
    public Player getSecondPlayer() { return secondPlayer; }
    public Player getWinner() { return winner; }
    public void setWinner(Player winner) { this.winner = winner; }
    public MatchScore getMatchScore() { return matchScore; }
    public void setMatchScore(MatchScore matchScore) { this.matchScore = matchScore; }
    public int getSetsInMatch() { return setsInMatch; }
    public void setSetsInMatch(int setsInMatch) { this.setsInMatch = setsInMatch; }
    public int getSetForWin() { return setForWin; }
}
