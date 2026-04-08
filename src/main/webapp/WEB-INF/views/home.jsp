<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Home</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<div class="container">
    <h1>Welcome, ${username}</h1>

    <c:if test="${isAdmin}">
        <p>You are logged in as ADMIN.</p>
        <a class="btn" href="/admin/quizzes">Manage Quizzes</a>
        <a class="btn btn-secondary" href="/admin/results">View All Results</a><br><br>
    </c:if>

    <c:if test="${!isAdmin}">
        <a class="btn" href="/quizzes">View Quizzes</a>
        <a class="btn btn-secondary" href="/results">My Results</a><br><br>
    </c:if>

    <form action="/logout" method="post">
        <button type="submit">Logout</button>
    </form>
</div>
</body>
</html>