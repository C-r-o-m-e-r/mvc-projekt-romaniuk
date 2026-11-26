<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/fragments/header.jspf" %>
<%-- Formularz dodawania obserwacji: wartości pól odtwarzane przez ${param...}, błędy przez ${errors...}. --%>
<section class="form-layout">
    <div class="form-heading">
        <p class="eyebrow">TWÓJ DZIENNIK</p>
        <h1>Dodaj obserwację</h1>
        <p>Wpisz zaobserwowany obiekt na niebie, miejsce oraz rok obserwacji.</p>
    </div>

    <div class="panel form-panel">
        <c:if test="${not empty errors}">
            <div class="error-summary" role="alert">
                <strong>Sprawdź formularz (liczba błędów: ${empty errors ? 0 : errors.size()})</strong><br>
                Popraw pola oznaczone poniżej i zapisz ponownie.
            </div>
        </c:if>

        <form method="post" action="${pageContext.request.contextPath}/observations/add" novalidate>
            <label for="title">Obiekt na niebie</label>
            <input id="title"
                   name="title"
                   type="text"
                   class="${not empty errors.title ? 'input-error' : ''}"
                   value="<c:out value='${param.title}'/>"
                   placeholder="np. Perseidy"
                   aria-describedby="title-error">
            <span class="error" id="title-error"><c:out value="${errors.title}"/></span>

            <label for="place">Miejsce obserwacji</label>
            <input id="place"
                   name="place"
                   type="text"
                   class="${not empty errors.place ? 'input-error' : ''}"
                   value="<c:out value='${param.place}'/>"
                   placeholder="np. Bieszczady"
                   aria-describedby="place-error">
            <span class="error" id="place-error"><c:out value="${errors.place}"/></span>

            <label for="year">Rok obserwacji</label>
            <input id="year"
                   name="year"
                   type="text"
                   inputmode="numeric"
                   class="${not empty errors.year ? 'input-error' : ''}"
                   value="<c:out value='${param.year}'/>"
                   placeholder="np. 2025"
                   aria-describedby="year-error">
            <span class="error" id="year-error"><c:out value="${errors.year}"/></span>

            <button type="submit">Zapisz obserwację <span aria-hidden="true">→</span></button>
        </form>

        <p class="form-note">Obserwacje są przechowywane w pamięci serwera do czasu zatrzymania aplikacji.</p>
    </div>
</section>
<%@ include file="/WEB-INF/views/fragments/footer.jspf" %>
