<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Attempt Quiz</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<div class="container">

<h2>${quiz.title}</h2>
<p>${quiz.description}</p>

<form action="/submit" method="post">

    <input type="hidden" name="quizId" value="${quiz.id}"/>

    <c:forEach var="q" items="${quiz.questions}">
        <p>${q.questionText}</p>

        <c:forEach var="opt" items="${q.options}">
            <input type="radio" name="answers[${q.id}]" value="${opt.id}">
            ${opt.optionText} <br>
        </c:forEach>

        <br>
    </c:forEach>

    <button type="submit">Submit</button>
</form>

<p><a href="/quizzes">Back to quizzes</a></p>

</div>
</body>
</html>