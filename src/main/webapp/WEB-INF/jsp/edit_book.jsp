<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:if test="${sessionScope.lang != null}">
    <fmt:setLocale value="${sessionScope.lang}"/>
</c:if>
<fmt:setBundle basename="messages"/>
<html>
<head>
    <title><fmt:message key="edit_book"/></title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<jsp:include page="navbar.jsp"/>
<h1><fmt:message key="edit_book"/></h1>
<form method="post">
    <ul class="wrapper">
        <li class="form-row">
            <label><fmt:message key="id"/>
                <input type="hidden" name="id" value="${requestScope.book.id}"></label>
        </li>
        <li class="form-row">
            <label><fmt:message key="title"/>
                <input id="input_title" type="text" name="title" minlength="1"
                       value="${requestScope.book.title}"></label>
        </li>
        <li class="form-row">
            <label><fmt:message key="author"/>
                <input type="text" name="author" minlength="2" value="${requestScope.book.author}"></label>
        </li>
        <li class="form-row">
            <label><fmt:message key="isbn"/>
                <input type="text" name="isbn" minlength="13" value="${requestScope.book.isbn}"></label>
        </li>
        <li class="form-row">
            <label><fmt:message key="price"/>
                <input type="number" name="price" step="0.01" min="0.01" value="${requestScope.book.price}"></label>
        </li>
        <li class="form-row">
            <label><fmt:message key="genre"/>
                <select name="genre" required="required">
                    <option value="${requestScope.book.genre}" selected="selected">
                        <fmt:message key="genre.${requestScope.book.genre}"/></option>
                    <option value=""><fmt:message key="book.select_genre"/></option>
                    <option value="FICTION"><fmt:message key="genre.FICTION"/></option>
                    <option value="MYSTERY"><fmt:message key="genre.MYSTERY"/></option>
                    <option value="THRILLER"><fmt:message key="genre.THRILLER"/></option>
                    <option value="HORROR"><fmt:message key="genre.HORROR"/></option>
                    <option value="HISTORICAL"><fmt:message key="genre.HISTORICAL"/></option>
                    <option value="ROMANCE"><fmt:message key="genre.ROMANCE"/></option>
                    <option value="WESTERN"><fmt:message key="genre.WESTERN"/></option>
                    <option value="FLORISTICS"><fmt:message key="genre.FLORISTICS"/></option>
                    <option value="SCIENCE_FICTION"><fmt:message key="genre.SCIENCE_FICTION"/></option>
                    <option value="DYSTOPIAN"><fmt:message key="genre.DYSTOPIAN"/></option>
                    <option value="REALISM"><fmt:message key="genre.REALISM"/></option>
                    <option value="RELIGION"><fmt:message key="genre.RELIGION"/></option>
                    <option value="MEDICINE"><fmt:message key="genre.MEDICINE"/></option>
                    <option value="ENGINEERING"><fmt:message key="genre.ENGINEERING"/></option>
                    <option value="ART"><fmt:message key="genre.ART"/></option>
                </select>
            </label>
        </li>
        <li class="form-row">
            <label><fmt:message key="cover"/>
                <select name="cover" required="required">
                    <option value="${requestScope.book.cover}" selected="selected">
                        <fmt:message key="cover.${requestScope.book.cover}"/></option>
                    <option value=""><fmt:message key="book.select_cover"/></option>
                    <option value="SOFT"><fmt:message key="cover.SOFT"/></option>
                    <option value="HARD"><fmt:message key="cover.HARD"/></option>
                    <option value="SPECIAL"><fmt:message key="cover.SPECIAL"/></option>
                </select>
            </label>
        </li>
        <li class="form-row">
            <label><fmt:message key="pages"/>
                <input type="number" name="pages" step="1" min="1" value="${requestScope.book.pages}">
            </label>
        </li>
        <li class="form-row">
            <label><fmt:message key="rating"/>
                <input type="number" name="rating" step="0.01" min="0.01" max="5.0" value="${requestScope.book.rating}">
            </label>
        </li>
        <li class="form-row">
            <input type="submit" name="Edit" formaction="/books/edit" value="<fmt:message key="edit"/>">
            <input type="submit" name="Delete" formaction="/books/delete" value="<fmt:message key="delete"/>">
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