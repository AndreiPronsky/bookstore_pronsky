<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Cart</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<c:if test="${sessionScope.cart == null}">
    <h1>Your cart is empty!</h1>
</c:if>
<c:if test="${!sessionScope.cart.isEmpty && sessionScope.cart != null}">
    <form action="controller?command=confirm_order_form" method="post">
    <table>
        <caption>Cart</caption>
        <thead>
        <tr>
            <th>Title</th>
            <th>Price</th>
            <th>Quantity</th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${sessionScope.cart}" var="cartItem">
            <tr>
                <td>${cartItem.value.title}</td>
                <td>${cartItem.value.price}</td>
                <td>
                <label>Quantity<input type="number" name="quantity" step="1" min="0" value="${cartItem.value.quantity}"></label>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
            <input type="submit" name="createOrder" value="Order">
    </form>
</c:if>
<footer></footer>
    </body>
    </html>
