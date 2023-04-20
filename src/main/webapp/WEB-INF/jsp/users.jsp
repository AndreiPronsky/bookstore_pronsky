<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <meta charset="UTF-8">
    <title><spring:message code="bookstore"/></title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<header></header>
<c:if test="${users.isEmpty()}">
    <h2><spring:message code="users.not_found"/></h2>
</c:if>
<c:if test="${!users.isEmpty()}">
    <jsp:include page="pagination.jsp"/>
    <table>
        <caption><spring:message code="users"/></caption>
        <thead>
        <tr>
            <th><spring:message code="id"/></th>
            <th><spring:message code="firstname"/></th>
            <th><spring:message code="lastname"/></th>
            <th><spring:message code="email"/></th>
            <th><spring:message code="role"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${users}" var="user">
            <tr>
                <td><c:out value="${user.id}"/></td>
                <td><c:out value="${user.firstName}"/></td>
                <td><c:out value="${user.lastName}"/></td>
                <td><c:out value="${user.email}"/></td>
                <td><c:out value="${user.role}"/></td>
                <c:if test="${sessionScope.user.role.toString() == 'ADMIN'}">
                    <td>
                        <a href="/users/edit/${user.id}"><spring:message code="edit_user"/></a>
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