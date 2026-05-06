package servlets;

import Exceptions.DatabaseException;
import Exceptions.InvalidEntryException;
import Exceptions.NotFoundEntryException;
import Service.MatchService;
import com.fasterxml.jackson.databind.ObjectMapper;
import models.FinishedMatch;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

@WebServlet("/matches")
public class MatchesServlet extends BaseServlet {

    private final MatchService matchService = new MatchService(null);

    private final String ERR_MSG_INNER           = "Внутренняя ошибка сервера.";
    private final String ERR_MSG_DATABASE        = "Ошибка при взаимодействии с БД.";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ObjectMapper mapper = new ObjectMapper();
        try {
            String filterName = req.getParameter("filter_by_player_name");
            String pageStr    = req.getParameter("page");
            Map<String, Object> map = matchService.getFinishedMatchList(filterName, pageStr);

            req.setAttribute("totalPages" , map.get("totalPages"));
            req.setAttribute("currentPage", map.get("page"));
            req.setAttribute("matches"    , map.get("matchList"));
            req.setAttribute("totalItems" , map.get("totalItems"));
            req.getRequestDispatcher("/view/matches.jsp").forward(req, resp);
        } catch (DatabaseException e) {
            generateError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, mapper, ERR_MSG_DATABASE);
        } catch (Exception e) {
            generateError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, mapper, ERR_MSG_INNER);
        }

    }

}
