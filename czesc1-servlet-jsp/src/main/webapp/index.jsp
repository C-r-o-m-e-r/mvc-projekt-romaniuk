<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/fragments/header.jspf" %>
<%-- Strona startowa prezentująca główne funkcje AstroDziennika oraz przykłady wyrażeń EL. --%>
<section class="intro">
    <p class="eyebrow">DZIENNIK OBSERWACJI NIEBA</p>
    <h1>AstroDziennik</h1>
    <p class="lead">Zapisuj obserwacje nocnego nieba: nazwę obiektu, miejsce obserwacji oraz rok.</p>
    <a class="button" href="${pageContext.request.contextPath}/observations/add">
        Dodaj obserwację <span aria-hidden="true">→</span>
    </a>
</section>

<section class="panel">
    <h2>Powitanie (servlet /start)</h2>
    <p><a href="${pageContext.request.contextPath}/start">Wyślij żądanie GET do /start</a></p>
    <form action="${pageContext.request.contextPath}/start" method="post" class="greeting">
        <label for="name">Twoje imię</label>
        <div>
            <input id="name" name="name" type="text" placeholder="Wpisz swoje imię">
            <button type="submit">Przywitaj się</button>
        </div>
    </form>
    <p class="muted">Przeglądarka (header): <c:out value="${header['User-Agent']}"/></p>
    <p class="muted">
        Kontekst aplikacji (pageContext): <c:out value="${pageContext.request.contextPath}"/>
        · Przykłady operatorów EL: 2 + 3 = ${2 + 3}, 10 &gt; 3 to ${10 gt 3}
    </p>
</section>
<%@ include file="/WEB-INF/views/fragments/footer.jspf" %>
