<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="messages"/>
</c:if>
<html>
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="cart"/></title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<jsp:include page="searchbar.jsp"/>
<header></header>
<c:if test="${sessionScope.cart == null}">
    <h1><fmt:message key="your_cart_is_empty"/></h1>
</c:if>
<c:if test="${!sessionScope.cart.isEmpty && sessionScope.cart != null}">
    <form action="controller?command=cart" method="post">
            <c:forEach items="${sessionScope.cart}" var="cartItem">
                <ul class="wrapper">
                    <li>${cartItem.key.title}</li>
                    <li>${cartItem.key.price}</li>
                    <li class="form-row">
                        <label>
                            <input type="number" name="quantity" step="1" min="0"
                                   value="${cartItem.value}"></label>
                    </li>
                </ul>
            </c:forEach>
            <li>
                <label><fmt:message key="cost"/> ${sessionScope.cost}</label>
            </li>
        <input type="submit" name="createOrder" value="<fmt:message key="order"/>">
    </form>
</c:if>
</body>
</html>
