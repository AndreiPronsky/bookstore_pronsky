<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="edit_order"/></title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h1><spring:message code="edit_order"/></h1>
<form action="/orders/edit" method="post">
    <c:if test="${sessionScope.user.role.toString() == 'USER'}">
    </c:if>
    <table>
        <tbody>
        <c:if test="${sessionScope.user.role.toString() == 'USER'}">
            <c:forEach items="${sessionScope.orderDto.items}" var="item">
                <c:set var="total" value="${total + item.book.price * item.quantity }"/>
                <tr>
                    <td><c:out value="${item.book.title}"/></td>
                    <td><c:out value="${item.book.price}"/></td>
                    <td>
                        <a href="${pageContext.request.contextPath}edit_IQ?action=dec&id=${item.id}">-</a>
                        <label><spring:message code="quantity"/>
                            <input type="number" name="quantity" step="1" min="0" value="${item.quantity}">
                        </label>
                        <a href="${pageContext.request.contextPath}edit_IQ?action=inc&id=${item.id}">+</a>
                        <a href="${pageContext.request.contextPath}edit_IQ?action=remove&id=${item.id}"><spring:message
                                code="remove"/></a>
                    </td>
                    <td>
                        <label>
                                <spring:message code="price"/>
                            <input type="number" name="price" readonly="readonly"
                                   value="${item.book.price * item.quantity}">
                    </td>
                </tr>
            </c:forEach>
        </c:if>
        <tr>
            <td>
                <label><spring:message code="delivery_type"/>
                    <select name="deliveryType" required="required">
                        <option value="${sessionScope.orderDto.deliveryType}" selected="selected">
                            <spring:message code="delivery_type.${sessionScope.orderDto.deliveryType}"/></option>
                        <option value=""><spring:message code="select.delivery_type"/></option>
                        <option value="COURIER"><spring:message code="delivery_type.COURIER"/></option>
                        <option value="BIKE"><spring:message code="delivery_type.BIKE"/></option>
                        <option value="CAR"><spring:message code="delivery_type.CAR"/></option>
                        <option value="MAIL"><spring:message code="delivery_type.MAIL"/></option>
                        <option value="SELF_PICKUP"><spring:message code="delivery_type.SELF_PICKUP"/></option>
                    </select>
                </label>
            </td>
        </tr>
        <tr>
            <td>
                <label><spring:message code="payment_method"/>
                    <select name="paymentMethod" required="required">
                        <option value="${sessionScope.orderDto.paymentMethod}" selected="selected">
                            <spring:message code="payment_method.${sessionScope.orderDto.paymentMethod}"/></option>
                        <option value=""><spring:message code="select.payment_method"/></option>
                        <option value="CASH"><spring:message code="payment_method.CASH"/></option>
                        <option value="CARD"><spring:message code="payment_method.CARD"/></option>
                        <option value="BANK_TRANSFER"><spring:message code="payment_method.BANK_TRANSFER"/></option>
                    </select>
                </label>
            </td>
        </tr>
        <c:if test="${sessionScope.user.role.toString() == 'ADMIN'}">
            <tr>
                <td>
                    <label><spring:message code="order_status"/>
                        <select name="orderStatus" required="required">
                            <option value="${sessionScope.orderDto.orderStatus}" selected="selected">
                                <spring:message code="order_status.${sessionScope.orderDto.orderStatus}"/></option>
                            <option value=""><spring:message code="select.order_status"/></option>
                            <option value="OPEN"><spring:message code="order_status.OPEN"/></option>
                            <option value="CONFIRMED"><spring:message code="order_status.CONFIRMED"/></option>
                            <option value="COMPLETED"><spring:message code="order_status.COMPLETED"/></option>
                            <option value="CANCELLED"><spring:message code="order_status.CANCELLED"/></option>
                        </select>
                    </label>
                </td>
            </tr>
            <tr>
                <td>
                    <label><spring:message code="payment_status"/>
                        <select name="paymentStatus" required="required">
                            <option value="${sessionScope.orderDto.paymentStatus}" selected="selected">
                                <spring:message code="payment_status.${sessionScope.orderDto.paymentStatus}"/></option>
                            <option value=""><spring:message code="select.payment_status"/></option>
                            <option value="UNPAID"><spring:message code="payment_status.UNPAID"/></option>
                            <option value="PAID"><spring:message code="payment_status.PAID"/></option>
                            <option value="FAILED"><spring:message code="payment_status.FAILED"/></option>
                            <option value="REFUNDED"><spring:message code="payment_status.REFUNDED"/></option>
                        </select>
                    </label>
                </td>
            </tr>
        </c:if>
        <c:if test="${sessionScope.user.role.toString() == 'USER'}">
            <td>
                <label><spring:message code="cost"/> = <c:out value="${total}"/></label>
            </td>
        </c:if>
        </tbody>
    </table>
    <input type="submit" name="updateOrder" value="<spring:message code="edit"/>">
</form>
</body>
</html>