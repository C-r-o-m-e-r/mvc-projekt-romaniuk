package pl.merito.project.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import pl.merito.project.model.Observation;
import pl.merito.project.model.ObservationRepository;
import java.io.IOException;
import java.time.Year;
import java.util.LinkedHashMap;
import java.util.Map;

// Servlet obsługujący formularz obserwacji, walidację, sesję oraz przekierowanie PRG.
@WebServlet("/observations/add")
public class ObservationFormServlet extends HttpServlet {

    private static final String VIEW = "/WEB-INF/views/observations/form.jsp";
    private static final int MIN_YEAR = 1900;
    private static final int MAX_TEXT_LENGTH = 100;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String title = clean(req.getParameter("title"));
        String place = clean(req.getParameter("place"));
        String yearText = clean(req.getParameter("year"));

        Map<String, String> errors = validate(title, place, yearText);
        if (!errors.isEmpty()) {
            req.setAttribute("errors", errors);
            req.getRequestDispatcher(VIEW).forward(req, resp);
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

        // Przekierowanie po poprawnym zapisie (wzorzec Post/Redirect/Get).
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
}
