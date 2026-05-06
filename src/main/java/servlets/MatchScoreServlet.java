package servlets;

import Exceptions.DatabaseException;
import Exceptions.InvalidEntryException;
import Exceptions.NotFoundEntryException;
import Service.CurrentMatch;
import Service.FinishedMatchesService;
import Service.MatchService;
import Service.OngoingMatchesService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/match-score")
public class MatchScoreServlet extends BaseServlet {

    private final String ERR_MSG_INCORRECT_DATA  = """
                                                      Неверно введены данные.
                                                      Пример: uuid=e32d0f66-59a6-48cf-96ec-4566f3716b16
                                                   """;
    private final String ERR_MSG_INNER           = "Внутренняя ошибка сервера.";
    private final String ERR_MSG_DATABASE        = "Ошибка при взаимодействии с БД.";
    private final String ERR_MSG_MATCH_NOT_FOUND = "Матч с указанным uuid не найден.";

    private final OngoingMatchesService ongoingMatchesService = OngoingMatchesService.getOngoingMatchesService();
    private final MatchService matchService = new MatchService(ongoingMatchesService);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        ObjectMapper mapper = new ObjectMapper();
        try {
            String uuidStr = req.getParameter("uuid");
            CurrentMatch currentMatch = matchService.getCurrentMatch(uuidStr);
            req.setAttribute("currentMatch", currentMatch);
            req.getRequestDispatcher("/view/match-score.jsp").forward(req, resp);
        } catch (InvalidEntryException | IOException e) {
            generateError(resp, HttpServletResponse.SC_BAD_REQUEST, mapper, ERR_MSG_INCORRECT_DATA);
        } catch (NotFoundEntryException e) {
            generateError(resp, HttpServletResponse.SC_NOT_FOUND, mapper, ERR_MSG_MATCH_NOT_FOUND);
        } catch (Exception e) {
            generateError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, mapper, ERR_MSG_INNER);
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        try {
            String uuidStr = req.getParameter("uuid");
            String winIndexStr = req.getParameter("winner_index");
            UUID uuid = matchService.checkMatch(uuidStr, winIndexStr);
            resp.sendRedirect("match-score?uuid=" + uuid);
        } catch (InvalidEntryException | IOException e) {
            generateError(resp, HttpServletResponse.SC_BAD_REQUEST, mapper, ERR_MSG_INCORRECT_DATA);
        } catch (DatabaseException e) {
            generateError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, mapper, ERR_MSG_DATABASE);
        } catch (Exception e) {
            generateError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, mapper, ERR_MSG_INNER);
        }

    }

}
