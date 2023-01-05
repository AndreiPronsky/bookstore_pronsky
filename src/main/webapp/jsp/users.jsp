<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${sessionScope.lang != null}">
  <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>
<fmt:setBundle basename="messages"/>
<html>
<head>
  <meta charset="UTF-8">
  <title><fmt:message key="bookstore"/></title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<header></header>
<c:if test="${requestScope.users.isEmpty()}">
  <h2><fmt:message key="no_users_found"/></h2>
</c:if>
<c:if test="${!requestScope.users.isEmpty()}">
  <div class="paging">
    <a href="controller?command=users&page=1"><fmt:message key="first"/></a>
    <c:if test="${requestScope.page <= 1} ">
      <a><fmt:message key="previous"/></a>
    </c:if>
    <c:if test="${requestScope.page > 1}">
      <a href="controller?command=users&page=${requestScope.page - 1}"><fmt:message key="previous"/></a>
    </c:if>
      ${requestScope.page}
    <c:if test="${requestScope.page < requestScope.total_pages}">
      <a href="controller?command=users&page=${requestScope.page + 1}"><fmt:message key="next"/></a>
    </c:if>
    <a href="controller?command=users&page=${requestScope.total_pages}"><fmt:message key="last"/></a>
  </div>
  <table>
    <caption><fmt:message key="users"/></caption>
    <thead>
    <tr>
      <th><fmt:message key="id"/></th>
      <th><fmt:message key="firstname"/></th>
      <th><fmt:message key="lastname"/></th>
      <th><fmt:message key="email"/></th>
      <th><fmt:message key="role"/></th>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${requestScope.users}" var="user">
      <tr>
        <td><c:out value="${user.id}"/></td>
        <td><c:out value="${user.firstName}"/></td>
        <td><c:out value="${user.lastName}"/></td>
        <td><c:out value="${user.email}"/></td>
        <td><c:out value="${user.role}"/></td>
        <c:if test="${sessionScope.user.role.toString() == 'ADMIN'}">
          <td><a href="controller?command=edit_user_form&id=${user.id}"><fmt:message key="edit_user"/></a></td>
        </c:if>
      </tr>
    </c:forEach>
    </tbody>
  </table>
</c:if>
<footer></footer>
</body>
</html>