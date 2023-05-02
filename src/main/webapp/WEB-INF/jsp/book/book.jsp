<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<head>
    <title><spring:message code="book"/></title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<jsp:include page="../navbar.jsp"/>
    <h1><spring:message code="book"/> : </h1>
    <h3><spring:message code="id"/> : </h3>
    <p><c:out value="${book.id}"/></p>
    <h3><spring:message code="author"/> : </h3>
    <p><c:out value="${book.author}"/></p>
    <h3><spring:message code="title"/> : </h3>
    <p><c:out value="${book.title}"/></p>
    <h6><spring:message code="available.${book.available}"/></h6>
</body>
</html>