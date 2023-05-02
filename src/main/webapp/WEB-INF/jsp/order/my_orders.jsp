<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <meta charset="UTF-8">
    <title><spring:message code="my_orders"/></title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<jsp:include page="../searchbar.jsp"/>
<jsp:include page="../pagination.jsp"/>
<header></header>
<c:if test="${orders.isEmpty()}">
    <h2><spring:message code="orders.not_found"/></h2>
</c:if>
<c:if test="${!orders.isEmpty()}">
    <table>
        <thead>
        <tr>
            <th><spring:message code="order"/></th>
            <th>
                <spring:message code="title"/>
                <spring:message code="price"/>
                <spring:message code="quantity"/>
            </th>
            <th><spring:message code="cost"/></th>
            <th><spring:message code="order_status"/></th>
            <th></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${orders}" var="order">
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
                            <spring:message code="edit_order"/></a>
                    </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</c:if>
</body>
</html>