<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bookstore-pronsky</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<header></header>
<c:if test="${requestScope.books.isEmpty()}">
    <h2>No books found!</h2>
</c:if>
<c:if test="${!requestScope.books.isEmpty()}">
    <table>
        <caption>Books</caption>
        <thead>
        <tr>
            <th>Author</th>
            <th>Title</th>
            <th>Genre</th>
            <th>Price</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.books}" var="book">
            <tr>
                <td>${book.author}</td>
                <td><a href="controller?command=book&id=${book.id}">${book.title}</a></td>
                <td>${book.genre}</td>
                <td>${book.price}</td>
                <td><a href="controller?command=add_to_cart&id=${book.id}">Add to cart</a></td>
                <c:if test="${sessionScope.user.role.toString() == 'ADMIN'
                || sessionScope.user.role.toString() == 'MANAGER'}">
                <td><a href="controller?command=edit_book_form&id=${book.id}">Edit book</a></td>
                </c:if>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
<footer></footer>
</body>
</html>