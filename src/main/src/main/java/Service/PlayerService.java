package Service;

import Exceptions.InvalidEntryException;
import models.Player;

import java.util.Map;
import java.util.UUID;

public class PlayerService {

    public UUID StartNewMatch(Map<String, Object> jsonObject) throws InvalidEntryException {

        if (!isValidStartMatch(jsonObject)) throw new InvalidEntryException();
        String playerName1 = jsonObject.get("player-1").toString().toUpperCase();
        String playerName2 = jsonObject.get("player-2").toString().toUpperCase();

        try {
            Player player1 = new Player(playerName1);
            Player player2 = new Player(playerName2);
            UUID uuid = UUID.randomUUID();
            //ongoingMatchesService.createNewMatch(uuid, firstPlayer, secondPlayer, setsInMatch);
            return uuid;
        } catch (Exception e) {
            throw e;
        }

    }

    private boolean isValidStartMatch(Map<String, Object> jsonObject) {
        String playerOne = jsonObject.get("player-1").toString();
        String playerTwo = jsonObject.get("player-2").toString();
        if (playerOne == null || playerTwo == null || playerOne.equalsIgnoreCase(playerTwo)) {
            return false;
        }
        return true;
    }

}
