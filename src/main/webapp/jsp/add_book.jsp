<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<fmt:setBundle basename="messages"/>
<c:if test="${sessionScope.lang != null}">
  <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>
<html>
<head>
    <title>Add new book</title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h1>Add new book</h1>
<form action="controller?command=add_book" method="post">
  <ul class="wrapper">
    <li class="form-row">
  <label>Title<input type="text" name="title" minlength="1"></label>
    </li>
    <li class="form-row">
  <label>Author<input type="text" name="author" minlength="1"></label>
    </li>
    <li class="form-row">
  <label>ISBN<input type="text" name="isbn" minlength="13"></label>
    </li>
    <li class="form-row">
  <label>Price<input type="number" name="price" step="0.01" min="0.01"></label>
    </li>
    <li class="form-row">
      <label>Genre
        <select name="genre" required="required">
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
      <label>Pages<input type="number" name="pages" step="1" min="1"></label>
    </li>
    <li class="form-row">
      <label>Rating<input type="number" name="rating" step="0.01" min="0.01"></label>
    </li>
    <li class="form-row">
      <input type="submit" name="Create">
    </li>
  </ul>
</form>
</body>
</html>
