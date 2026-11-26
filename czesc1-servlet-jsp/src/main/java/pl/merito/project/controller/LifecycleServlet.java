package pl.merito.project.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

// Servlet demonstrujący cykl życia: init(), service()/doGet() oraz destroy().
@WebServlet(urlPatterns = "/lifecycle", loadOnStartup = 1)
public class LifecycleServlet extends HttpServlet {

    private static final DateTimeFormatter TIME_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    // Jedna instancja servletu obsługuje wiele wątków, dlatego licznik korzysta z AtomicInteger.
    private final AtomicInteger counter = new AtomicInteger();
    private volatile String initializedAt = "";

    @Override
    public void init() throws ServletException {
        super.init();
        initializedAt = LocalDateTime.now().format(TIME_FORMAT);
        getServletContext().log("LifecycleServlet: init() – utworzono instancję servletu (" + initializedAt + ")");
    }

    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        getServletContext().log("LifecycleServlet: service() – obsługa metody " + req.getMethod());
        super.service(req, resp);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int count = counter.incrementAndGet();
        getServletContext().log("LifecycleServlet: doGet() – żądanie nr " + count);

        resp.setContentType("text/html;charset=UTF-8");
        PrintWriter out = resp.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html lang=\"pl\">");
        out.println("<head><meta charset=\"UTF-8\"><title>Cykl życia servletu – AstroDziennik</title></head>");
        out.println("<body>");
        out.println("<h1>Cykl życia servletu</h1>");
        out.println("<p>Czas uruchomienia (init): " + HtmlUtils.escape(initializedAt) + "</p>");
        out.println("<p>Liczba obsłużonych żądań (doGet): " + count + "</p>");
        out.println("<p>Komunikaty z metod init(), service(), doGet() oraz destroy() zapisywane są w logach Tomcata.</p>");
        out.println("<p><a href=\"" + req.getContextPath() + "/\">Powrót na stronę główną</a></p>");
        out.println("</body></html>");
    }

    @Override
    public void destroy() {
        getServletContext().log("LifecycleServlet: destroy() – usuwanie servletu, obsłużono żądań: " + counter.get());
        super.destroy();
    }
}
