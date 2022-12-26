<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages"/>
<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="messages"/>
</c:if>
<html>
<div class="search">
<form action="controller?command=search" method="post">
        <input type="text" name="search" class="search" placeholder="<fmt:message key="search"/>">
    <input type="submit" name="submit" class="submit" value="<fmt:message key="search"/>">
</form>
</div>
</html>
