<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Results</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<div class="container">
    <h2>Quiz Results</h2>

    <c:if test="${empty results}">
        <p>No results found.</p>
    </c:if>

    <c:forEach var="result" items="${results}">
        <div>
            <p>User: ${result.user.username}</p>
            <p>Quiz: ${result.quiz.title}</p>
            <p>Score: ${result.score}</p>
            <p>Attempted At: ${result.attemptedAt}</p>
            <hr>
        </div>
    </c:forEach>

    <p><a href="/home">Back to home</a></p>
</div>
</body>
</html>
