<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages"/>
<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>
<html>
<head>
    <title><fmt:message key="confirm_order"/></title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h1><fmt:message key="confirm_order"/></h1>
<form action="controller?command=confirm_order" method="post">
    <table>
        <caption><fmt:message key="order"/></caption>
        <thead>
        <tr>
            <th><fmt:message key="title"/></th>
            <th><fmt:message key="price"/></th>
            <th><fmt:message key="quantity"/></th>
        </tr>
        </thead>
        <tbody>
        <c:forEach items="${sessionScope.cart}" var="cartItem">
            <tr>
                <td>${cartItem.key.title}</td>
                <td>${cartItem.key.price}</td>
                <td>
                    <label><fmt:message key="quantity"/><input type="submit" name="quantity" step="1" min="0" value="${cartItem.value}"></label>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
    <label><fmt:message key="delivery_type"/>
        <select name="delivery_type" required="required" >
            <option value=""><fmt:message key="select.delivery_type"/></option>
            <option value="COURIER"><fmt:message key="delivery_type.courier"/></option>
            <option value="BIKE"><fmt:message key="delivery_type.bike"/></option>
            <option value="CAR"><fmt:message key="delivery_type.car"/></option>
            <option value="MAIL"><fmt:message key="delivery_type.mail"/></option>
            <option value="SELF_PICKUP"><fmt:message key="delivery_type.self_pickup"/></option>
        </select>
    </label>
    <br>
    <label><fmt:message key="payment_method"/>
        <select name="payment_method" required="required">
            <option value=""><fmt:message key="select.payment_method"/></option>
            <option value="CASH"><fmt:message key="payment_method.cash"/></option>
            <option value="CARD"><fmt:message key="payment_method.card"/></option>
            <option value="BANK_TRANSFER"><fmt:message key="payment_method.bank_transfer"/></option>
            <option value="CASH_TO_COURIER"><fmt:message key="payment_method.cash_to_courier"/></option>
            <option value="CARD_TO_COURIER"><fmt:message key="payment_method.card_to_courier"/></option>
        </select>
    </label>
    <br>
    <label><fmt:message key="cost"/> = ${sessionScope.cost}</label>
    <input type="submit" name="createOrder" value="Order">
</form>
</body>
</html>
