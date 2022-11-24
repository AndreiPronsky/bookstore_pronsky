<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error page</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h1>Error!!!</h1>
<p>${requestScope.message}</p>
</body>
</html>
