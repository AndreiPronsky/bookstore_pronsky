<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="book"/></title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<header></header>
<article>
    <h1><spring:message code="order"/> : </h1>
    <h3><spring:message code="id"/> : <c:out value="${order.id}"/></h3>
    <h3><spring:message code="user"/> :
        <c:out value="${order.user.firstName}"/>
        <c:out value="${order.user.lastName}"/></h3>
    <h3><spring:message code="order_status"/> : <c:out value="${order.orderStatus}"/></h3>
    <h3><spring:message code="payment_method"/> : <c:out value="${order.paymentMethod}"/></h3>
    <h3><spring:message code="delivery_type"/> : <c:out value="${order.deliveryType}"/></h3>
    <h3><spring:message code="payment_status"/> : <c:out value="${order.paymentStatus}"/></h3>
    <a href="/orders/edit/${order.id}"><spring:message code="edit_order"/></a>
</article>
<footer></footer>
</body>
</html>