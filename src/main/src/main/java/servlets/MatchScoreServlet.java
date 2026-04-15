package servlets;

import Exceptions.InvalidEntryException;
import Exceptions.NotFoundEntryException;
import Service.CurrentMatch;
import Service.MatchService;
import Service.OngoingMatchesService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

public class MatchScoreServlet extends BaseServlet {

    private final OngoingMatchesService ongoingMatchesService = OngoingMatchesService.getOngoingMatchesService();

    private MatchService matchService;

    protected final String ERR_MSG_INCORRECT_DATA = "Неверно введены данные. Пример: uuid=\"83495621\"";
    protected final String ERR_MSG_INNER          = "Внутренняя ошибка сервера.";
    protected final String ERR_CURRENCY_NOT_FOUND = "Матч с указанным UUID не найден в БД.";
    protected final String ERR_MSG_DATABASE       = "Ошибка при взаимодействии с БД.";

    @Override
    public void init() throws ServletException {
        super.init();
        matchService = new MatchService();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        try {
            String uuidStr = req.getParameter("uuid");
            CurrentMatch currentMatch = matchService.getMatchByUuid(uuidStr, ongoingMatchesService);
            req.setAttribute("currentMatch", currentMatch);
            req.getRequestDispatcher("/view/match-score.jsp").forward(req, resp);
        } catch (InvalidEntryException e) {
            generateError(resp, HttpServletResponse.SC_BAD_REQUEST, mapper, ERR_MSG_INCORRECT_DATA);
        } catch (NotFoundEntryException e) {
            generateError(resp, HttpServletResponse.SC_NOT_FOUND, mapper, ERR_CURRENCY_NOT_FOUND);
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        String uuidStr = req.getParameter("uuid");
        String winnerIndex = req.getParameter("winnerIndex");
        matchService.updateMatchProcess(uuidStr, winnerIndex, ongoingMatchesService);
        resp.sendRedirect("match-score?uuid=" + uuid);
    }

}
