<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit book</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h1>Edit book</h1>
<form action="controller?command=edit_book" method="post">
    <ul class="wrapper">
        <li class="form-row">
            <label>Id<input type="hidden" name="id" value="${requestScope.book.id}"></label>
        </li>
        <li class="form-row">
            <label>Title<input id="input_title" type="text" name="title" minlength="1" value="${requestScope.book.title}"></label>
        </li>
        <li class="form-row">
            <label>Author<input type="text" name="author" minlength="1" value="${requestScope.book.author}"></label>
        </li>
        <li class="form-row">
            <label>ISBN<input type="text" name="isbn" minlength="13" value="${requestScope.book.isbn}"></label>
        </li>
        <li class="form-row">
            <label>Price<input type="number" name="price" step="0.01" min="0.01" value="${requestScope.book.price}"></label>
        </li>
        <li class="form-row">
            <label>Genre
                <select name="genre" required="required" >
                    <option value="">Select genre</option>
                    <option value="FICTION">Fiction</option>
                    <option value="MYSTERY">Mystery</option>
                    <option value="THRILLER">Thriller</option>
                    <option value="HORROR">Horror</option>
                    <option value="HISTORICAL">Historical</option>
                    <option value="ROMANCE">Romance</option>
                    <option value="WESTERN">Western</option>
                    <option value="FLORISTICS">Floristics</option>
                    <option value="SCIENCE">Science fiction</option>
                    <option value="DYSTOPIAN">Dystopian</option>
                    <option value="REALISM">Realism</option>
                    <option value="RELIGION">Religion</option>
                    <option value="MEDICINE">Medicine</option>
                    <option value="ENGINEERING">Engineering</option>
                    <option value="ART">Art</option>
                </select>
            </label>
        </li>
        <li class="form-row">
            <label>Cover
                <select name="cover" required="required">
                    <option value="">Select cover</option>
                    <option value="SOFT">Soft</option>
                    <option value="HARD">Hard</option>
                    <option value="SPECIAL">Special</option>
                </select>
            </label>
        </li>
        <li class="form-row">
            <label>Pages<input type="number" name="pages" step="1" min="1" value="${requestScope.book.pages}"></label>
        </li>
        <li class="form-row">
            <label>Rating<input type="number" name="rating" step="0.01" min="0.01" value="${requestScope.book.rating}"></label>
        </li>
        <li class="form-row">
            <input type="submit" name="Edit" value="Edit">
        </li>
    </ul>
</form>
</body>
</html>