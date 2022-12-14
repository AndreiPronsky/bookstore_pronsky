
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h1>Confirm order</h1>
<form action="controller?command=confirm_order" method="post">
  <table>
    <caption>Order</caption>
    <thead>
    <tr>
      <th>Title</th>
      <th>Price</th>
      <th>Quantity</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${sessionScope.cart}" var="cartItem">
      <tr>
        <td>${cartItem.key.title}</td>
        <td>${cartItem.key.price}</td>
        <td>
          <label>Quantity<input type="submit" name="quantity" step="1" min="0" value="${cartItem.value}"></label>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
  <label>Delivery type
    <select name="delivery_type" required="required" >
      <option value="">Select delivery type</option>
      <option value="COURIER">Courier</option>
      <option value="BIKE">Bike</option>
      <option value="CAR">Car</option>
      <option value="MAIL">Mail</option>
      <option value="SELF_PICKUP">Self pickup</option>
    </select>
  </label>
  <br>
    <label>Payment method
    <select name="payment_method" required="required">
      <option value="">Select payment method</option>
      <option value="CASH">Cash</option>
      <option value="CARD">Card</option>
      <option value="BANK_TRANSFER">Bank transfer</option>
      <option value="CASH_TO_COURIER">Cash to courier</option>
      <option value="CARD_TO_COURIER">Card to courier</option>
    </select>
  </label>
  <br>
    <label>Cost = ${sessionScope.cost}</label>
  <input type="submit" name="createOrder" value="Order">
</form>
</body>
</html>
