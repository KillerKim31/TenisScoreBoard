package servlets;

import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public abstract class BaseServlet extends HttpServlet {

    protected void generateError(HttpServletResponse resp, int ErrorCode, ObjectMapper mapper, String errorText)
            throws IOException {
        resp.setStatus(ErrorCode);
        Map<String, Object> errorObject = new HashMap<>();
        errorObject.put("error", errorText);
        mapper.writeValue(resp.getWriter(), errorObject);
    }

}
