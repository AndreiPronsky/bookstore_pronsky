<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <meta charset="UTF-8">
  <title>Bookstore-pronsky</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<header></header>
<c:if test="${requestScope.users.isEmpty()}">
  <h2>No users found!</h2>
</c:if>
<c:if test="${!requestScope.users.isEmpty()}">
  <div class="paging">
    <a href="controller?command=users&page=1">First</a>
    <c:if test="${requestScope.page <= 1} ">
      <a>Previous</a>
    </c:if>
    <c:if test="${requestScope.page > 1}">
      <a href="controller?command=users&page=${requestScope.page - 1}">Previous</a>
    </c:if>
      ${requestScope.page}
    <c:if test="${requestScope.page < requestScope.total_pages}">
      <a href="controller?command=users&page=${requestScope.page + 1}">Next</a>
    </c:if>
    <c:if test="${requestScope.page >= requestScope.total_pages}">
      <a>Next</a>
    </c:if>
    <a href="controller?command=users&page=${requestScope.total_pages}">Last</a>
  </div>
  <table>
    <caption>Users</caption>
    <thead>
    <tr>
      <th>Id</th>
      <th>Firstname</th>
      <th>Lastname</th>
      <th>Email</th>
      <th>Role</th>
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
        <c:if test="${sessionScope.user.role.toString() == 'ADMIN'
                || sessionScope.user.role.toString() == 'MANAGER'}">
          <td><a href="controller?command=edit_user_form&id=${user.id}">Edit user</a></td>
        </c:if>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</c:if>
<footer></footer>
</body>
</html>