<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${sessionScope.lang != null}">
  <fmt:setLocale value="${sessionScope.lang}"/>
  <fmt:setBundle basename="messages"/>
</c:if>
<html>
<head>
    <title>
      <fmt:message key="add.book"/>
    </title>
  <link rel="stylesheet" href="css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h1><fmt:message key="add.book"/></h1>
<form action="controller?command=add_book" method="post">
  <ul class="wrapper">
    <li class="form-row">
  <label><fmt:message key="title"/><input type="text" name="title" minlength="1"></label>
    </li>
    <li class="form-row">
  <label><fmt:message key="author"/><input type="text" name="author" minlength="1"></label>
    </li>
    <li class="form-row">
  <label><fmt:message key="isbn"/><input type="text" name="isbn" minlength="13"></label>
    </li>
    <li class="form-row">
  <label><fmt:message key="price"/><input type="number" name="price" step="0.01" min="0.01"></label>
    </li>
    <li class="form-row">
      <label><fmt:message key="genre"/>
        <select name="genre" required="required">
          <option value=""><fmt:message key="book.select_genre"/></option>
          <option value="FICTION"><fmt:message key="genre.fiction"/></option>
          <option value="MYSTERY"><fmt:message key="genre.mystery"/></option>
          <option value="THRILLER"><fmt:message key="genre.thriller"/></option>
          <option value="HORROR"><fmt:message key="genre.horror"/></option>
          <option value="HISTORICAL"><fmt:message key="genre.historical"/></option>
          <option value="ROMANCE"><fmt:message key="genre.romance"/></option>
          <option value="WESTERN"><fmt:message key="genre.western"/></option>
          <option value="FLORISTICS"><fmt:message key="genre.floristics"/></option>
          <option value="SCIENCE_FICTION"><fmt:message key="genre.science_fiction"/></option>
          <option value="DYSTOPIAN"><fmt:message key="genre.dystopian"/></option>
          <option value="REALISM"><fmt:message key="genre.realism"/></option>
          <option value="RELIGION"><fmt:message key="genre.religion"/></option>
          <option value="MEDICINE"><fmt:message key="genre.medicine"/></option>
          <option value="ENGINEERING"><fmt:message key="genre.engineering"/></option>
          <option value="ART"><fmt:message key="genre.art"/></option>
        </select>
      </label>
    </li>
    <li class="form-row">
      <label><fmt:message key="cover"/>
        <select name="cover" required="required">
          <option value=""><fmt:message key="book.select_cover"/></option>
          <option value="SOFT"><fmt:message key="cover.soft"/></option>
          <option value="HARD"><fmt:message key="cover.hard"/></option>
          <option value="SPECIAL"><fmt:message key="cover.special"/></option>
        </select>
      </label>
    </li>
    <li class="form-row">
      <label><fmt:message key="pages"/><input type="number" name="pages" step="1" min="1"></label>
    </li>
    <li class="form-row">
      <label><fmt:message key="rating"/><input type="number" name="rating" step="0.01" min="0.01"></label>
    </li>
    <li class="form-row">
      <input type="submit" name="Create">
    </li>
  </ul>
</form>
</body>
</html>