<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <title><fmt:message key="confirm_order"/></title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<c:if test="${sessionScope.cart == null || sessionScope.cart.isEmpty()}">
    <h1><fmt:message key="your_cart_is_empty"/></h1>
</c:if>
<c:if test="${sessionScope.cart != null && !sessionScope.cart.isEmpty()}">
    <h1><fmt:message key="confirm_order"/></h1>
    <form action="controller?command=confirm_order" method="post">
        <table>
            <tbody>
            <c:forEach items="${sessionScope.cart}" var="cartItem">
                <c:set var="total" value="${total + cartItem.key.price * cartItem.value }"/>
                <tr>
                    <td><c:out value="${cartItem.key.title}"/></td>
                    <td><c:out value="${cartItem.key.price}"/></td>
                    <td>
                        <label><fmt:message key="quantity"/>
                        <a href="controller?command=corr_cart&action=dec&id=${cartItem.key.id }">-</a>
                            <input type="number" name="quantity" step="1" min="0" value="${cartItem.value}">
                        </label>
                        <a href="controller?command=corr_cart&action=inc&id=${cartItem.key.id }">+</a>
                        <a href="controller?command=corr_cart&action=remove&id=${cartItem.key.id }"><fmt:message
                                key="remove"/></a>
                    </td>
                    <td><c:out value="${cartItem.key.price * cartItem.value }"/></td>
                </tr>
            </c:forEach>
            <td>
                <label><fmt:message key="delivery_type"/>
                    <select name="delivery_type" required="required">
                        <option value=""><fmt:message key="select.delivery_type"/></option>
                        <option value="COURIER"><fmt:message key="delivery_type.courier"/></option>
                        <option value="BIKE"><fmt:message key="delivery_type.bike"/></option>
                        <option value="CAR"><fmt:message key="delivery_type.car"/></option>
                        <option value="MAIL"><fmt:message key="delivery_type.mail"/></option>
                        <option value="SELF_PICKUP"><fmt:message key="delivery_type.self_pickup"/></option>
                    </select>
                </label>
            </td>
            <td>
                <label><fmt:message key="payment_method"/>
                    <select name="payment_method" required="required">
                        <option value=""><fmt:message key="select.payment_method"/></option>
                        <option value="CASH"><fmt:message key="payment_method.cash"/></option>
                        <option value="CARD"><fmt:message key="payment_method.card"/></option>
                        <option value="BANK_TRANSFER"><fmt:message key="payment_method.bank_transfer"/></option>
                    </select>
                </label>
            </td>
            <td>
                <label><fmt:message key="cost"/> = <c:out value="${total}"/></label>
            </td>
            </tbody>
        </table>
        <input type="submit" name="createOrder" value="Order">
    </form>
</c:if>
</body>
</html>
