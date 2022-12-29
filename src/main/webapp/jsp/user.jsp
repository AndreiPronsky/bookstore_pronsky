<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${sessionScope.lang != null}">
  <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>
<fmt:setBundle basename="messages"/>
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
  <h3><fmt:message key="id"/> : ${requestScope.user.id}</h3>
  </c:if>
  <h3><fmt:message key="firstname"/> : ${requestScope.user.firstName}</h3>
  <h3><fmt:message key="lastname"/> : ${requestScope.user.lastName}</h3>
  <h3><fmt:message key="email"/> : ${requestScope.user.email}</h3>
  <c:if test="${sessionScope.user.role.toString() == 'ADMIN'}">
  <h3><fmt:message key="role"/> : ${requestScope.user.role}</h3>
  </c:if>
</article>
<footer></footer>
</body>
</html>