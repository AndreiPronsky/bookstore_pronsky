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
    <title><fmt:message key="orders"/></title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<jsp:include page="orders_searchbar.jsp"/>
<header></header>
<c:if test="${requestScope.orders.isEmpty()}">
    <h2><fmt:message key="orders.not_found"/></h2>
</c:if>
<c:if test="${!requestScope.orders.isEmpty()}">
    <div class="paging">
        <c:if test="${requestScope.total_pages > 1}">
            <a href="controller?command=orders&page=1"><fmt:message key="first"/></a>
            <c:if test="${requestScope.page <= 1} ">
                <a><fmt:message key="previous"/></a>
            </c:if>
            <c:if test="${requestScope.page > 1}">
                <a href="controller?command=orders&page=${requestScope.page - 1}"><fmt:message key="previous"/></a>
            </c:if>
            ${requestScope.page}
            <c:if test="${requestScope.page < requestScope.total_pages}">
                <a href="controller?command=orders&page=${requestScope.page + 1}"><fmt:message key="next"/></a>
            </c:if>
            <a href="controller?command=orders&page=${requestScope.total_pages}"><fmt:message key="last"/></a>
        </c:if>
    </div>
</c:if>
<table>
    <thead>
    <tr>
        <th><fmt:message key="id"/></th>
        <th><fmt:message key="user"/></th>
        <th><fmt:message key="order_status"/></th>
        <th><fmt:message key="payment_method"/></th>
        <th><fmt:message key="payment_status"/></th>
        <th><fmt:message key="delivery_type"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${requestScope.orders}" var="order">
        <tr>
            <td><c:out value="${order.key.id}"/></td>
            <td><c:out value="${order.value.firstName}"/> <c:out value="${order.value.lastName}"/></td>
            <td><c:out value="${order.key.orderStatus}"/></td>
            <td><c:out value="${order.key.paymentMethod}"/></td>
            <td><c:out value="${order.key.paymentStatus}"/></td>
            <td><c:out value="${order.key.deliveryType}"/></td>
            <td>
                <a href="controller?command=edit_order_form&id=${order.key.id}">
                    <fmt:message key="edit_order"/></a>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
</body>
</html>