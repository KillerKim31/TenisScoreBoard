package servlets;

import Exceptions.InvalidEntryException;
import Service.MatchService;
import Service.OngoingMatchesService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/new-match")
public class NewMatchServlet extends BaseServlet {

    private final String ERR_MSG_INCORRECT_DATA = """
                                                        Неверно введены данные.
                                                        Пример: player-1="Д. Медведев", player-2="М. Фукович", match-sets=6
                                                    """;
    private final String ERR_MSG_INNER          = "Внутренняя ошибка сервера.";

    private final OngoingMatchesService ongoingMatchesService = OngoingMatchesService.getOngoingMatchesService();
    private MatchService matchService = new MatchService(ongoingMatchesService);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("/view/new-match.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {

        ObjectMapper mapper = new ObjectMapper();
        try {
            String playerOne = req.getParameter("player-1");
            String playerTwo = req.getParameter("player-2");
            String setsInMatch = req.getParameter("match-sets");
            UUID uuid = matchService.createNewMatch(playerOne, playerTwo, setsInMatch);
            resp.sendRedirect("match-score?uuid=" + uuid);
        } catch (InvalidEntryException | IOException e) {
            generateError(resp, HttpServletResponse.SC_BAD_REQUEST, mapper, ERR_MSG_INCORRECT_DATA);
        } catch (Exception e) {
            generateError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, mapper, ERR_MSG_INNER);
        }

    }

}
