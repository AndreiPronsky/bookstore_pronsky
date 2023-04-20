<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>

<html>
<div class="search">
    <form action="/search" method="post">
        <input type="text" name="search" class="search" placeholder="<spring:message code="search"/>">
        <input type="submit" name="submit" class="submit" value="<spring:message code="search"/>">
    </form>
</div>
</html>
