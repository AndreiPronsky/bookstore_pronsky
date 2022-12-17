<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages"/>
<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>
<html>
<head>
    <title><fmt:message key="cart"/></title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<c:if test="${sessionScope.cart == null}">
    <h1><fmt:message key="your_cart_is_empty"/></h1>
</c:if>
<c:if test="${!sessionScope.cart.isEmpty && sessionScope.cart != null}">
    <form action="controller?command=cart" method="get">
        <table>
            <caption><fmt:message key="cart"/></caption>
            <thead>
            <tr>
                <td><fmt:message key="title"/></td>
                <td><fmt:message key="price"/></td>
                <td>
                    <label><fmt:message key="quantity"/><input type="number" name="quantity" step="1" min="0"
                                                               value="<fmt:message key="quantity"/>"></label>
                </td>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${sessionScope.cart}" var="cartItem">
                <tr>
                    <td>${cartItem.key.title}</td>
                    <td>${cartItem.key.price}</td>
                    <td>
                        <label><fmt:message key="quantity"/><input type="number" name="quantity" step="1" min="0"
                                                                   value="${cartItem.value}"></label>
                    </td>
                </tr>
            </c:forEach>
            <tr>
                <td>${sessionScope.cost}</td>
            </tr>
            </tbody>
        </table>
        <input type="submit" name="createOrder" value="<fmt:message key="order"/>">
    </form>
</c:if>
<footer></footer>
</body>
</html>
