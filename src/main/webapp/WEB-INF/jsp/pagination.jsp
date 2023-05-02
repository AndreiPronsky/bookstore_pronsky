<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<body>
<div class="paging">
    <c:if test="${totalPages > 0}">
        <a href="${pageContext.request.contextPath}?page=0&size=${size}&sort=id">
            <spring:message code="first"/></a>
        <c:if test="${page <= 1} ">
            <a><spring:message code="previous"/></a>
        </c:if>
        <c:if test="${page > 0}">
            <a href="${pageContext.request.contextPath}?page=${page - 1}&size=${size}&sort=id">
                <spring:message code="previous"/></a>
        </c:if>
        ${page+1}
        <c:if test="${page < totalPages-1}">
            <a href="${pageContext.request.contextPath}?page=${page + 1}&size=${size}&sort=id">
                <spring:message code="next"/></a>
        </c:if>
        <a href="${pageContext.request.contextPath}?page=${totalPages-1}&size=${size}&sort=id">
            <spring:message code="last"/></a>
    </c:if>
</div>
</body>
</html>
