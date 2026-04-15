package Service;

import Service.Scrore.MatchScore;
import lombok.Data;
import lombok.ToString;
import models.Player;

import java.util.UUID;

@Data
@ToString
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

}
