<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <title>Book</title>
        <link rel="stylesheet" href="css/style.css">
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
            <p><a href="controller?command=add_to_cart&id=${requestScope.book.id}">Add to cart</a></p>
        </article>
        <footer></footer>
     </body>
 </html>