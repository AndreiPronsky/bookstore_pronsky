<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title><spring:message code="login"/></title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h1><spring:message code="login"/></h1>
<form method="post" action="/users/login">
    <input type="hidden" value="login">
    <label for="email-input"><spring:message code="email"/> : </label>
    <input id="email-input" name="email" type="email">
    <br/>
    <label for="password-input"><spring:message code="password"/> : </label>
    <input id="password-input" name="password" type="password" minlength="4">
    <br/>
    <input type="submit" name="Login" value="<spring:message code="login"/>">
</form>
</body>
</html>
