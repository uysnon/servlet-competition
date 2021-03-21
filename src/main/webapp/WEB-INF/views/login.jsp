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
    <%@include file="/resources/all_pages.css" %>
    <%@include file="/resources/login_page.css" %>
<%--    <%@include file="/lib/bootstrap/css/bootstrap.min.css" %>--%>

</style>
<html>
<head>
    <title>Title</title>
</head>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<body>

<body>

<form id="login" method="POST" action="/">
    <input type="hidden" name="command" value="login" />
    <h1>Вход</h1>
    <fieldset id="inputs">
        <input id="username" name="login" placeholder="Логин" autofocus="" required="" type="text" value="test_admin">
        <input id="password" name="password" placeholder="Пароль" required="" type="password" value="test_admin">
    </fieldset>
    <br/>
    ${errorLoginPassMessage}
    <br/>
    <fieldset id="actions">
        <input class="myButton middleButton" id="submit" value="Войти" type="submit">
        <a href="/?command=show_registration_page">
            <input class="myButton" type="button" value="Регистрация" />
        </a>
    </fieldset>
    <p>${registering_message}</p>
</form>

</body>
</html>
