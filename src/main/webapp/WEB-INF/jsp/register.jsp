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
<%--@elvariable id="userDto" type=""--%>
<form:form action="/users/add" method="post" modelAttribute="userDto">
    <table>
        <tr>
            <td><spring:message code="firstname"/></td>
            <td><form:input type="text" path="firstName"/></td>
            <td><form:errors path="firstName"/></td>
        </tr>
        <tr>
            <td><spring:message code="lastname"/></td>
            <td><form:input type="text" path="lastName"/></td>
            <td><form:errors path="lastName"/></td>
        </tr>
        <tr>
            <td><spring:message code="email"/></td>
            <td><form:input type="email" path="email"/></td>
            <td><form:errors path="email"/></td>
        </tr>
        <tr>
            <td><spring:message code="password"/></td>
            <td><form:input type="password" path="password" minlength="8"/></td>
            <td><form:errors path="password"/></td>
        </tr>
    </table>
    <input type="submit" name="Create" value="<spring:message code="register"/>">
</form:form>
</body>
</html>