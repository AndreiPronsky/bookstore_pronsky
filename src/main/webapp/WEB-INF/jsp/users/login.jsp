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
<jsp:include page="../navbar.jsp"/>
<h1><spring:message code="login"/></h1>
<%--@elvariable id="loginDto" type=""--%>
<form:form method="post" action="/users/login" modelAttribute="loginDto">
    <input type="hidden" value="login">
    <label for="email-input"><spring:message code="email"/> : </label>
<%--    type="email"--%>
    <input id="email-input" name="username" >
    <br/>
    <label for="password-input"><spring:message code="password"/> : </label>
    <input id="password-input" name="password" type="password" minlength="4">
    <br/>
    <input type="submit" name="Login" value="<spring:message code="login"/>">
</form:form>
</body>
</html>
