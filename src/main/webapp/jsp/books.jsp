<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Bookstore-pronsky</title>
</head>
<body>
<header></header>
<c:if test="${requestScope.books.isEmpty()}">
    <h2>No books found!</h2>
</c:if>
<c:if test="${!requestScope.books.isEmpty()}">
    <table>
        <caption>Books</caption>
        <thead>
        <tr>
            <th>Id</th>
            <th>Author</th>
            <th>Title</th>
            <th>Genre</th>
            <th>Price</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.books}" var="book">
            <tr>
                <td>${book.id}</td>
                <td>${book.author}</td>
                <td><a href="/command=book&id=${book.id}">${book.title}</a></td>
                <td>${book.genre}</td>
                <td>${book.price}</td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
<footer></footer>
</body>
</html>