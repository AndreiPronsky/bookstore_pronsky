<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Successful order</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h1> Number of your order is ${sessionScope.order_id}! Our manager will contact you soon!</h1>
</body>
</html>
