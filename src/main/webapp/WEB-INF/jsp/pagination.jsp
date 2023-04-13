<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<html>

<body>
<div class="paging">
    <c:if test="${totalPages > 0}">
        <a href="${pageContext.request.contextPath}?page=0&page_size=5"><fmt:message key="first"/></a>
        <c:if test="${page <= 1} ">
            <a><fmt:message key="previous"/></a>
        </c:if>
        <c:if test="${page > 0}">
            <a href="${pageContext.request.contextPath}?page=${page - 1}&size=${size}"><fmt:message key="previous"/></a>
        </c:if>
        ${page+1}
        <c:if test="${page < totalPages-1}">
            <a href="${pageContext.request.contextPath}?page=${page + 1}&size=${size}"><fmt:message key="next"/></a>
        </c:if>
        <a href="${pageContext.request.contextPath}?page=${totalPages-1}&size=${size}"><fmt:message key="last"/></a>
    </c:if>
</div>
</body>
</html>
