<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<nav>
  <ul class="navbar">
    <li><a href="controller?command=books">Books</a></li>
    <li><a href="controller?command=login_form">Log In</a></li>
    <li><a href="controller.command=cart">Cart</a></li>
    <c:if test="${sessionScope.user.role.toString() == 'ADMIN'
                || sessionScope.user.role.toString() == 'MANAGER'}">
    <li><a href="controller?command=users">Users</a></li>
    <li><a href="controller?command=add_book_form">Add new book</a></li>
    <li><a href="controller?command=add_user_form">Add new user</a></li>
    <li><a href="controller?command=edit_user_form">Edit user</a></li>
  </c:if>
    <li><a href="controller?command=home">Home</a></li>
    <c:if test="${sessionScope.user != null}">
    <li><a href="controller?command=logout">Log Out</a></li>
  </c:if>
  </ul>
</nav>
