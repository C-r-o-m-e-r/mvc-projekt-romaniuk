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
import java.time.Year;
import java.util.LinkedHashMap;
import java.util.Map;

// Servlet obsługujący formularz dodawania obserwacji, walidację oraz przekierowanie PRG.
@WebServlet("/observations/add")
public class ObservationFormServlet extends HttpServlet {

    private static final int MIN_YEAR = 1900;
    private static final int MAX_TEXT_LENGTH = 100;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        renderForm(req, resp, Map.of(), "", "", "");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        req.setCharacterEncoding("UTF-8");
        String title = clean(req.getParameter("title"));
        String place = clean(req.getParameter("place"));
        String yearText = clean(req.getParameter("year"));

        Map<String, String> errors = validate(title, place, yearText);
        if (!errors.isEmpty()) {
            renderForm(req, resp, errors, title, place, yearText);
            return;
        }

        Observation observation = new Observation(title, place, Integer.parseInt(yearText));
        ObservationRepository.add(observation);

        HttpSession session = req.getSession();
        synchronized (session) {
            Integer count = (Integer) session.getAttribute("addedCount");
            session.setAttribute("addedCount", count == null ? 1 : count + 1);
            session.setAttribute("lastObservation", observation);
        }

        // Przekierowanie po udanym zapisie (wzorzec Post/Redirect/Get).
        resp.sendRedirect(req.getContextPath() + "/observations/added");
    }

    static String clean(String value) {
        return value == null ? "" : value.trim();
    }

    static Map<String, String> validate(String title, String place, String yearText) {
        Map<String, String> errors = new LinkedHashMap<>();

        if (title.isBlank()) {
            errors.put("title", "Obiekt jest wymagany.");
        } else if (title.length() > MAX_TEXT_LENGTH) {
            errors.put("title", "Nazwa obiektu może mieć maksymalnie " + MAX_TEXT_LENGTH + " znaków.");
        }

        if (place.isBlank()) {
            errors.put("place", "Miejsce obserwacji jest wymagane.");
        } else if (place.length() > MAX_TEXT_LENGTH) {
            errors.put("place", "Miejsce obserwacji może mieć maksymalnie " + MAX_TEXT_LENGTH + " znaków.");
        }

        if (yearText.isBlank()) {
            errors.put("year", "Rok obserwacji jest wymagany.");
        } else {
            try {
                int year = Integer.parseInt(yearText);
                int currentYear = Year.now().getValue();
                if (year < MIN_YEAR || year > currentYear) {
                    errors.put("year", "Rok musi mieścić się w zakresie " + MIN_YEAR + "–" + currentYear + ".");
                }
            } catch (NumberFormatException e) {
                errors.put("year", "Rok obserwacji musi być liczbą całkowitą.");
            }
        }

        return errors;
    }

    private void renderForm(HttpServletRequest req, HttpServletResponse resp,
                            Map<String, String> errors, String title, String place, String year) throws IOException {
        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        String ctx = req.getContextPath();

        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"pl\">");
        out.println("<head><meta charset=\"UTF-8\"><title>Dodaj obserwację – AstroDziennik</title></head>");
        out.println("<body>");
        out.println("<h1>Dodaj obserwację nieba</h1>");
        if (!errors.isEmpty()) {
            out.println("<p style=\"color:#a33830\"><strong>Popraw błędy w formularzu (" + errors.size() + "):</strong></p>");
        }
        out.println("<form method=\"post\" action=\"" + ctx + "/observations/add\">");
        renderField(out, "title", "Obiekt na niebie", title, errors);
        renderField(out, "place", "Miejsce obserwacji", place, errors);
        renderField(out, "year", "Rok obserwacji", year, errors);
        out.println("<button type=\"submit\">Zapisz obserwację</button>");
        out.println("</form>");
        out.println("<p><a href=\"" + ctx + "/observations/added\">Zapisane obserwacje</a> | <a href=\"" + ctx + "/\">Strona główna</a></p>");
        out.println("</body></html>");
    }

    private void renderField(PrintWriter out, String key, String label, String value, Map<String, String> errors) {
        out.print("<p><label>" + label + ": <input type=\"text\" name=\"" + key + "\" value=\"" + HtmlUtils.escape(value) + "\"></label>");
        String error = errors.get(key);
        if (error != null) {
            out.print(" <span style=\"color:#a33830\">" + HtmlUtils.escape(error) + "</span>");
        }
        out.println("</p>");
    }
}
