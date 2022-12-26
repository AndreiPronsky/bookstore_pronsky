<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
    <fmt:setBundle basename="messages"/>
</c:if>
<html>
<head>
    <title><fmt:message key="successful_order"/></title>
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h1> <fmt:message key="number_of_your_order_is"/> : ${requestScope.order_id}! <fmt:message key="our_manager_will_contact_you_soon"/></h1>
</body>
</html>
