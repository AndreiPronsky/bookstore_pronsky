<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>Bookstore-pronsky</title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<header></header>
<c:if test="${requestScope.users.isEmpty()}">
  <h2>No users found!</h2>
</c:if>
<c:if test="${!requestScope.users.isEmpty()}">
  <table>
    <caption>Users</caption>
    <thead>
    <tr>
      <th>Id</th>
      <th>Firstname</th>
      <th>Lastname</th>
      <th>Email</th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${requestScope.users}" var="user">
      <tr>
        <td>${user.id}</td>
        <td>${user.firstName}</td>
        <td>${user.lastName}</td>
        <td>${user.email}</td>
        <td>${user.role}</td>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</c:if>
<footer></footer>
</body>
</html>