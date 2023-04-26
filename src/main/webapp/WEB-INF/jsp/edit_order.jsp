<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title><spring:message code="edit_order"/></title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h1><spring:message code="edit_order"/></h1>
<%--@elvariable id="orderDto" type=""--%>
<form:form action="/orders/edit" method="post" modelAttribute="orderDto">
    <form:input type="hidden" path="id" value="${orderDto.id}"/>
    <c:if test="${sessionScope.user.role.toString() == 'USER'}">
    </c:if>
    <table>
        <tbody>
        <c:if test="${sessionScope.user.role.toString() == 'USER'}">
            <c:forEach items="${orderDto.items}" var="item">
                <c:set var="total" value="${total + item.book.price * item.quantity }"/>
                <tr>
                    <td><c:out value="${item.book.title}"/></td>
                    <td><c:out value="${item.book.price}"/></td>
                    <td>
                        <a href="${pageContext.request.contextPath}edit_IQ?action=dec&id=${item.id}">-</a>
                        <spring:message code="quantity"/>
                        <label>
                            <input type="number" name="quantity" step="1" min="0" value="${item.quantity}"/>
                        </label>
                        <a href="${pageContext.request.contextPath}edit_IQ?action=inc&id=${item.id}">+</a>
                        <a href="${pageContext.request.contextPath}edit_IQ?action=remove&id=${item.id}"><spring:message
                                code="remove"/></a>
                    </td>
                    <td>
                        <spring:message code="price"/>
                        <label>
                            <input type="number" name="price" readonly="readonly"
                                        value="${item.book.price * item.quantity}"/>
                        </label>
                    </td>
                </tr>
            </c:forEach>
        </c:if>
        <tr>
            <td><spring:message code="delivery_type"/>
                <form:select path="deliveryType" required="required">
                    <option value="${orderDto.deliveryType}" selected="selected">
                        <spring:message code="delivery_type.${orderDto.deliveryType}"/></option>
                    <option value=""><spring:message code="select.delivery_type"/></option>
                    <option value="COURIER"><spring:message code="delivery_type.COURIER"/></option>
                    <option value="BIKE"><spring:message code="delivery_type.BIKE"/></option>
                    <option value="CAR"><spring:message code="delivery_type.CAR"/></option>
                    <option value="MAIL"><spring:message code="delivery_type.MAIL"/></option>
                    <option value="SELF_PICKUP"><spring:message code="delivery_type.SELF_PICKUP"/></option>
                </form:select>
            </td>
        </tr>
        <tr>
            <td><spring:message code="payment_method"/>
                <form:select path="paymentMethod" required="required">
                    <option value="${orderDto.paymentMethod}" selected="selected">
                        <spring:message code="payment_method.${orderDto.paymentMethod}"/></option>
                    <option value=""><spring:message code="select.payment_method"/></option>
                    <option value="CASH"><spring:message code="payment_method.CASH"/></option>
                    <option value="CARD"><spring:message code="payment_method.CARD"/></option>
                    <option value="BANK_TRANSFER"><spring:message code="payment_method.BANK_TRANSFER"/></option>
                </form:select>
            </td>
        </tr>
        <c:if test="${sessionScope.user.role.toString() == 'ADMIN'}">
            <tr>
                <td><spring:message code="order_status"/>
                    <form:select path="orderStatus" required="required">
                        <option value="${orderDto.orderStatus}" selected="selected">
                            <spring:message code="order_status.${orderDto.orderStatus}"/></option>
                        <option value=""><spring:message code="select.order_status"/></option>
                        <option value="OPEN"><spring:message code="order_status.OPEN"/></option>
                        <option value="CONFIRMED"><spring:message code="order_status.CONFIRMED"/></option>
                        <option value="COMPLETED"><spring:message code="order_status.COMPLETED"/></option>
                        <option value="CANCELLED"><spring:message code="order_status.CANCELLED"/></option>
                    </form:select>
                </td>
            </tr>
            <tr>
                <td><spring:message code="payment_status"/>
                    <form:select path="paymentStatus" required="required">
                        <option value="${orderDto.paymentStatus}" selected="selected">
                            <spring:message code="payment_status.${orderDto.paymentStatus}"/></option>
                        <option value=""><spring:message code="select.payment_status"/></option>
                        <option value="UNPAID"><spring:message code="payment_status.UNPAID"/></option>
                        <option value="PAID"><spring:message code="payment_status.PAID"/></option>
                        <option value="FAILED"><spring:message code="payment_status.FAILED"/></option>
                        <option value="REFUNDED"><spring:message code="payment_status.REFUNDED"/></option>
                    </form:select>
                </td>
            </tr>
        </c:if>
        <c:if test="${sessionScope.user.role.toString() == 'USER'}">
            <tr>
                <td><spring:message code="cost"/></td>
                <td><form:input path="cost" type="number" readonly="true" value="${total}" step="0.01" min="0.01"/></td>
                <td><form:errors path="cost"/></td>
            </tr>
        </c:if>
        </tbody>
    </table>
    <input type="submit" name="updateOrder" value="<spring:message code="edit"/>">
</form:form>
</body>
</html>