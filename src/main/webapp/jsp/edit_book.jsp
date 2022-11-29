<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Edit book</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h1>Edit book</h1>
<form action="controller?command=edit_book&id=5" method="post">
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
                    <option value="1">Fiction</option>
                    <option value="2">Mystery</option>
                    <option value="3">Thriller</option>
                    <option value="4">Horror</option>
                    <option value="5">Historical</option>
                    <option value="6">Romance</option>
                    <option value="7">Western</option>
                    <option value="8">Floristics</option>
                    <option value="9">Science fiction</option>
                    <option value="10">Dystopian</option>
                    <option value="11">Realism</option>
                    <option value="12">Religion</option>
                    <option value="13">Medicine</option>
                    <option value="14">Engineering</option>
                    <option value="15">Art</option>
                </select>
            </label>
        </li>
        <li class="form-row">
            <label>Cover
                <select name="cover" required="required">
                    <option value="">Select cover</option>
                    <option value="1">Soft</option>
                    <option value="2">Hard</option>
                    <option value="3">Special</option>
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
            <input type="submit" name="Edit">
        </li>
    </ul>
</form>
</body>
</html>