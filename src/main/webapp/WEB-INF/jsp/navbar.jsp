<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<header class="header">
    <a href="home">
        <div class="logo-image">
            <img height="75" src="/serviceImages/logo.png" class="img-fluid" alt="logo">
        </div>
    </a>
    <ul class="main-nav">
        <li><a href="/books/all?page=0&size=5&sort=asc"><spring:message code="books"/></a></li>
        <c:if test="${sessionScope.user.role.toString() != 'ADMIN' && sessionScope.user.role.toString() != 'MANAGER'}">
            <li><a href="/cart"><spring:message code="cart"/></a></li>
        </c:if>
        <c:if test="${sessionScope.user == null}">
            <li><a href="/users/login"><spring:message code="login"/></a></li>
            <li><a href="/users/register"><spring:message code="register"/></a></li>
        </c:if>
        <c:if test="${sessionScope.user != null}">
            <li><a href="/users/logout"><spring:message code="logout"/></a></li>
        </c:if>
        <c:if test="${sessionScope.user.role.toString() == 'USER'}">
            <li><a href="/users/my_orders"><spring:message code="my_orders"/></a></li>
        </c:if>
        <c:if test="${sessionScope.user.role.toString() == 'ADMIN'}">
            <li><a href="/users/all?page=0&size=5"><spring:message code="users"/></a></li>
            <li><a href="/users/add"><spring:message code="add.user"/></a></li>
            <li><a href="/orders/all"><spring:message code="orders"/></a></li>
        </c:if>
        <c:if test="${sessionScope.user.role.toString() == 'MANAGER'}">
            <li><a href="/books/add"><spring:message code="add.book"/></a></li>
        </c:if>
        <li><a href="/change_lang?lang=ru">
            <img height="30" src="/serviceImages/RU.png" alt="Русский"></a></li>
        <li><a href="/change_lang?lang=en">
            <img height="30" src="/serviceImages/UK.png" alt="English"></a></li>
    </ul>
</header>
