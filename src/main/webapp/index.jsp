<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Bookstore</title>
    <link rel="stylesheet" href="css/style.css">
</head>
<body>
<h1>Welcome to bookstore_pronsky, dear ${sessionScope.user != null ? sessionScope.user.firstName : 'Guest'}</h1>
<jsp:include page="jsp/navbar.jsp"/>
<jsp:include page="jsp/searchbar.jsp"/>
</body>
</html>
