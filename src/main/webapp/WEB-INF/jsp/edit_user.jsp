<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <title><fmt:message key="edit_user"/></title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h1><fmt:message key="edit_user"/></h1>
<form action="/users/edit" method="post">
    <ul class="wrapper">
        <li>
            <input type="hidden" name="id" value="${requestScope.user.id}">
        </li>
        <li class="form-row">
            <label><fmt:message key="firstname"/>
                <input type="text" name="firstName" minlength="1" value="${requestScope.user.firstName}">
            </label>
        </li>
        <li class="form-row">
            <label><fmt:message key="lastname"/>
                <input type="text" name="lastName" minlength="1" value="${requestScope.user.lastName}">
            </label>
        </li>
        <li class="form-row">
            <label><fmt:message key="email"/>
                <input type="text" name="email" value="${requestScope.user.email}">
            </label>
        </li>
        <li>
            <input type="hidden" name="password" value="${requestScope.user.password}">
        </li>
        <li class="form-row">
            <label><fmt:message key="role"/>
                <select name="role" required="required">
                    <option value=""><fmt:message key="select.role"/></option>
                    <option value="USER"><fmt:message key="role.user"/></option>
                    <option value="ADMIN"><fmt:message key="role.admin"/></option>
                    <option value="MANAGER"><fmt:message key="role.manager"/></option>
                </select>
            </label>
        </li>
        <li class="form-row">
            <label><fmt:message key="rating"/>
                <input type="number" name="rating" step="0.01" min="0.01" max="5.0" value="${requestScope.user.rating}">
            </label>
        </li>
        <li class="form-row">
            <input type="submit" name="Edit" value="<fmt:message key="edit"/>">
        </li>
    </ul>
</form>
</body>
</html>
