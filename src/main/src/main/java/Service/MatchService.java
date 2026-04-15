package Service;

import Exceptions.InvalidEntryException;
import Exceptions.NotFoundEntryException;
import models.FinishedMatch;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class MatchService {

    public CurrentMatch getMatchByUuid(String uuidStr, OngoingMatchesService ongoingMatchesService) throws InvalidEntryException, NotFoundEntryException {
        if (!isValidUuid(uuidStr)) throw new InvalidEntryException();
        UUID uuid = UUID.fromString(uuidStr);
        CurrentMatch currentMatch = ongoingMatchesService.getCurrentMatch(uuid);
        if (currentMatch == null) throw new NotFoundEntryException();
        return currentMatch;
    }

    public void updateMatchProcess(String uuidStr, String winnerIndex, OngoingMatchesService ongoingMatchesService)
            throws InvalidEntryException {

        if (!isValidMatchUpdate(uuidStr, winnerIndex)) throw new InvalidEntryException();
        UUID uuid = UUID.fromString(uuidStr);
        long index = Long.parseLong(winnerIndex);

        CurrentMatch currentMatch = ongoingMatchesService.getCurrentMatch(uuid);
        if (currentMatch.getMatchScore().pointWon(index) == State.PLAYER_ONE_WON) {
            currentMatch.setWinner(currentMatch.getFirstPlayer());
            finishedMatchesPersistenceService.persist(currentMatch);
        }
    }

    public Map<String, Object> getMatchesResources(String filterName, String pageStr) throws InvalidEntryException {

        long page;
        long pageSize = 5L;
        long totalItems = 0L;

        try {
            page = Long.parseLong(pageStr);
        } catch (Exception e) {
            throw new InvalidEntryException();
        }

        List<FinishedMatch> matchList;
        Map<String, Object> resultMap = new HashMap<>();
        if (filterName == null || filterName.trim().isEmpty()) {
            matchList = matchesDao.getAllPagination(pageSize, page * pageSize);
            totalItems = matchesDao.getAllUnique();
        }
        else {
            matchList = matchesDao.getByPlayerNamePagination(filterName, (int) pageSize, (int) (page * pageSize));
            totalItems = matchesDao.getByPlayerNameUnique(filterName);
        }
        long totalPages = (totalItems / pageSize) + 1;

        resultMap.put("matchList", matchList);
        resultMap.put("totalItems", totalItems);
        resultMap.put("totalPages", totalPages);

        return resultMap;

    }

    private boolean isValidUuid(String uuidStr) {
        if(uuidStr == null || uuidStr.isBlank()) return false;
        try {
            UUID.fromString(uuidStr);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    private boolean isValidMatchUpdate(String uuidStr, String winnerIndex) {
        if (!isValidUuid(uuidStr) || winnerIndex == null || winnerIndex.isBlank()) return false;
        try {
            long index = Long.parseLong(winnerIndex);
            return List.of(0L, 1L).contains(index);
        } catch (Exception e) {
            return false;
        }
    }

}
