package Service;

import DAO.FinishedMatchDAO;
import Exceptions.DatabaseException;
import Exceptions.InvalidEntryException;
import Exceptions.NotFoundEntryException;
import Service.Scrore.StateScore;
import models.FinishedMatch;
import models.Player;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MatchService {

    private final OngoingMatchesService ongoingMatchesService;
    private final FinishedMatchesService finishedMatchesPersistenceService;
    private final FinishedMatchDAO matchesDao;

    public MatchService(OngoingMatchesService ongoingMatchesService) {
        this.ongoingMatchesService = ongoingMatchesService;
        this.finishedMatchesPersistenceService = new FinishedMatchesService();
        this.matchesDao = new FinishedMatchDAO();
    }

    public CurrentMatch getCurrentMatch(String uuidStr) throws InvalidEntryException, NotFoundEntryException {
        if (!utils.Utils.isValidUuid(uuidStr)) throw new InvalidEntryException();
        UUID uuid = UUID.fromString(uuidStr);
        CurrentMatch currentMatch = ongoingMatchesService.getCurrentMatch(uuid);
        if (currentMatch == null) throw new NotFoundEntryException();
        return currentMatch;
    }

    public UUID createNewMatch(String strPlayer1, String strPlayer2, String strSetsInMatch) throws InvalidEntryException {

        if (!isValidParamsCreateMatch(strPlayer1, strPlayer2, strSetsInMatch)) throw new InvalidEntryException();
        Player firstPlayer = new Player(strPlayer1.toUpperCase());
        Player secondPlayer = new Player(strPlayer2.toUpperCase());
        int setsInMatch = strSetsInMatch != null ? Integer.parseInt(strSetsInMatch) : 0;

        return ongoingMatchesService.createNewMatch(firstPlayer, secondPlayer, setsInMatch);

    }

    public UUID checkMatch(String uuidStr, String winIndexStr) throws InvalidEntryException, DatabaseException {

        if (!utils.Utils.isValidUuid(uuidStr) || !utils.Utils.isValidInteger(winIndexStr))
            throw new InvalidEntryException();
        UUID uuid = UUID.fromString(uuidStr);
        int winnerIndex = Integer.parseInt(winIndexStr);

        CurrentMatch currentMatch = ongoingMatchesService.getCurrentMatch(uuid);
        StateScore winner = currentMatch.getMatchScore().pointWon(winnerIndex);
        if (winner != StateScore.ONGOING) {
            if (winner == StateScore.PLAYER_ONE_WON) {
                currentMatch.setWinner(currentMatch.getFirstPlayer());
            } else if (winner == StateScore.PLAYER_TWO_WON) {
                currentMatch.setWinner(currentMatch.getSecondPlayer());
            }
            finishedMatchesPersistenceService.persist(currentMatch);
        }
        return uuid;

    }

    public Map<String, Object> getFinishedMatchList(String filterName, String pageStr) throws DatabaseException {

        long page;
        long totalItems;
        long pageSize = 5;
        Map<String, Object> map = new HashMap<>();
        try {
            page = Long.parseLong(pageStr);
        } catch (NumberFormatException e) {
            page = 0;
        }

        if (filterName == null || filterName.isBlank()) {
            totalItems = matchesDao.getAllUnique();
            map.put("matchList", matchesDao.getAllAtPagination((int) pageSize, (int) (page * pageSize)));
            map.put("totalItems", totalItems);
        } else {

            map.put("matchList", matchesDao.getByPlayerNameAtPagination(filterName, (int) pageSize, (int) (page * pageSize)));
            totalItems = matchesDao.getByPlayerNameUnique(filterName);
            map.put("totalItems", totalItems);
        }
        map.put("page", page);
        map.put("totalPages", totalItems / pageSize + 1);
        return map;

    }

    private boolean isValidParamsCreateMatch(String strPlayer1, String strPlayer2, String strSetsInMatch) {
        if (strPlayer1 == null || strPlayer1.isBlank() || strPlayer1 == null || strPlayer1.isBlank())
            return false;
        if (strSetsInMatch != null) {
            try {
                Integer.parseInt(strSetsInMatch);
            } catch (Exception e) {
                return false;
            }
        }
        return true;
    }

}
