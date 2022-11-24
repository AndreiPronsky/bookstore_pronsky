<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
  <title>Bookstore-pronsky</title>
</head>
<body>
<header></header>
<nav></nav>
<article>
  <h1>User</h1>
  <h3>Id :</h3>
  <p>${requestScope.user.id}</p>
  <h3>Firstname :</h3>
  <p>${requestScope.user.firstName}</p>
  <h3>Lastname :</h3>
  <p>${requestScope.user.lastName}</p>
  <h3>Email :</h3>
  <p>${requestScope.user.email}</p>
  <h3> Role :</h3>
  <p>${requestScope.user.role}</p>
</article>
<footer></footer>
</body>
</html>