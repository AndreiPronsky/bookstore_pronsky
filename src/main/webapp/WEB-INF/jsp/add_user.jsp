<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title><spring:message code="register"/></title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h1><spring:message code="register"/></h1>
<table>
    <c:forEach items="${validationMessages}" var="message">
        <tr>
            <td><c:out value="${message}"/></td>
        </tr>
    </c:forEach>
</table>
<form action="/users/add" method="post">
    <ul class="wrapper">
        <li class="form-row">
            <label><spring:message code="firstname"/><input type="text" name="firstName"></label>
        </li>
        <li class="form-row">
            <label><spring:message code="lastname"/><input type="text" name="lastName"></label>
        </li>
        <li class="form-row">
            <label><spring:message code="email"/><input type="text" name="email"></label>
        </li>
        <li class="form-row">
            <label><spring:message code="password"/><input type="password" name="password" minlength="8"></label>
        </li>
        <c:if test="${sessionScope.user.role == 'ADMIN'}">
            <li class="form-row">
                <label><spring:message code="role"/>
                    <select name="role" required="required">
                        <option value=""><spring:message code="select.role"/></option>
                        <option value="USER"><spring:message code="role.USER"/></option>
                        <option value="ADMIN"><spring:message code="role.ADMIN"/></option>
                        <option value="MANAGER"><spring:message code="role.MANAGER"/></option>
                    </select>
                </label>
            </li>
            <li class="form-row">
                <label><spring:message code="rating"/><input type="number" name="rating" step="0.01" min="0.01"
                                                         max="5.0"></label>
            </li>
        </c:if>
        <li class="form-row">
            <input type="submit" name="Create">
        </li>
    </ul>
</form>
</body>
</html>