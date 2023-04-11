<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="bookstore"/></title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<header></header>
<c:if test="${requestScope.users.isEmpty()}">
    <h2><fmt:message key="users.not_found"/></h2>
</c:if>
<c:if test="${!requestScope.users.isEmpty()}">
<jsp:include page="pagination.jsp"/>
    <table>
        <caption><fmt:message key="users"/></caption>
        <thead>
        <tr>
            <th><fmt:message key="id"/></th>
            <th><fmt:message key="firstname"/></th>
            <th><fmt:message key="lastname"/></th>
            <th><fmt:message key="email"/></th>
            <th><fmt:message key="role"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.users}" var="user">
            <tr>
                <td><c:out value="${user.id}"/></td>
                <td><c:out value="${user.firstName}"/></td>
                <td><c:out value="${user.lastName}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.role}"/></td>
                <c:if test="${sessionScope.user.role.toString() == 'ADMIN'}">
                    <td><a href="/users/edit/${user.id}"><fmt:message key="edit_user"/></a>
                    </td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
<footer></footer>
</body>
</html>