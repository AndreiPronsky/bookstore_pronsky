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
    <title><fmt:message key="my_orders"/></title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<jsp:include page="searchbar.jsp"/>
<header></header>
<c:if test="${requestScope.orders.isEmpty()}">
    <h2><fmt:message key="orders.not_found"/></h2>
</c:if>
<c:if test="${!requestScope.orders.isEmpty()}">
    <table>
        <thead>
        <tr>
            <th><fmt:message key="order"/></th>
            <th><fmt:message key="title"/> <fmt:message key="price"/> <fmt:message key="quantity"/></th>
            <th><fmt:message key="cost"/></th>
            <th><fmt:message key="order_status"/></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.orders}" var="order">
            <tr>
                <td><c:out value="${order.id}"/></td>
                <td>
                    <c:forEach items="${order.items}" var="item">
                        <table>
                            <tr>
                                <td><c:out value="${item.book.title}"/></td>
                                <td><c:out value="${item.price}"/></td>
                                <td><c:out value="${item.quantity}"/></td>
                            </tr>
                        </table>
                    </c:forEach>
                </td>
                <td><c:out value="${order.cost}"/></td>
                <td><c:out value="${order.orderStatus}"/></td>
                <td>
                    <c:if test="${order.orderStatus == 'OPEN'}">
                        <a href="/orders/edit/${order.id}">
                            <fmt:message key="edit_order"/></a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
</body>
</html>