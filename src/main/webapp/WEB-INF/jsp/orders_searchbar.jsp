<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<div class="search">
    <form action="/orders/" method="post">
        <input type="number" name="id" class="search" placeholder="<spring:message code="orders.search"/>">
        <input type="submit" name="submit" class="submit" value="<spring:message code="search"/>">
    </form>
</div>
</html>