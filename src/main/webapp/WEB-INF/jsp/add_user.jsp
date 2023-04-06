<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <title><fmt:message key="register"/></title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h1><fmt:message key="register"/></h1>
<form action="/users/add" method="post">
    <ul class="wrapper">
        <li class="form-row">
            <label><fmt:message key="firstname"/><input type="text" name="firstName" minlength="1"></label>
        </li>
        <li class="form-row">
            <label><fmt:message key="lastname"/><input type="text" name="lastName" minlength="1"></label>
        </li>
        <li class="form-row">
            <label><fmt:message key="email"/><input type="text" name="email" minlength="13"></label>
        </li>
        <li class="form-row">
            <label><fmt:message key="password"/><input type="password" name="password" minlength="8"></label>
        </li>
        <c:if test="${sessionScope.user.role == 'ADMIN'}">
            <li class="form-row">
                <label><fmt:message key="role"/>
                    <select name="role" required="required">
                        <option value=""><fmt:message key="select.role"/></option>
                        <option value="USER"><fmt:message key="role.USER"/></option>
                        <option value="ADMIN"><fmt:message key="role.ADMIN"/></option>
                        <option value="MANAGER"><fmt:message key="role.MANAGER"/></option>
                    </select>
                </label>
            </li>
            <li class="form-row">
                <label><fmt:message key="rating"/><input type="number" name="rating" step="0.01" min="0.01"
                                                         max="5.0"></label>
            </li>
        </c:if>
        <li class="form-row">
            <input type="submit" name="Create">
        </li>
    </ul>
</form>
</body>
</html>