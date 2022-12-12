
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
        <td>${cartItem.value.title}</td>
        <td>${cartItem.value.price}</td>
        <td>
            <label>Quantity<input type="number" name="quantity" step="1" min="0" value="${cartItem.value.quantity}"></label>
        </td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
  <label>Delivery type
    <select name="delivery_type" required="required" >
      <option value="">Select delivery type</option>
      <option value="1">Courier</option>
      <option value="2">Bike</option>
      <option value="3">Car</option>
      <option value="4">Mail</option>
      <option value="5">Self pickup</option>
    </select>
  </label>
  <br>
    <label>Payment method
    <select name="payment_method" required="required">
      <option value="">Select payment method</option>
      <option value="1">Cash</option>
      <option value="2">Card</option>
      <option value="3">Bank transfer</option>
      <option value="4">Cash to courier</option>
      <option value="5">Card to courier</option>
    </select>
  </label>
  <br>
  <input type="submit" name="createOrder" value="Order">
</form>
</body>
</html>
