<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages"/>
<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>
<html>
<head>
    <title></title>
</head>
<body>
<div id="searchbar"><fmt:message key="search"/></div>
<form action="controller?command=search" method="post">
    <ul class="wrapper">
        <li class="form-row">
            <label><fmt:message key="search"/><input type="text" name="search" minlength="1"></label>
            <input type="submit">
        </li>
    </ul>
</form>
</body>
</html>
