<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<html>
<head>
    <title><spring:message code="user"/></title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<jsp:include page="../navbar.jsp"/>
<header></header>
<article>
    <h1><spring:message code="user"/></h1>
    <c:if test="${sessionScope.user.role.toString() == 'ADMIN'}">
        <h3><spring:message code="id"/> : <c:out value="${user.id}"/></h3>
    </c:if>
    <h3><spring:message code="firstname"/> : <c:out value="${user.firstName}"/></h3>
    <h3><spring:message code="lastname"/> : <c:out value="${user.lastName}"/></h3>
    <h3><spring:message code="email"/> : <c:out value="${user.email}"/></h3>
    <c:if test="${sessionScope.user.role.toString() == 'ADMIN'}">
        <h3><spring:message code="role"/> : <c:out value="${user.role}"/></h3>
    </c:if>
</article>
<footer></footer>
</body>
</html>