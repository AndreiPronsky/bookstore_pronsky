<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>
<fmt:setBundle basename="messages"/>
<html>
<div class="search">
    <form action="/orders/" method="post">
        <input type="number" name="id" class="search" placeholder="<fmt:message key="orders.search"/>">
        <input type="submit" name="submit" class="submit" value="<fmt:message key="search"/>">
    </form>
</div>
</html>