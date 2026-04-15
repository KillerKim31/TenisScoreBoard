package servlets;

import Exceptions.DatabaseException;
import Exceptions.DuplicateEntryException;
import Exceptions.InvalidEntryException;
import Exceptions.NotFoundEntryException;
import com.fasterxml.jackson.databind.ObjectMapper;
import Service.OngoingMatchesService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.UUID;

@WebServlet("/new-match")
public class MatchesServlet extends BaseServlet {

    private final OngoingMatchesService ongoingMatchesService = OngoingMatchesService.getOngoingMatchesService();

    protected final String ERR_MSG_INCORRECT_DATA = "Неверно введены данные. Пример: player-1=\"Ф. Маккари\" player-2=\"С. Деревянко\"";
    protected final String ERR_MSG_INNER          = "Внутренняя ошибка сервера.";
    protected final String ERR_MSG_DATABASE       = "Ошибка при взаимодействии с БД.";



    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String filterName = req.getParameter("filter_by_player_name");
        String page = req.getParameter("page");



        req.setAttribute("totalPages", totalPages);
        req.setAttribute("currentPage", page);
        req.setAttribute("matches", matches);
        req.setAttribute("totalItems", totalItems);
        log.info("Total pages -> {}", totalPages);
        log.info("Current page -> {}", page);
        log.info("Total_Items -> {}", totalItems);
        req.getRequestDispatcher("/view/matches.jsp").forward(req, resp);

    }


//    @Override
//    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
//
//        ObjectMapper mapper = new ObjectMapper();
//        try {
//            String requestBody = utils.Utils.getRequestBodyString(req);
//            HashMap jsonObject = mapper.readValue(requestBody, HashMap.class);
//            UUID uuid = new Service.PlayerService().StartNewMatch(jsonObject);
//            resp.sendRedirect("match-score?uuid=" + uuid);
//        }
//        catch (InvalidEntryException | IOException e) {
//            generateError(resp, HttpServletResponse.SC_BAD_REQUEST, mapper, ERR_MSG_INCORRECT_DATA);
//        } catch (DatabaseException e) {
//            generateError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, mapper, ERR_MSG_DATABASE);
//        } catch (Exception e) {
//            generateError(resp, HttpServletResponse.SC_INTERNAL_SERVER_ERROR, mapper, ERR_MSG_INNER);
//        }
//
//    }
}
