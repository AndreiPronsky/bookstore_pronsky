<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
  <title><spring:message code="cart"/></title>
  <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<c:if test="${sessionScope.cart == null || sessionScope.cart.isEmpty()}">
  <h1><spring:message code="your_cart_is_empty"/></h1>
</c:if>
<c:if test="${sessionScope.cart != null && !sessionScope.cart.isEmpty()}">
  <h1><spring:message code="cart"/></h1>
  <form action="/orders/confirm" method="get">
    <table>
      <tbody>
      <c:forEach items="${sessionScope.cart}" var="cartItem">
        <c:set var="total" value="${total + cartItem.key.price * cartItem.value }"/>
        <tr>
          <td><c:out value="${cartItem.key.title}"/></td>
          <td><c:out value="${cartItem.key.price}"/></td>
          <td>
            <label><spring:message code="quantity"/>
              <a href="/cart/edit?action=dec&id=${cartItem.key.id }">-</a>
              <input type="number" name="quantity" step="1" min="0" value="${cartItem.value}">
            </label>
            <a href="/cart/edit?action=inc&id=${cartItem.key.id }">+</a>
            <a href="/cart/edit?action=remove&id=${cartItem.key.id }"><spring:message
                    code="remove"/></a>
          </td>
          <td><c:out value="${cartItem.key.price * cartItem.value }"/></td>
        </tr>
      </c:forEach>
      <td>
        <label><spring:message code="cost"/> = <c:out value="${total}"/></label>
      </td>
      </tbody>
    </table>
    <input type="submit" name="proceed" value=<spring:message code="proceed.to.purchasing"/>>
  </form>
</c:if>
</body>
</html>