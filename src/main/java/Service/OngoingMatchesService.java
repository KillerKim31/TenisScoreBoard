package Service;

import models.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class OngoingMatchesService {

    private static final OngoingMatchesService INSTANCE = new OngoingMatchesService();
    private static final Map<UUID, CurrentMatch> currentMatches = new HashMap<>();

    private OngoingMatchesService() { }

    public static OngoingMatchesService getOngoingMatchesService() {
        return INSTANCE;
    }

    public UUID createNewMatch(Player player1, Player player2, int setsInMatch) {
        UUID uuid = UUID.randomUUID();
        CurrentMatch currentMatch = new CurrentMatch(uuid, player1, player2, setsInMatch);
        currentMatches.put(uuid, currentMatch);
        return uuid;
    }

    public void remove(UUID uuid) {
        currentMatches.remove(uuid);
    }

    public CurrentMatch getCurrentMatch(UUID uuid) {
        return currentMatches.get(uuid);
    }

}
