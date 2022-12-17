<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
  <title>User</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<header></header>
<article>
  <h1>User</h1>
  <c:if test="${sessionScope.user.role.toString() == 'ADMIN'}">
  <h3>Id :</h3>
  <p>${requestScope.user.id}</p>
  </c:if>
  <h3>Firstname :</h3>
  <p>${requestScope.user.firstName}</p>
  <h3>Lastname :</h3>
  <p>${requestScope.user.lastName}</p>
  <h3>Email :</h3>
  <p>${requestScope.user.email}</p>
  <c:if test="${sessionScope.user.role.toString() == 'ADMIN'}">
  <h3> Role :</h3>
  <p>${requestScope.user.role}</p>
  </c:if>
</article>
<footer></footer>
</body>
</html>