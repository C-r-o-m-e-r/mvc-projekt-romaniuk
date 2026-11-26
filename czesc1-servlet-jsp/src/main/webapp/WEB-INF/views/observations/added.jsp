<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/fragments/header.jspf" %>
<%-- Widok potwierdzenia po przekierowaniu PRG: odczytuje ostatni wpis z sesji oraz listę obserwacji z repozytorium. --%>
<section class="panel narrow">
    <p class="eyebrow">TWÓJ DZIENNIK</p>
    <c:choose>
        <c:when test="${empty sessionScope.lastObservation}">
            <h1>Brak nowej obserwacji w sesji</h1>
            <p>Dodaj obserwację przez formularz, aby zobaczyć tutaj potwierdzenie zapisu.</p>
        </c:when>
        <c:otherwise>
            <h1>Obserwacja została zapisana</h1>
            <p>
                Ostatnio dodano:
                <strong><c:out value="${sessionScope.lastObservation.title}"/></strong>
                (<c:out value="${sessionScope.lastObservation.place}"/>, ${sessionScope.lastObservation.year}).
            </p>
            <p>Liczba obserwacji dodanych w tej sesji: <strong>${sessionScope.addedCount}</strong>.</p>
        </c:otherwise>
    </c:choose>

    <h2>Wszystkie zapisane obserwacje (${empty requestScope.observations ? 0 : requestScope.observations.size()})</h2>
    <c:choose>
        <c:when test="${empty requestScope.observations}">
            <p class="muted">Brak zapisanych obserwacji w pamięci serwera.</p>
        </c:when>
        <c:otherwise>
            <table class="observations-table">
                <thead>
                <tr>
                    <th>Obiekt</th>
                    <th>Miejsce</th>
                    <th>Rok</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach var="obs" items="${requestScope.observations}">
                    <tr>
                        <td><c:out value="${obs.title}"/></td>
                        <td><c:out value="${obs.place}"/></td>
                        <td>${obs.year}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </c:otherwise>
    </c:choose>

    <div class="actions">
        <a class="button" href="${pageContext.request.contextPath}/observations/add">
            Dodaj kolejną obserwację <span aria-hidden="true">→</span>
        </a>
        <a href="${pageContext.request.contextPath}/">Wróć na stronę główną</a>
    </div>
</section>
<%@ include file="/WEB-INF/views/fragments/footer.jspf" %>
