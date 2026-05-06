package servlets;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.IOException;

@WebFilter("/*")
public class ServletFilter implements Filter {

    private int tomcatVersion;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String serverInfo = filterConfig.getServletContext().getServerInfo();
        try {
            String[] parts = serverInfo.split("/");
            if (parts.length > 1) {
                String versionStr = parts[1].split("\\.")[0];
                tomcatVersion = Integer.parseInt(versionStr);
            }
        } catch (Exception e) {
            tomcatVersion = 0;
        }
    }

    @Override
    public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
            throws IOException, ServletException {

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");
        //response.setContentType("application/json;charset=UTF-8");

        // Если GET, нужно перекодировать параметры, если версия Tomcat = 7
        if (tomcatVersion == 7 && "GET".equalsIgnoreCase(((HttpServletRequest) req).getMethod())) {
            req = new HttpServletRequestWrapper((HttpServletRequest) req) {
                @Override
                public String getParameter(String name) {
                    String value = super.getParameter(name);
                    if (value != null) {
                        try {
                            return new String(value.getBytes("ISO-8859-1"), "UTF-8");
                        } catch (Exception e) {
                            return value;
                        }
                    }
                    return null;
                }
            };
        }

        chain.doFilter(req, resp);
    }

    @Override
    public void destroy() {
        // Пустая реализация обязательна!
    }
}