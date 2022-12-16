<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>Register</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h1>Register</h1>
<form action="controller?command=register" method="post">
  <ul class="wrapper">
    <li class="form-row">
      <label>Firstname<input type="text" name="firstname" minlength="1"></label>
    </li>
    <li class="form-row">
      <label>Lastname<input type="text" name="lastname" minlength="1"></label>
    </li>
    <li class="form-row">
      <label>Email<input type="text" name="email" minlength="13"></label>
    </li>
    <li class="form-row">
      <label>password<input type="password" name="password" minlength="8"></label>
    </li>
    <li class="form-row">
      <input type="submit" name="Create">
    </li>
  </ul>
</form>
</body>
</html>