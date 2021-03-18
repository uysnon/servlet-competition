<%--
  Created by IntelliJ IDEA.
  User: avgor
  Date: 15.03.2021
  Time: 17:35
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style type="text/css">
    <%@include file="/resources/all_pages.css" %>
    <%@include file="/lib/bootstrap/css/bootstrap.min.css" %>
    <%@include file="/resources/registration_page.css" %>
</style>
<html>
<head>
</head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<body>
<form class="registration-form" method="post" action="/">
    <input type="hidden" name="command" value="create_account"/>
    <div class="container">
        <h1>Создание учетной записи</h1>
        <p>Пожалуйста, заполните поля ниже, чтобы создать аккаунт</p>
        <hr>

        <b>Роль</b>
        <select name="role" class="form-select" aria-label="Выберите роль учетной записи">
            <option value="participant">Участник</option>
            <option value="expert">Эксперт</option>
            <option value="administrator">Администратор</option>
        </select>
        <br>

        <label for="login">
            <b>Логин</b>
            <c:if test="${!login_validation.validationClass.isNormal()}"><br><span
                    class="error">${login_validation.description}</span></c:if>
        </label>
        <input type="text" placeholder="Введите логин" name="login" id="login" value="${login}" required>


        <label for="name">
            <b>Имя</b>

        </label>
        <input type="text" placeholder="Введите имя" name="name" id="name" value="${name}" required>


        <label for="password">
            <b>Пароль</b>
            <c:if test="${!password_validation.validationClass.isNormal()}"><br><span
                    class="error">${password_validation.description}</span></c:if>
        </label>
        <input type="password" placeholder="Введите пароль" name="password" id="password" value="${password}"
               required>

        <label for="password_repeat">
            <b>Повторите пароль</b>
            <c:if test="${!password_repeat_validation.validationClass.isNormal()}"><br><span
                    class="error">${password_repeat_validation.description}</span></c:if>
        </label>
        <input type="password" placeholder="Повторите пароль" name="password_repeat" id="password_repeat"
               value="${password_repeat}" required>
        <hr>

        <button type="submit" class="registerbtn myButton">Зарегистрироваться</button>
    </div>
</form>

</body>
</html>
