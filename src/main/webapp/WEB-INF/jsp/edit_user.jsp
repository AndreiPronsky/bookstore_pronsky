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
<jsp:include page="navbar.jsp"/>
<h1><spring:message code="edit_user"/></h1>
<form action="/users/edit" method="post">
    <ul class="wrapper">
        <li>
            <input type="hidden" name="id" value="${user.id}">
        </li>
        <li class="form-row">
            <label><spring:message code="firstname"/>
                <input type="text" name="firstName" minlength="1" value="${user.firstName}">
            </label>
        </li>
        <li class="form-row">
            <label><spring:message code="lastname"/>
                <input type="text" name="lastName" minlength="1" value="${user.lastName}">
            </label>
        </li>
        <li class="form-row">
            <label><spring:message code="email"/>
                <input type="text" name="email" value="${user.email}">
            </label>
        </li>
        <li>
            <input type="hidden" name="password" value="${user.password}">
        </li>
        <li class="form-row">
            <label><spring:message code="role"/>
                <select name="role" required="required">
                    <option value="${user.role}" selected="selected">
                        <spring:message code="role.${user.role}"/></option>
                    <option value=""><spring:message code="select.role"/></option>
                    <option value="USER"><spring:message code="role.USER"/></option>
                    <option value="ADMIN"><spring:message code="role.ADMIN"/></option>
                    <option value="MANAGER"><spring:message code="role.MANAGER"/></option>
                </select>
            </label>
        </li>
        <li class="form-row">
            <label><spring:message code="rating"/>
                <input type="number" name="rating" step="0.01" min="0.01" max="5.0" value="${user.rating}">
            </label>
        </li>
        <li class="form-row">
            <input type="submit" name="Edit" value="<spring:message code="edit"/>">
        </li>
    </ul>
</form>
</body>
</html>
