<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages"/>
<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>
<html>
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="books"/></title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<jsp:include page="searchbar.jsp"/>
<header></header>
<c:if test="${requestScope.books.isEmpty()}">
    <h2><fmt:message key="no_books_found"/></h2>
</c:if>
<c:if test="${!requestScope.books.isEmpty()}">
    <div class="paging">
        <c:if test="${requestScope.total_pages > 1}">
        <a href="controller?command=books&page=1"><fmt:message key="first"/></a>
        <c:if test="${requestScope.page <= 1} ">
            <a><fmt:message key="previous"/></a>
        </c:if>
        <c:if test="${requestScope.page > 1}">
            <a href="controller?command=books&page=${requestScope.page - 1}"><fmt:message key="previous"/></a>
        </c:if>
            ${requestScope.page}
        <c:if test="${requestScope.page < requestScope.total_pages}">
            <a href="controller?command=books&page=${requestScope.page + 1}"><fmt:message key="next"/></a>
        </c:if>
        <c:if test="${requestScope.page >= requestScope.total_pages}">
            <a><fmt:message key="next"/></a>
        </c:if>
        <a href="controller?command=books&page=${requestScope.total_pages}"><fmt:message key="last"/></a>
        </c:if>
    </div>
    <table>
        <thead>
<%--        <tr>--%>
<%--            <th><fmt:message key="id"/></th>--%>
<%--            <th><fmt:message key="author"/></th>--%>
<%--            <th><fmt:message key="title"/></th>--%>
<%--            <th><fmt:message key="genre"/></th>--%>
<%--            <th><fmt:message key="price"/></th>--%>
<%--        </tr>--%>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.books}" var="book">
            <div class="col-md-3 my-3">
                <div class="card w-100" style="width: 18rem;">
                    <img class="card-img-top" src="..." alt="card-image">
                    <div class="card-body">
                        <h5 class="card-author">${book.author}</h5>
                        <h5 class="card-title">
                            <a href="controller?command=book&id=${book.id}">${book.title}</a>
                        </h5>
                        <h6 class="price">${book.price}</h6>
                        <h6 class="genre">${book.genre}</h6>
                        <div class="mt-3 d-flex justify-content-between">
                            <a href="controller?command=add_to_cart&id=${book.id}"><fmt:message key="add_to_cart"/></a>
                        </div>
                    </div>
                </div>
            </div>
        </c:forEach>
        </tbody>
    </table>
</c:if>
</body>
</html>