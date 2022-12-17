<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<fmt:setBundle basename="messages"/>
<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>
<nav>
    <ul class="navbar">
        <li><a href="controller?command=home"><fmt:message key="home"/></a></li>
        <li><a href="controller?command=books&page=1"><fmt:message key="books"/></a></li>
        <li><a href="controller?command=cart"><fmt:message key="cart"/></a></li>
        <c:if test="${sessionScope.user == null}">
            <li><a href="controller?command=login_form"><fmt:message key="login"/></a></li>
            <li><a href="controller?command=register_form"><fmt:message key="register"/></a></li>
        </c:if>
        <c:if test="${sessionScope.user != null}">
            <li><a href="controller?command=logout"><fmt:message key="logout"/></a></li>
        </c:if>
        <c:if test="${sessionScope.user.role.toString() == 'ADMIN'}">
            <li><a href="controller?command=users"><fmt:message key="users"/></a></li>
            <li><a href="controller?command=add_user_form"><fmt:message key="add.user"/></a></li>
        </c:if>
        <c:if test="${sessionScope.user.role.toString() == 'MANAGER'}">
            <li><a href="controller?command=add_book_form"><fmt:message key="add.book"/></a></li>
        </c:if>
    </ul>
</nav>