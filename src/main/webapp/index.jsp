<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages"/>
<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>
<html>
<head>
    <title><fmt:message key="bookstore"/></title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h1><fmt:message key="welcome.home"/></h1>
<jsp:include page="jsp/navbar.jsp"/>
<jsp:include page="jsp/searchbar.jsp"/>
<div class="container">
    <div class="card-header my-3"><fmt:message key="books"/></div>
    <c:forEach items="${requestScope.books}" var="book">
        <div class="row">
            <div class="col-md-3">
                <div class="card w-100" style="width: 18rem;">
                    <img class="card-img-top" src="..." alt="card-image">
                    <div class="card-body">
                        <h5 class="card-author">${book.author}</h5>
                        <h5 class="card-title"><a>href="controller?command=book&id=${book.id}"</a>>${book.title}</h5>
                        <h6 class="price">${book.price}</h6>
                        <h6 class="genre">${book.genre}</h6>
                        <div class="mt-3 d-flex justify-content-between">
                            <a href="controller?command=add_to_cart&id=${book.id}"><fmt:message key="add_to_cart"/></a>
                        </div>
                    </div>
                </div>
            </div>
        </div>
    </c:forEach>
</div>
</body>
</html>
