<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Error page</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h1>Error!!!</h1>
<h1>Something went wrong</h1>
<p>${requestScope.message}</p>
</body>
</html>
