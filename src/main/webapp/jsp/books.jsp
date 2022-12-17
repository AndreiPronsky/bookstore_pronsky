<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <meta charset="UTF-8">
    <title>Books</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<header></header>
<c:if test="${requestScope.books.isEmpty()}">
    <h2>No books found!</h2>
</c:if>
<c:if test="${!requestScope.books.isEmpty()}">
    <div class="paging">
        <a href="controller?command=books&page=1">First</a>
        <c:if test="${requestScope.page <= 1} ">
            <a>Previous</a>
        </c:if>
        <c:if test="${requestScope.page > 1}">
            <a href="controller?command=books&page=${requestScope.page - 1}">Previous</a>
        </c:if>
            ${requestScope.page}
        <c:if test="${requestScope.page < requestScope.total_pages}">
            <a href="controller?command=books&page=${requestScope.page + 1}">Next</a>
        </c:if>
        <c:if test="${requestScope.page >= requestScope.total_pages}">
            <a>Next</a>
        </c:if>
        <a href="controller?command=books&page=${requestScope.total_pages}">Last</a>
    </div>
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
                            <a href="controller?command=add_to_cart&id=${book.id}">Add to cart</a>
                        </div>
                    </div>
                </div>
            </div>

        </c:forEach>
    </div>
</div>
</body>
</html>