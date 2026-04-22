<%@ page contentType="text/html;charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Quizzes</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<div class="container">
<h2>Available Quizzes</h2>

<c:if test="${empty quizzes}">
    <p>No quizzes available yet.</p>
</c:if>

<c:forEach var="quiz" items="${quizzes}">
    <div class="card">
        <h3>${quiz.title}</h3>
        <p>${quiz.description}</p>

        <c:if test="${attemptedQuizIds.contains(quiz.id)}">
            <p><span class="badge-attempted">Attempted</span></p>
        </c:if>

        <a class="btn btn-small" href="/quiz/${quiz.id}">
            <c:choose>
                <c:when test="${attemptedQuizIds.contains(quiz.id)}">View Result</c:when>
                <c:otherwise>Attempt</c:otherwise>
            </c:choose>
        </a>
    </div>
</c:forEach>

<p><a href="/home">Back to home</a></p>

</div>
</body>
</html>