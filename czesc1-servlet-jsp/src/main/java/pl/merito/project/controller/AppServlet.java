package pl.merito.project.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

// Servlet startowy z etapu 1 rozbudowany o zabezpieczenie danych wyjściowych (XSS).
@WebServlet("/start")
public class AppServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"pl\">");
        out.println("<head><meta charset=\"UTF-8\"><title>AstroDziennik – powitanie</title></head>");
        out.println("<body>");
        out.println("<h1>Witaj w AstroDzienniku!</h1>");
        out.println("<p>To jest odpowiedź na żądanie GET.</p>");
        out.println("<p><a href=\"" + req.getContextPath() + "/\">Powrót na stronę główną</a></p>");
        out.println("</body></html>");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String rawName = req.getParameter("name");
        String displayName = (rawName == null || rawName.isBlank()) ? "Gościu" : rawName.trim();

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"pl\">");
        out.println("<head><meta charset=\"UTF-8\"><title>AstroDziennik – powitanie</title></head>");
        out.println("<body>");
        out.println("<h1>Cześć, " + HtmlUtils.escape(displayName) + "!</h1>");
        out.println("<p>To jest odpowiedź na żądanie POST.</p>");
        out.println("<p><a href=\"" + req.getContextPath() + "/\">Powrót na stronę główną</a></p>");
        out.println("</body></html>");
    }
}
