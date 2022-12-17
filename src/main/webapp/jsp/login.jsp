<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages"/>
<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>
<html>
<head>
    <title><fmt:message key="login"/></title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h1><fmt:message key="login"/></h1>
<form method="post" action="controller">
    <input name="command" type="hidden" value="login">
    <label for="email-input"><fmt:message key="email"/> : </label>
    <input id="email-input" name="email" type="email">
    <br/>
    <label for="password-input"><fmt:message key="password"/> : </label>
    <input id="password-input" name="password" type="password" minlength="4">
    <br/>
    <input type="submit" name="Login" value="<fmt:message key="login"/>">
</form>
</body>
</html>
