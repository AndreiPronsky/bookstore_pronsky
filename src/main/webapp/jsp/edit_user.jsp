<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Edit user</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h1>Edit user</h1>
<form action="controller?command=edit_user" method="post">
  <ul class="wrapper">
    <li>
    <input type="hidden" name="id" value="${requestScope.user.id}">
    </li>
    <li class="form-row">
      <label>Firstname<input type="text" name="firstname" minlength="1" value="${requestScope.user.firstName}"></label>
    </li>
    <li class="form-row">
      <label>Lastname<input type="text" name="lastname" minlength="1" value="${requestScope.user.lastName}"></label>
    </li>
    <li class="form-row">
      <label>Email<input type="text" name="email" value="${requestScope.user.email}" ></label>
    </li>
    <li class="form-row">
      <label>password<input type="text" name="password" minlength="8" value="${requestScope.user.password}"></label>
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
      <label>Rating<input type="number" name="rating" step="0.01" min="0.01" value="${requestScope.user.rating}"></label>
    </li>
    <li class="form-row">
      <input type="submit" name="Edit" value="Edit">
    </li>
  </ul>
</form>
</body>
</html>
