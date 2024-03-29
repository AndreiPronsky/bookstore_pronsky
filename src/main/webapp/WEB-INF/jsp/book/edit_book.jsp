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
<jsp:include page="../navbar.jsp"/>
<h1><spring:message code="edit_book"/></h1>
<form:form action="/books/edit" method="post" modelAttribute="bookDto">
    <form:input type="hidden" path="id" value="${bookDto.id}"/>
    <table>
        <tr>
            <td><spring:message code="title"/></td>
            <td><form:input path="title" type="text" value="${bookDto.title}"/></td>
            <td><form:errors path="title"/></td>
        </tr>
        <tr>
            <td><spring:message code="author"/></td>
            <td><form:input path="author" type="text" value="${bookDto.author}"/></td>
            <td><form:errors path="author"/></td>
        </tr>
        <tr>
            <td><spring:message code="isbn"/></td>
            <td><form:input path="isbn" type="text" value="${bookDto.isbn}"/></td>
            <td><form:errors path="isbn"/></td>
        </tr>
        <tr>
            <td><spring:message code="price"/></td>
            <td><form:input path="price" type="number" value="${bookDto.price}" step="0.01"/></td>
            <td><form:errors path="price"/></td>
        </tr>
        <tr>
            <td><spring:message code="genre"/></td>
            <td><form:select path="genre" required="required">
                <option value="${bookDto.genre}" selected="selected">
                    <spring:message code="genre.${bookDto.genre}"/></option>
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
            </form:select></td>
        </tr>
        <tr>
            <td><spring:message code="cover"/></td>
            <td><form:select path="cover" required="required">
                    <option value="${bookDto.cover}" selected="selected">
                        <spring:message code="cover.${bookDto.cover}"/></option>
                <option value=""><spring:message code="book.select_cover"/></option>
                <option value="SOFT"><spring:message code="cover.SOFT"/></option>
                <option value="HARD"><spring:message code="cover.HARD"/></option>
                <option value="SPECIAL"><spring:message code="cover.SPECIAL"/></option>
            </form:select></td>
        </tr>
        <tr>
            <td><spring:message code="pages"/></td>
            <td><form:input path="pages" type="number" value="${bookDto.pages}" step="1" min="1"/></td>
            <td><form:errors path="pages"/></td>
        </tr>
        <tr>
            <td><spring:message code="rating"/></td>
            <td><form:input path="rating" type="number" value="${bookDto.rating}" step="0.01" min="0.01"/></td>
            <td><form:errors path="rating"/></td>
        </tr>
    </table>
        <li class="form-row">
            <input type="submit" name="Edit" formaction="/books/edit" value="<spring:message code="edit"/>">
            <input type="submit" name="Delete" formaction="/books/delete" value="<spring:message code="delete"/>">
        </li>
</form:form>
</body>
</html>