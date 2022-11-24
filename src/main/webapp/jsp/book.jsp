<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Bookstore-pronsky</title>
    </head>
    <body>
    <jsp:include page="navbar.jsp"/>
        <header></header>
        <article>
         <h1>Book</h1>
         <h3>Id :</h3>
         <p>${requestScope.book.id}</p>
         <h3>Author :</h3>
         <p>${requestScope.book.author}</p>
         <h3>Title :</h3>
         <p>${requestScope.book.title}</p>
        </article>
        <footer></footer>
     </body>
 </html>