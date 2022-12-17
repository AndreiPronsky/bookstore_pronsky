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
    <form action="controller?command=cart" method="get">
        <table>
            <caption>Cart</caption>
            <thead>
            <tr>
                <td>${cartItem.key.title}</td>
                <td>${cartItem.key.price}</td>
                <td>
                <label>Quantity<input type="number" name="quantity" step="1" min="0" value="${cartItem.value}"></label>
                </td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${sessionScope.cart}" var="cartItem">
                <tr>
                    <td>${cartItem.key.title}</td>
                    <td>${cartItem.key.price}</td>
                    <td>
                        <label>Quantity<input type="number" name="quantity" step="1" min="0" value="${cartItem.value}"></label>
                    </td>
                </tr>
            </c:forEach>
            <tr><td>${sessionScope.cost}</td></tr>
            </tbody>
        </table>
        <input type="submit" name="createOrder" value="Order">
    </form>
</c:if>
<footer></footer>
    </body>
    </html>
