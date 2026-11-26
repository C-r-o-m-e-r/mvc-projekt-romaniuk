<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ include file="/WEB-INF/views/fragments/header.jspf" %>
<%-- Widok powitania obsługujący odpowiedzi GET i POST z AppServlet. --%>
<section class="panel narrow">
    <p class="eyebrow">POWITANIE</p>
    <h1>
        <c:choose>
            <c:when test="${requestScope.post and requestScope.hasName}">
                Cześć, <c:out value="${param.name}"/>!
            </c:when>
            <c:when test="${requestScope.post}">
                Cześć, Gościu!
            </c:when>
            <c:otherwise>
                Witaj w AstroDzienniku!
            </c:otherwise>
        </c:choose>
    </h1>
    <p>To jest odpowiedź na żądanie <strong>${requestScope.post ? 'POST' : 'GET'}</strong>.</p>
    <p><a href="${pageContext.request.contextPath}/">Wróć na stronę główną</a></p>
</section>
<%@ include file="/WEB-INF/views/fragments/footer.jspf" %>
