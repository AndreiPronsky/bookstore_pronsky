<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>
<fmt:setBundle basename="messages"/>
<html>
    <head>
        <title><fmt:message key="book"/></title>
        <link rel="stylesheet" href="css/style.css">
    </head>
    <body>
    <jsp:include page="navbar.jsp"/>
        <header></header>
        <article>
         <h1><fmt:message key="book"/> : </h1>
         <h3><fmt:message key="id"/> : </h3>
         <p><c:out value="${requestScope.book.id}"/></p>
         <h3><fmt:message key="author"/> : </h3>
         <p><c:out value="${requestScope.book.author}"/></p>
         <h3><fmt:message key="title"/> : </h3>
         <p><c:out value="${requestScope.book.title}"/></p>
            <p><a href="controller?command=add_to_cart&id=${requestScope.book.id}"><fmt:message key="add_to_cart"/></a></p>
        </article>
        <footer></footer>
     </body>
 </html>