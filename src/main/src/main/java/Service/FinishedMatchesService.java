package Service;

import DAO.FinishedMatchDAO;
import DAO.PlayerDAO;
import Exceptions.DatabaseException;
import models.FinishedMatch;
import models.Player;

public class FinishedMatchesService {

    private final OngoingMatchesService ongoingMatchesService = OngoingMatchesService.getOngoingMatchesService();
    private final FinishedMatchDAO matchesDao = new FinishedMatchDAO();
    private final PlayerDAO playerDao = new PlayerDAO();

    public void persist(CurrentMatch currentMatch) throws DatabaseException {

        Player firstPlayer = null;
        try {
            firstPlayer = currentMatch.getFirstPlayer();
            playerDao.save(firstPlayer);
        } catch (Exception e) {
            Player player = playerDao.findByName(currentMatch.getFirstPlayer().getName());
            if (player != null) {
                firstPlayer = player;
            }
        }

        Player secondPlayer = null;
        try {
            secondPlayer = currentMatch.getSecondPlayer();
            playerDao.save(secondPlayer);
        } catch (Exception e) {
            Player player = playerDao.findByName(currentMatch.getSecondPlayer().getName());
            if (player != null) {
                secondPlayer = player;
            }
        }

        Player winner;
        if (currentMatch.getWinner().getName().equalsIgnoreCase(firstPlayer.getName())) {
            winner = firstPlayer;
        } else {
            winner = secondPlayer;
        }

        matchesDao.save(new FinishedMatch(firstPlayer, secondPlayer, winner));

    }

}
