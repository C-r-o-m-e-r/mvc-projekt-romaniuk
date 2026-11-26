package pl.merito.project.controller;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import pl.merito.project.model.Observation;
import pl.merito.project.model.ObservationRepository;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

// Servlet wyświetlający potwierdzenie zapisu po przekierowaniu GET oraz listę zapisanych obserwacji.
@WebServlet("/observations/added")
public class ObservationAddedServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession session = req.getSession(false);
        Observation lastObservation = null;
        Integer addedCount = null;
        if (session != null) {
            synchronized (session) {
                lastObservation = (Observation) session.getAttribute("lastObservation");
                addedCount = (Integer) session.getAttribute("addedCount");
            }
        }

        List<Observation> observations = ObservationRepository.findAll();
        String ctx = req.getContextPath();

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"pl\">");
        out.println("<head><meta charset=\"UTF-8\"><title>Zapisane obserwacje – AstroDziennik</title></head>");
        out.println("<body>");

        if (lastObservation == null) {
            out.println("<h1>Brak ostatniej obserwacji w sesji</h1>");
            out.println("<p>Dodaj nową obserwację, aby zobaczyć tutaj potwierdzenie zapisu.</p>");
        } else {
            out.println("<h1>Obserwacja została zapisana</h1>");
            out.println("<p>Ostatnio dodano: <strong>" + HtmlUtils.escape(lastObservation.getTitle()) + "</strong>"
                    + " (" + HtmlUtils.escape(lastObservation.getPlace()) + ", " + lastObservation.getYear() + ").</p>");
            out.println("<p>Liczba obserwacji dodanych w tej sesji: " + (addedCount == null ? 0 : addedCount) + ".</p>");
        }

        out.println("<h2>Wszystkie zapisane obserwacje (" + observations.size() + ")</h2>");
        if (observations.isEmpty()) {
            out.println("<p>Brak zapisanych obserwacji w pamięci serwera.</p>");
        } else {
            out.println("<table border=\"1\" cellpadding=\"6\" cellspacing=\"0\">");
            out.println("<tr><th>Obiekt</th><th>Miejsce</th><th>Rok</th></tr>");
            for (Observation obs : observations) {
                out.println("<tr><td>" + HtmlUtils.escape(obs.getTitle()) + "</td><td>"
                        + HtmlUtils.escape(obs.getPlace()) + "</td><td>" + obs.getYear() + "</td></tr>");
            }
            out.println("</table>");
        }

        out.println("<p><a href=\"" + ctx + "/observations/add\">Dodaj kolejną obserwację</a> | <a href=\"" + ctx + "/\">Strona główna</a></p>");
        out.println("</body></html>");
    }
}
