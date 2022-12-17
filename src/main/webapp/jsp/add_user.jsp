<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages"/>
<c:if test="${sessionScope.lang != null}">
  <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>
<html>
<head>
  <title><fmt:message key="register"/></title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h1><fmt:message key="register"/></h1>
<form action="controller?command=add_user" method="post">
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
    <c:if test="${sessionScope.user.Role == 'ADMIN'}">
      <li class="form-row">
        <label><fmt:message key="role"/>
          <select name="role" required="required">
            <option value=""><fmt:message key="select.role"/></option>
            <option value="USER"><fmt:message key="role.user"/></option>
            <option value="ADMIN"><fmt:message key="role.admin"/></option>
            <option value="MANAGER"><fmt:message key="role.manager"/></option>
          </select>
        </label>
      </li>
      <li class="form-row">
        <label><fmt:message key="rating"/><input type="number" name="rating" step="0.01" min="0.01"></label>
      </li>
    </c:if>
    <li class="form-row">
      <input type="submit" name="Create">
    </li>
  </ul>
</form>
</body>
</html>