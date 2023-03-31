<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <title><fmt:message key="edit_order"/></title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h1><fmt:message key="edit_order"/></h1>
<c:if test="${sessionScope.user.role.toString() == 'USER'}">
<form action="/orders/edit&id=${order.id}" method="post">
    </c:if>
        <table>
            <tbody>
            <c:if test="${sessionScope.user.role.toString() == 'USER'}">
                <c:forEach items="${order.items}" var="item">
                    <c:set var="total" value="${total + item.book.price * item.quantity }"/>
                    <tr>
                        <td><c:out value="${item.book.title}"/></td>
                        <td><c:out value="${item.book.price}"/></td>
                        <td>
                            <a href="/cart/edit?action=dec&id=${item.id }">-</a>
                            <label><fmt:message key="quantity"/>
                                <input type="number" name="quantity" step="1" min="0" value="${item.quantity}">
                            </label>
                            <a href="/cart/edit?action=inc&id=${item.id }">+</a>
                            <a href="/cart/edit?action=remove&id=${item.id }"><fmt:message
                                    key="remove"/></a>
                        </td>
                        <td>${item.book.price * item.quantity }</td>
                    </tr>
                </c:forEach>
            </c:if>
            <tr>
                <td>
                    <label><fmt:message key="delivery_type"/>
                        <select name="deliveryType" required="required">
                            <option value=""><fmt:message key="select.delivery_type"/></option>
                            <option value="COURIER"><fmt:message key="delivery_type.courier"/></option>
                            <option value="BIKE"><fmt:message key="delivery_type.bike"/></option>
                            <option value="CAR"><fmt:message key="delivery_type.car"/></option>
                            <option value="MAIL"><fmt:message key="delivery_type.mail"/></option>
                            <option value="SELF_PICKUP"><fmt:message key="delivery_type.self_pickup"/></option>
                        </select>
                    </label>
                </td>
            </tr>
            <tr>
                <td>
                    <label><fmt:message key="payment_method"/>
                        <select name="paymentMethod" required="required">
                            <option value=""><fmt:message key="select.payment_method"/></option>
                            <option value="CASH"><fmt:message key="payment_method.cash"/></option>
                            <option value="CARD"><fmt:message key="payment_method.card"/></option>
                            <option value="BANK_TRANSFER"><fmt:message key="payment_method.bank_transfer"/></option>
                        </select>
                    </label>
                </td>
            </tr>
            <c:if test="${sessionScope.user.role.toString() == 'ADMIN'}">
                <tr>
                    <td>
                        <label><fmt:message key="order_status"/>
                            <select name="orderStatus" required="required">
                                <option value=""><fmt:message key="select.order_status"/></option>
                                <option value="OPEN"><fmt:message key="order_status.open"/></option>
                                <option value="CONFIRMED"><fmt:message key="order_status.confirmed"/></option>
                                <option value="COMPLETED"><fmt:message key="order_status.completed"/></option>
                                <option value="CANCELLED"><fmt:message key="order_status.cancelled"/></option>
                            </select>
                        </label>
                    </td>
                </tr>
                <tr>
                    <td>
                        <label><fmt:message key="payment_status"/>
                            <select name="paymentStatus" required="required">
                                <option value=""><fmt:message key="select.payment_status"/></option>
                                <option value="UNPAID"><fmt:message key="payment_status.unpaid"/></option>
                                <option value="PAID"><fmt:message key="payment_status.paid"/></option>
                                <option value="FAILED"><fmt:message key="payment_status.failed"/></option>
                                <option value="REFUNDED"><fmt:message key="payment_status.refunded"/></option>
                            </select>
                        </label>
                    </td>
                </tr>
            </c:if>
            <c:if test="${sessionScope.user.role.toString() == 'USER'}">
                <td>
                    <label><fmt:message key="cost"/> = <c:out value="${total}"/></label>
                </td>
            </c:if>
            </tbody>
        </table>
        <input type="submit" name="updateOrder" value="<fmt:message key="edit"/>">
    </form>
</body>
</html>