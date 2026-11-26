package pl.merito.project.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import pl.merito.project.model.ObservationRepository;
import java.io.IOException;

// Servlet przekazujący listę zapisanych obserwacji do widoku potwierdzenia JSP.
@WebServlet("/observations/added")
public class ObservationAddedServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("observations", ObservationRepository.findAll());
        req.getRequestDispatcher("/WEB-INF/views/observations/added.jsp").forward(req, resp);
    }
}
