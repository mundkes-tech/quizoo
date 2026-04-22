<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Admin - Manage Quizzes</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<div class="container">
    <h1>Manage Quizzes</h1>

    <c:if test="${not empty message}">
        <p class="success">${message}</p>
    </c:if>

    <p><a class="btn" href="/admin/quizzes/new">Create New Quiz</a></p>
    <p><a class="btn btn-secondary" href="/admin/results">View All Results</a></p>

    <c:if test="${empty quizzes}">
        <p class="muted">No quizzes found.</p>
    </c:if>

    <c:forEach var="quiz" items="${quizzes}">
        <div class="card">
            <h3>${quiz.title}</h3>
            <p>${quiz.description}</p>
            <div class="row-actions">
                <a class="btn btn-small" href="/admin/quizzes/edit/${quiz.id}">Edit</a>
                <form action="/admin/quizzes/delete/${quiz.id}" method="post" style="display:inline;">
                    <button class="btn btn-danger btn-small" type="submit">Delete</button>
                </form>
            </div>
        </div>
    </c:forEach>

    <p><a href="/home">Back to Home</a></p>
</div>
</body>
</html>
