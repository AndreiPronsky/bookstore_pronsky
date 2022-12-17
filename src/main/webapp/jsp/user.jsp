<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages"/>
<c:if test="${sessionScope.lang != null}">
  <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>
<html>
<head>
  <title><fmt:message key="user"/></title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<header></header>
<article>
  <h1><fmt:message key="user"/></h1>
  <c:if test="${sessionScope.user.role.toString() == 'ADMIN'}">
  <h3><fmt:message key="id"/> :</h3>
  <p>${requestScope.user.id}</p>
  </c:if>
  <h3><fmt:message key="firstname"/> :</h3>
  <p>${requestScope.user.firstName}</p>
  <h3><fmt:message key="lastname"/> :</h3>
  <p>${requestScope.user.lastName}</p>
  <h3><fmt:message key="email"/> :</h3>
  <p>${requestScope.user.email}</p>
  <c:if test="${sessionScope.user.role.toString() == 'ADMIN'}">
  <h3><fmt:message key="role"/> :</h3>
  <p>${requestScope.user.role}</p>
  </c:if>
</article>
<footer></footer>
</body>
</html>