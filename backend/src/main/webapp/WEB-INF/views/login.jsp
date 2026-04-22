<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Login</title>
    <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<div class="container">
    <h2>User Login</h2>

    <c:if test="${param.error != null}">
        <p style="color:red;">Invalid username or password.</p>
    </c:if>

    <c:if test="${param.logout != null}">
        <p style="color:green;">You have been logged out.</p>
    </c:if>

    <c:if test="${not empty message}">
        <p style="color:green;">${message}</p>
    </c:if>

    <form action="/perform_login" method="post">
        Username: <input type="text" name="username"/><br><br>
        Password: <input type="password" name="password"/><br><br>

        <button type="submit">Login</button>
    </form>

    <p>New user? <a href="/register">Create account</a></p>
</div>
</body>
</html>