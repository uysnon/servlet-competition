<%--
  Created by IntelliJ IDEA.
  User: avgor
  Date: 03.03.2021
  Time: 16:15
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<style type="text/css">
    <%@include file="/resources/login_page.css" %>
</style>
<html>
<head>
    <title>Title</title>
</head>
<body>

<form id="login" method="POST" action="/">
    <input type="hidden" name="command" value="login" />
    <h1>Вход</h1>
    <fieldset id="inputs">
        <input id="username" name="login" placeholder="Логин" autofocus="" required="" type="text">
        <input id="password" name="password" placeholder="Пароль" required="" type="password">
    </fieldset>
    <br/>
    ${errorLoginPassMessage}
    <br/>
    <fieldset id="actions">
        <input id="submit" value="Войти" type="submit">
    </fieldset>
</form>

</body>
</html>
