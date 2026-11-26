package pl.merito.project.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

// Servlet startowy przekazujący sterowanie do widoku JSP w WEB-INF/views.
@WebServlet("/start")
public class AppServlet extends HttpServlet {

    private static final String VIEW = "/WEB-INF/views/start.jsp";

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher(VIEW).forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        String name = req.getParameter("name");
        req.setAttribute("post", true);
        req.setAttribute("hasName", name != null && !name.isBlank());
        req.getRequestDispatcher(VIEW).forward(req, resp);
    }
}
