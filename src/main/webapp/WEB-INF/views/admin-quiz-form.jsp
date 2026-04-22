<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Admin - Quiz Form</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<div class="container">
    <h1><c:choose><c:when test="${not empty quiz.id}">Edit Quiz</c:when><c:otherwise>Create Quiz</c:otherwise></c:choose></h1>

    <form action="/admin/quizzes/save" method="post">
        <input type="hidden" name="id" value="${quiz.id}">

        <label>Title</label>
        <input type="text" name="title" value="${quiz.title}" required>

        <label>Description</label>
        <textarea name="description" rows="3" required>${quiz.description}</textarea>

        <h2>Questions</h2>
        <div id="questions-container">
            <c:forEach var="question" items="${quiz.questions}" varStatus="qStatus">
                <div class="question-block">
                    <input type="hidden" name="questions[${qStatus.index}].id" value="${question.id}">
                    <label>Question</label>
                    <input type="text" name="questions[${qStatus.index}].questionText" value="${question.questionText}" required>

                    <div class="options-block" data-q-index="${qStatus.index}">
                        <c:forEach var="option" items="${question.options}" varStatus="oStatus">
                            <div class="option-row">
                                <input type="hidden" name="questions[${qStatus.index}].options[${oStatus.index}].id" value="${option.id}">
                                <input type="text" name="questions[${qStatus.index}].options[${oStatus.index}].optionText" value="${option.optionText}" placeholder="Option text" required>
                                <label>
                                    <input type="checkbox" name="questions[${qStatus.index}].options[${oStatus.index}].correct" <c:if test="${option.correct}">checked</c:if>> Correct
                                </label>
                            </div>
                        </c:forEach>
                    </div>

                    <button class="btn btn-secondary btn-small" type="button" onclick="addOption(this, ${qStatus.index})">Add Option</button>
                </div>
            </c:forEach>
        </div>

        <button class="btn btn-secondary" type="button" onclick="addQuestion()">Add Question</button>
        <button class="btn" type="submit">Save Quiz</button>
    </form>

    <p><a href="/admin/quizzes">Back to Manage Quizzes</a></p>
</div>

<script>
let questionIndex = ${empty quiz.questions ? 0 : quiz.questions.size()};
const optionCounters = {};

<c:forEach var="question" items="${quiz.questions}" varStatus="qStatus">
optionCounters[${qStatus.index}] = ${empty question.options ? 0 : question.options.size()};
</c:forEach>

function addQuestion() {
    const qIndex = questionIndex++;
    optionCounters[qIndex] = 0;

    const block = document.createElement('div');
    block.className = 'question-block';
    block.innerHTML =
        '<label>Question</label>' +
        '<input type="text" name="questions[' + qIndex + '].questionText" required>' +
        '<div class="options-block" data-q-index="' + qIndex + '"></div>' +
        '<button class="btn btn-secondary btn-small" type="button" onclick="addOption(this, ' + qIndex + ')">Add Option</button>';

    document.getElementById('questions-container').appendChild(block);
    const addButton = block.querySelector('button');
    addOption(addButton, qIndex);
    addOption(addButton, qIndex);
}

function addOption(button, qIndex) {
    const questionBlock = button.closest('.question-block');
    const container = questionBlock.querySelector('.options-block');
    if (!container) {
        return;
    }

    const oIndex = optionCounters[qIndex] || 0;
    optionCounters[qIndex] = oIndex + 1;

    const row = document.createElement('div');
    row.className = 'option-row';
    row.innerHTML =
        '<input type="text" name="questions[' + qIndex + '].options[' + oIndex + '].optionText" placeholder="Option text" required>' +
        '<label><input type="checkbox" name="questions[' + qIndex + '].options[' + oIndex + '].correct"> Correct</label>';

    container.appendChild(row);
}

if (questionIndex === 0) {
    addQuestion();
}
</script>
</body>
</html>
