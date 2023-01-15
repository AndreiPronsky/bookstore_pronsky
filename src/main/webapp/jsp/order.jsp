<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${sessionScope.lang != null}">
  <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>
<fmt:setBundle basename="messages"/>
<html>
<head>
  <title><fmt:message key="book"/></title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<header></header>
<article>
  <h1><fmt:message key="order"/> : </h1>
  <h3><fmt:message key="id"/> : <c:out value="${requestScope.order.id}"/></h3>
  <h3><fmt:message key="user"/> :
    <c:out value="${requestScope.order.user.firstName}"/>
    <c:out value="${requestScope.order.user.lastName}"/></h3>
  <h3><fmt:message key="order_status"/> : <c:out value="${requestScope.order.orderStatus}"/></h3>
  <h3><fmt:message key="payment_method"/> : <c:out value="${requestScope.order.paymentMethod}"/></h3>
  <h3><fmt:message key="delivery_type"/> : <c:out value="${requestScope.order.deliveryType}"/></h3>
  <h3><fmt:message key="payment_status"/> : <c:out value="${requestScope.order.paymentStatus}"/></h3>
  <a href="controller?command=edit_order_form&id=${requestScope.order.id}"><fmt:message key="edit_order"/></a>
</article>
<footer></footer>
</body>
</html>