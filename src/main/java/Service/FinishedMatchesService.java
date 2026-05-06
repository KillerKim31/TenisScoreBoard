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

        Player firstPlayer = playerDao.findByName(currentMatch.getFirstPlayer().getName());
        Player secondPlayer = playerDao.findByName(currentMatch.getSecondPlayer().getName());
        if (firstPlayer == null) {
            firstPlayer = currentMatch.getFirstPlayer();
            playerDao.save(firstPlayer);
        }
        if (secondPlayer == null) {
            secondPlayer = currentMatch.getSecondPlayer();
            playerDao.save(secondPlayer);
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
