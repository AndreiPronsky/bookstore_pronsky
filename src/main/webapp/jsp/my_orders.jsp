<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages"/>
<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>
<html>
<head>
    <meta charset="UTF-8">
    <title><fmt:message key="my_orders"/></title>
    <link rel="stylesheet" href="css/style.css">
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
            <th><fmt:message key="title"/></th>
            <th><fmt:message key="price"/></th>
            <th><fmt:message key="quantity"/></th>
            <th><fmt:message key="cost"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${requestScope.orders}" var="order">
            <tr>
            <td rowspan="${order.value.size()}">${order.key.id}</td>
            <c:forEach items="${order.value}" var="item">
                <td>${item.value.title}</td>
                <td>${item.key.price}</td>
                <td>${item.key.quantity}</td>
                <td rowspan="${order.value.size()}">${order.key.cost}</td>
                </tr>
            </c:forEach>
        </c:forEach>
        </tbody>
    </table>
</c:if>