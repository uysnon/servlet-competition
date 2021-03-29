<%--
  Created by IntelliJ IDEA.
  User: avgor
  Date: 29.03.2021
  Time: 21:13
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
    <title>Учетная запись</title>
</head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<body>
<div class="container container-main containter-top">
    <jsp:include page="../logged/top-bar.jsp"></jsp:include>
    <div class="row">
        <div class="col-12">
            <form class="registration-form" method="post" action="/">
                <input type="hidden" name="command" value="edit_account_by_user"/>
                <div class="container top-container">
                    <h1>Учетная запись пользователя</h1>
                    <hr>
                    <div class="form-group row">
                        <label for="id">
                            id
                        </label>
                        <input type="text" class="form-control" placeholder="Введите логин" name="id" id="id"
                               value="${id}"
                               readonly>
                    </div>

                    <div class="form-group row">
                        <label for="login">
                            Логин
                        </label>
                        <input type="text" class="form-control" name="login" id="login"
                               value="${login}"
                               readonly>
                    </div>

                    <div class="form-group row">
                        <label for="name">
                            <b>Имя [возможно изменение]</b>

                        </label>
                        <input type="text" class="form-control" placeholder="Введите имя" name="name" id="name"
                               value="${name}" required>
                    </div>

                    <div class="form-group row">
                        <label for="password">
                            <b>Новый пароль</b>
                            <c:if test="${!password_validation.validationClass.isNormal()}"><br><span
                                    class="error">${password_validation.description}</span></c:if>
                        </label>
                        <input class="form-control" type="password" placeholder="Введите пароль" name="password"
                               id="password" value="${password}"
                               required>
                    </div>

                    <div>

                        <label for="password_repeat">
                            <b>Повторите пароль</b>
                            <c:if test="${!password_repeat_validation.validationClass.isNormal()}"><br><span
                                    class="error">${password_repeat_validation.description}</span></c:if>
                        </label>
                        <input type="password" class="form-control" placeholder="Повторите пароль"
                               name="password_repeat" id="password_repeat"
                               value="${password_repeat}" required>

                    </div>
                    <hr>
                    <button type="submit" class="registerbtn myButton">Изменить</button>
                </div>
            </form>
        </div>
    </div>
</div>
</body>
</html>