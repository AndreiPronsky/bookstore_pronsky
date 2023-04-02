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
<c:if test="${requestScope.books.isEmpty()}">
    <h2><fmt:message key="books.not_found"/></h2>
</c:if>
<c:if test="${!requestScope.books.isEmpty()}">
    <div class="paging">
        <c:if test="${requestScope.total_pages > 1}">
            <a href="all?page=1&page_size=${requestScope.pageSize}"><fmt:message key="first"/></a>
            <c:if test="${requestScope.page <= 1} ">
                <a><fmt:message key="previous"/></a>
            </c:if>
            <c:if test="${requestScope.page > 1}">
                <a href="all?page=${requestScope.page - 1}&page_size=${requestScope.pageSize}"><fmt:message key="previous"/></a>
            </c:if>
            ${requestScope.page}
            <c:if test="${requestScope.page < requestScope.total_pages}">
                <a href="all?page=${requestScope.page + 1}&page_size=${requestScope.pageSize}"><fmt:message key="next"/></a>
            </c:if>
            <a href="all?page=${requestScope.total_pages}&page_size=${requestScope.pageSize}"><fmt:message key="last"/></a>
        </c:if>
    </div>
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
    <c:forEach items="${requestScope.books}" var="book">
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
                    <a href="/cart/add?id=${book.id}&page=${requestScope.page}&page_size=${requestScope.pageSize}">
                        <img height="30" src="/serviceImages/cart.png" alt=<fmt:message key="add_to_cart"/>></a>
                </c:if>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>