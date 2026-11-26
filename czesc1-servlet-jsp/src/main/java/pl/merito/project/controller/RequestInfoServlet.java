package pl.merito.project.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;
import java.util.Map;

// Servlet prezentujący podstawowe informacje o bieżącym żądaniu HTTP.
@WebServlet("/info")
public class RequestInfoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setContentType("text/html;charset=UTF-8");

        String queryString = req.getQueryString();
        String userAgent = req.getHeader("User-Agent");
        Map<String, String[]> params = req.getParameterMap();

        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"pl\">");
        out.println("<head><meta charset=\"UTF-8\"><title>Informacje o żądaniu – AstroDziennik</title></head>");
        out.println("<body>");
        out.println("<h1>Informacje o żądaniu HTTP</h1>");
        out.println("<ul>");
        out.println("<li>Metoda: " + HtmlUtils.escape(req.getMethod()) + "</li>");
        out.println("<li>URI: " + HtmlUtils.escape(req.getRequestURI()) + "</li>");
        out.println("<li>Kontekst aplikacji: " + HtmlUtils.escape(req.getContextPath()) + "</li>");
        out.println("<li>Ścieżka servletu: " + HtmlUtils.escape(req.getServletPath()) + "</li>");
        out.println("<li>Query string: " + (queryString == null ? "(brak)" : HtmlUtils.escape(queryString)) + "</li>");
        out.println("<li>Adres klienta: " + HtmlUtils.escape(req.getRemoteAddr()) + "</li>");
        out.println("<li>Przeglądarka (User-Agent): " + (userAgent == null ? "(brak)" : HtmlUtils.escape(userAgent)) + "</li>");
        if (params.isEmpty()) {
            out.println("<li>Parametry żądania: (brak)</li>");
        } else {
            params.forEach((name, values) -> out.println(
                    "<li>Parametr " + HtmlUtils.escape(name) + " = " + HtmlUtils.escape(Arrays.toString(values)) + "</li>"
            ));
        }
        out.println("</ul>");
        out.println("<p><a href=\"" + req.getContextPath() + "/\">Powrót na stronę główną</a></p>");
        out.println("</body></html>");
    }
}
