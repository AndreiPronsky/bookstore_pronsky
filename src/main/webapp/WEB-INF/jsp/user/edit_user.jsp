<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title><spring:message code="edit_user"/></title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<h1><spring:message code="edit_user"/></h1>
<form:form action="/users/edit" method="post" modelAttribute="userDto">
    <form:input type="hidden" path="id" value="${userDto.id}"/>
    <table>
        <tr>
            <td><spring:message code="firstname"/></td>
            <td><form:input type="text" path="firstName" minlength="1" value="${userDto.firstName}"/></td>
            <td><form:errors path="firstName"/></td>
        </tr>
        <tr>
            <td><spring:message code="lastname"/></td>
            <td><form:input type="text" path="lastName" minlength="1" value="${userDto.lastName}"/></td>
            <td><form:errors path="lastName"/></td>
        </tr>
        <tr>
            <td><spring:message code="email"/></td>
            <td><form:input type="email" path="email" value="${userDto.email}"/></td>
            <td><form:errors path="email"/></td>
        </tr>
        <tr>
            <td><spring:message code="password"/></td>
            <td><form:input type="password" path="password" value="${userDto.password}"/></td>
            <td><form:errors path="password"/></td>
        </tr>
        <tr>
            <td><spring:message code="role"/>
                <form:select path="role" required="required">
                    <option value="${userDto.role}" selected="selected">
                        <spring:message code="role.${userDto.role}"/></option>
                    <option value=""><spring:message code="select.role"/></option>
                    <option value="USER"><spring:message code="role.USER"/></option>
                    <option value="ADMIN"><spring:message code="role.ADMIN"/></option>
                    <option value="MANAGER"><spring:message code="role.MANAGER"/></option>
                </form:select>
            </td>
        </tr>
        <tr>
            <td><spring:message code="rating"/></td>
            <td><form:input type="number" path="rating" value="${userDto.rating}" step="0.01"/></td>
            <td><form:errors path="rating"/></td>
        </tr>
    </table>
    <li class="form-row">
        <input type="submit" name="Edit" value="<spring:message code="edit"/>">
    </li>
</form:form>
</body>
</html>
