<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title><spring:message code="register"/></title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h1><spring:message code="register"/></h1>
<form action="/users/add" method="post">
    <ul class="wrapper">
        <li class="form-row">
            <label><spring:message code="firstname"/><input type="text" name="firstName" minlength="1"></label>
        </li>
        <li class="form-row">
            <label><spring:message code="lastname"/><input type="text" name="lastName" minlength="1"></label>
        </li>
        <li class="form-row">
            <label><spring:message code="email"/><input type="text" name="email" minlength="13"></label>
        </li>
        <li class="form-row">
            <label><spring:message code="password"/><input type="password" name="password" minlength="8"></label>
        </li>
        <li class="form-row">
            <input type="submit" name="Create" value="<spring:message code="register"/>">
        </li>
    </ul>
</form>
</body>
</html>