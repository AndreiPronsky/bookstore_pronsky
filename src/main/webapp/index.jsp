<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
</head>
<body>
<jsp:include page="jsp/navbar.jsp"/>
<h1>Welcome to bookstore_pronsky!</h1>
<nav>
    <ul>
        <li><a href="controller?command=books">View all books></a></li>
        <li><a href="controller?command=users">View all Users</a></li>
        <li><a href="">Home</a></li>
    </ul>
</nav>
</body>
</html>
