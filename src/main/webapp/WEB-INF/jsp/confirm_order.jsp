<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title><spring:message code="confirm_order"/></title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<c:if test="${sessionScope.cart == null || sessionScope.cart.isEmpty()}">
    <h1><spring:message code="your_cart_is_empty"/></h1>
</c:if>
<c:if test="${sessionScope.cart != null && !sessionScope.cart.isEmpty()}">
    <h1><spring:message code="confirm_order"/></h1>
    <form action="/orders/confirm" method="post">
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
                <label><spring:message code="delivery_type"/>
                    <select name="deliveryType" required="required">
                        <option value=""><spring:message code="select.delivery_type"/></option>
                        <option value="COURIER"><spring:message code="delivery_type.COURIER"/></option>
                        <option value="BIKE"><spring:message code="delivery_type.BIKE"/></option>
                        <option value="CAR"><spring:message code="delivery_type.CAR"/></option>
                        <option value="MAIL"><spring:message code="delivery_type.MAIL"/></option>
                        <option value="SELF_PICKUP"><spring:message code="delivery_type.SELF_PICKUP"/></option>
                    </select>
                </label>
            </td>
            <td>
                <label><spring:message code="payment_method"/>
                    <select name="paymentMethod" required="required">
                        <option value=""><spring:message code="select.payment_method"/></option>
                        <option value="CASH"><spring:message code="payment_method.CASH"/></option>
                        <option value="CARD"><spring:message code="payment_method.CARD"/></option>
                        <option value="BANK_TRANSFER"><spring:message code="payment_method.BANK_TRANSFER"/></option>
                    </select>
                </label>
            </td>
            <td>
                <label><spring:message code="cost"/> = <c:out value="${total}"/></label>
            </td>
            </tbody>
        </table>
        <input type="submit" name="createOrder" value="Order">
    </form>
</c:if>
</body>
</html>
