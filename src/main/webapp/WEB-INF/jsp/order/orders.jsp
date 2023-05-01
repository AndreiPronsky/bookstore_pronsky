<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <meta charset="UTF-8">
    <title><spring:message code="orders"/></title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<jsp:include page="orders_searchbar.jsp"/>
<header></header>
<c:if test="${orders.isEmpty()}">
    <h2><spring:message code="orders.not_found"/></h2>
</c:if>
<c:if test="${!orders.isEmpty()}">
    <jsp:include page="../pagination.jsp"/>
</c:if>
<table>
    <thead>
    <tr>
        <th><spring:message code="id"/></th>
        <th><spring:message code="user"/></th>
        <th><spring:message code="order_status"/></th>
        <th><spring:message code="payment_method"/></th>
        <th><spring:message code="payment_status"/></th>
        <th><spring:message code="delivery_type"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${orders}" var="order">
        <tr>
            <td><c:out value="${order.id}"/></td>
            <td><c:out value="${order.user.firstName}"/> <c:out value="${order.user.lastName}"/></td>
            <td><c:out value="${order.orderStatus}"/></td>
            <td><c:out value="${order.paymentMethod}"/></td>
            <td><c:out value="${order.paymentStatus}"/></td>
            <td><c:out value="${order.deliveryType}"/></td>
            <td>
                <a href="/orders/edit/${order.id}">
                    <spring:message code="edit_order"/></a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>