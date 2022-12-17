<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h1>Login</h1>
<form method="post" action="controller">
    <input name="command" type="hidden" value="login">
    <label for="email-input">Email:</label>
    <input id="email-input" name="email" type="email">
    <br/>
    <label for="password-input">Password:</label>
    <input id="password-input" name="password" type="password" minlength="4">
    <br/>
    <input type="submit" name="Login">
</form>
</body>
</html>