<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <meta charset="UTF-8">
    <title><spring:message code="books"/></title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<jsp:include page="../searchbar.jsp"/>
<header></header>
<c:if test="${books.isEmpty()}">
    <h2><spring:message code="books.not_found"/></h2>
</c:if>
<c:if test="${!books.isEmpty()}">
    <jsp:include page="../pagination.jsp"/>
</c:if>
<table>
    <caption><spring:message code="books"/></caption>
    <thead>
    <tr>
        <th></th>
        <th><spring:message code="author"/></th>
        <th><spring:message code="title"/></th>
        <th><spring:message code="price"/></th>
        <th><spring:message code="genre"/></th>
        <th></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${books}" var="book">
        <tr>
            <td>
                <img height="100" src="${book.filePath}" alt="book-image">
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
                <h6><spring:message code="available.${book.available}"/></h6>
            </td>

            <c:if test="${sessionScope.user.role.toString() == 'MANAGER'}">
                <td>
                    <a href="edit/${book.id}"><spring:message code="edit_book"/></a>
                </td>
            </c:if>
            <c:if test="${sessionScope.user.role.toString() == 'USER' || sessionScope.user == null}">
                <td>
                    <a href="/cart/add?id=${book.id}">
                        <img height="30" src="/serviceImages/cart.png" alt=<spring:message code="add_to_cart"/>></a>
                </td>
            </c:if>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>