<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<c:if test="${sessionScope.lang != null}">
  <fmt:setLocale value="${sessionScope.lang}"/>
  <fmt:setBundle basename="messages"/>
</c:if>
<html>
<head>
  <title><fmt:message key="register"/></title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h1><fmt:message key="register"/></h1>
<form action="controller?command=register" method="post">
  <ul class="wrapper">
    <li class="form-row">
      <label><fmt:message key="firstname"/><input type="text" name="firstname" minlength="1"></label>
    </li>
    <li class="form-row">
      <label><fmt:message key="lastname"/><input type="text" name="lastname" minlength="1"></label>
    </li>
    <li class="form-row">
      <label><fmt:message key="email"/><input type="text" name="email" minlength="13"></label>
    </li>
    <li class="form-row">
      <label><fmt:message key="password"/><input type="password" name="password" minlength="8"></label>
    </li>
    <li class="form-row">
      <input type="submit" name="Create" value="<fmt:message key="register"/>">
    </li>
  </ul>
</form>
</body>
</html>