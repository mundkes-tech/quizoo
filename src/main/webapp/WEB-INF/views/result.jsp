<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Result</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<div class="container">
    <c:if test="${not empty error}">
        <p style="color:red;">${error}</p>
    </c:if>

    <c:if test="${alreadyAttempted}">
        <h2>You already attempted this quiz.</h2>
    </c:if>

    <c:if test="${not empty result}">
        <h2>Your Score: ${result.score}</h2>
        <p>Attempted At: ${result.formattedAttemptedAt}</p>
    </c:if>

    <p><a href="/quizzes">Back to quizzes</a></p>
    <p><a href="/results">My results</a></p>
</div>
</body>
</html>