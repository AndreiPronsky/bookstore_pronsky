<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Add new user</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h1>Add new user</h1>
<form action="controller?command=add_user" method="post">
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
      <label>password<input type="text" name="password" minlength="8"></label>
    </li>
    <li class="form-row">
      <label>Role
        <select name="role" required="required">
          <option value="">Select role</option>
          <option value="USER">User</option>
          <option value="ADMIN">Admin</option>
          <option value="MANAGER">Manager</option>
        </select>
      </label>
    </li>
    <li class="form-row">
      <label>Rating<input type="number" name="rating" step="0.01" min="0.01"></label>
    </li>
    <li class="form-row">
      <input type="submit" name="Create">
    </li>
  </ul>
</form>
</body>
</html>