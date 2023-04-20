<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title><spring:message code="edit_book"/></title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h1><spring:message code="edit_book"/></h1>
<form method="post">
    <ul class="wrapper">
        <li class="form-row">
            <label><spring:message code="id"/>
                <input type="hidden" name="id" value="${book.id}"></label>
        </li>
        <li class="form-row">
            <label><spring:message code="title"/>
                <input id="input_title" type="text" name="title" minlength="1"
                       value="${book.title}"></label>
        </li>
        <li class="form-row">
            <label><spring:message code="author"/>
                <input type="text" name="author" minlength="2" value="${book.author}"></label>
        </li>
        <li class="form-row">
            <label><spring:message code="isbn"/>
                <input type="text" name="isbn" minlength="13" value="${book.isbn}"></label>
        </li>
        <li class="form-row">
            <label><spring:message code="price"/>
                <input type="number" name="price" step="0.01" min="0.01" value="${book.price}"></label>
        </li>
        <li class="form-row">
            <label><spring:message code="genre"/>
                <select name="genre" required="required">
                    <option value="${book.genre}" selected="selected">
                        <spring:message code="genre.${book.genre}"/></option>
                    <option value=""><spring:message code="book.select_genre"/></option>
                    <option value="FICTION"><spring:message code="genre.FICTION"/></option>
                    <option value="MYSTERY"><spring:message code="genre.MYSTERY"/></option>
                    <option value="THRILLER"><spring:message code="genre.THRILLER"/></option>
                    <option value="HORROR"><spring:message code="genre.HORROR"/></option>
                    <option value="HISTORICAL"><spring:message code="genre.HISTORICAL"/></option>
                    <option value="ROMANCE"><spring:message code="genre.ROMANCE"/></option>
                    <option value="WESTERN"><spring:message code="genre.WESTERN"/></option>
                    <option value="FLORISTICS"><spring:message code="genre.FLORISTICS"/></option>
                    <option value="SCIENCE_FICTION"><spring:message code="genre.SCIENCE_FICTION"/></option>
                    <option value="DYSTOPIAN"><spring:message code="genre.DYSTOPIAN"/></option>
                    <option value="REALISM"><spring:message code="genre.REALISM"/></option>
                    <option value="RELIGION"><spring:message code="genre.RELIGION"/></option>
                    <option value="MEDICINE"><spring:message code="genre.MEDICINE"/></option>
                    <option value="ENGINEERING"><spring:message code="genre.ENGINEERING"/></option>
                    <option value="ART"><spring:message code="genre.ART"/></option>
                </select>
            </label>
        </li>
        <li class="form-row">
            <label><spring:message code="cover"/>
                <select name="cover" required="required">
                    <option value="${book.cover}" selected="selected">
                        <spring:message code="cover.${book.cover}"/></option>
                    <option value=""><spring:message code="book.select_cover"/></option>
                    <option value="SOFT"><spring:message code="cover.SOFT"/></option>
                    <option value="HARD"><spring:message code="cover.HARD"/></option>
                    <option value="SPECIAL"><spring:message code="cover.SPECIAL"/></option>
                </select>
            </label>
        </li>
        <li class="form-row">
            <label><spring:message code="pages"/>
                <input type="number" name="pages" step="1" min="1" value="${book.pages}">
            </label>
        </li>
        <li class="form-row">
            <label><spring:message code="rating"/>
                <input type="number" name="rating" step="0.01" min="0.01" max="5.0" value="${book.rating}">
            </label>
        </li>
        <li class="form-row">
            <input type="submit" name="Edit" formaction="/books/edit" value="<spring:message code="edit"/>">
            <input type="submit" name="Delete" formaction="/books/delete" value="<spring:message code="delete"/>">
        </li>
    </ul>
</form>
<table>
    <c:forEach items="${sessionScope.validationMessages}" var="message">
        <tr>
            <td><c:out value="${message}"/></td>
        </tr>
    </c:forEach>
</table>
</body>
</html>