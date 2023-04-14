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
    <title><fmt:message key="books"/></title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<jsp:include page="searchbar.jsp"/>
<header></header>
<c:if test="${books.isEmpty()}">
    <h2><fmt:message key="books.not_found"/></h2>
</c:if>
<c:if test="${!books.isEmpty()}">
<jsp:include page="pagination.jsp"/>
</c:if>
<table>
    <caption><fmt:message key="books"/></caption>
    <thead>
    <tr>
        <th></th>
        <th><fmt:message key="author"/></th>
        <th><fmt:message key="title"/></th>
        <th><fmt:message key="price"/></th>
        <th><fmt:message key="genre"/></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${books}" var="book">
        <tr>
            <td>
                <img height="100" src="/coverImages/${book.id}.png" alt="book-image">
            </td>
            <td>
                <h5><c:out value="${book.author}"/></h5>
            </td>
            <td>
                <h5><a href="${book.id}"><c:out value="${book.title}"/></a></h5>
            </td>
            <td>
                <h6><c:out value="${book.price}"/></h6>
            </td>
            <td>
                <h6><c:out value="${book.genre}"/></h6>
            </td>
            <td>
                <c:if test="${sessionScope.user.role.toString() == 'MANAGER'}">
                    <a href="edit/${book.id}"><fmt:message key="edit_book"/></a>
                </c:if>
                <c:if test="${sessionScope.user.role.toString() == 'USER' || sessionScope.user == null}">
                    <a href="/cart/add?id=${book.id}">
                        <img height="30" src="/serviceImages/cart.png" alt=<fmt:message key="add_to_cart"/>></a>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>