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
<p>Status: ${status}</p>
<p>Reason: ${reason}</p>
</body>
</html>