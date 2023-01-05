<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <title><fmt:message key="error"/></title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h1><fmt:message key="error"/></h1>
<h1><fmt:message key="something_went_wrong"/></h1>
<c:if test="${requestScope.messages.isEmpty()}">
    <c:forEach items="${requestScope.messages}" var="message">
        <p><c:out value="${message}"/></p>
    </c:forEach>
</c:if>
<p>${requestScope.message}</p>
</body>
</html>
