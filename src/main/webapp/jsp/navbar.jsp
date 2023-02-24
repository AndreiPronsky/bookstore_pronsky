<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>
<fmt:setBundle basename="messages"/>
<header class="header">
    <a href="controller?command=home">
        <div class="logo-image">
            <img height="75" src="serviceImages/logo.png" class="img-fluid" alt="logo">
        </div>
    </a>
    <ul class="main-nav">
        <li><a href="controller?command=books&page=1"><fmt:message key="books"/></a></li>
        <c:if test="${sessionScope.user.role.toString() != 'ADMIN' && sessionScope.user.role.toString() != 'MANAGER'}">
            <li><a href="controller?command=cart"><fmt:message key="cart"/></a></li>
        </c:if>
        <c:if test="${sessionScope.user == null}">
            <li><a href="controller?command=login_form"><fmt:message key="login"/></a></li>
            <li><a href="controller?command=register_form"><fmt:message key="register"/></a></li>
        </c:if>
        <c:if test="${sessionScope.user != null}">
            <li><a href="controller?command=logout"><fmt:message key="logout"/></a></li>
        </c:if>
        <c:if test="${sessionScope.user.role.toString() == 'USER'}">
            <li><a href="controller?command=my_orders"><fmt:message key="my_orders"/></a></li>
        </c:if>
        <c:if test="${sessionScope.user.role.toString() == 'ADMIN'}">
            <li><a href="controller?command=users"><fmt:message key="users"/></a></li>
            <li><a href="controller?command=add_user_form"><fmt:message key="add.user"/></a></li>
            <li><a href="controller?command=orders&page=1"><fmt:message key="orders"/></a></li>
        </c:if>
        <c:if test="${sessionScope.user.role.toString() == 'MANAGER'}">
            <li><a href="controller?command=add_book_form"><fmt:message key="add.book"/></a></li>
        </c:if>
        <li><a href="controller?command=change_lang&lang=ru">
            <img height="30" src="serviceImages/RU.png" alt="Русский"></a></li>
        <li><a href="controller?command=change_lang&lang=en">
            <img height="30" src="serviceImages/UK.png" alt="English"></a></li>
    </ul>
</header>
