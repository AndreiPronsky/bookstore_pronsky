<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title><spring:message code="register"/></title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h1><spring:message code="register"/></h1>
<form:form action="/users/add" method="post" modelAttribute="userDto">
<table>
    <tr>
        <td><spring:message code="firstname"/></td>
        <td><form:input path="firstName" type="text"/></td>
        <td><form:errors path="firstName"/></td>
    </tr>
    <tr>
        <td><spring:message code="lastname"/></td>
        <td><form:input path="lastName" type="text"/></td>
        <td><form:errors path="lastName"/></td>
    </tr>
    <tr>
        <td><spring:message code="email"/></td>
        <td><form:input path="email" type="email"/></td>
        <td><form:errors path="email"/></td>
    </tr>
    <tr>
        <td><spring:message code="password"/></td>
        <td><form:input path="password" type="password" minLength="8"/></td>
        <td><form:errors path="password"/></td>
    </tr>
    <c:if test="${sessionScope.user.role == 'ADMIN'}">
    <tr>
        <td><spring:message code="role"/></td>
        <td><form:select path="role" required="required">
            <option value=""><spring:message code="select.role"/></option>
            <option value="USER"><spring:message code="role.USER"/></option>
            <option value="ADMIN"><spring:message code="role.ADMIN"/></option>
            <option value="MANAGER"><spring:message code="role.MANAGER"/></option>
        </form:select></td>
    </tr>
    <tr>
        <td><spring:message code="rating"/></td>
        <td><form:input path="rating" type="number" min="0.00" step="0.01"/></td>
        <td><form:errors path="rating"/></td>
    </tr>
    </c:if>
</table>
    <li>
        <input type="submit" name="Create">
    </li>
    </form:form>
</body>
</html>