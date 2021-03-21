<%--
  Created by IntelliJ IDEA.
  User: avgor
  Date: 03.03.2021
  Time: 16:16
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<style type="text/css">
    <%@include file="/resources/all_pages.css" %>
    <%@include file="/lib/bootstrap/css/bootstrap.min.css" %>
    <%@include file="/resources/users_list_page.css" %>
</style>
<html>
<head>
</head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
<body>
<div class="container container-main containter-top">
    <jsp:include page="../logged/top-bar.jsp"></jsp:include>
    <h1>Список учетных записей</h1>
    <br>
        <h2>Участники</h2>
    <div class="user-list">
        <table class="table">
            <thead>
            <tr>
                <th scope="col">Login</th>
                <th scope="col">Name</th>
                <th scope="col">Status</th>
                <th scope="col">Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${participant_users}" var="user">
                <tr>
                    <th scope="row">${user.login}</th>
                    <td>${user.name}</td>
                    <td>${user.status.description}</td>
                    <td>
                        <c:choose>
                            <c:when test="${user.status == 'ACTIVE'}">
                                <a href="/?command=block&user_login=${user.login}&previousPage=show_user_list">
                                    <button type="button" class="btn btn-warning">Заблокировать</button>
                                </a>
                                <a href="/?command=delete&user_login=${user.login}&previousPage=show_user_list">
                                    <button type="button" class="btn btn-danger">Удалить</button>
                                </a>

                            </c:when>

                            <c:when test="${user.status == 'BLOCKED'}">

                                <a href="/?command=unblock&user_login=${user.login}&previousPage=show_user_list">
                                    <button type="button" class="btn btn-success">Разблокировать
                                    </button>
                                </a>

                                <a href="/?command=delete&user_login=${user.login}&previousPage=show_user_list">
                                    <button type="button" class="btn btn-danger">Удалить</button>
                                </a>

                            </c:when>
                            <c:otherwise>
                                -
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <h2>Эксперты</h2>
    <div class="user-list">
        <table class="table">
            <thead>
            <tr>
                <th scope="col">Login</th>
                <th scope="col">Name</th>
                <th scope="col">Status</th>
                <th scope="col">Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${expert_users}" var="user">
                <tr>
                    <th scope="row">${user.login}</th>
                    <td>${user.name}</td>
                    <td>${user.status.description}</td>
                    <td>
                        <c:choose>
                            <c:when test="${user.status == 'ACTIVE'}">
                                <a href="/?command=block&user_login=${user.login}&previousPage=show_user_list">
                                    <button type="button" class="btn btn-warning">Заблокировать</button>
                                </a>
                                <a href="/?command=delete&user_login=${user.login}&previousPage=show_user_list">
                                    <button type="button" class="btn btn-danger">Удалить</button>
                                </a>

                            </c:when>

                            <c:when test="${user.status == 'BLOCKED'}">

                                <a href="/?command=unblock&user_login=${user.login}&previousPage=show_user_list">
                                    <button type="button" class="btn btn-success">Разблокировать
                                    </button>
                                </a>

                                <a href="/?command=delete&user_login=${user.login}&previousPage=show_user_list">
                                    <button type="button" class="btn btn-danger">Удалить</button>
                                </a>

                            </c:when>
                            <c:otherwise>
                                -
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

    <h2>Администраторы</h2>
    <div class="user-list">

        <table class="table">
            <thead>
            <tr>
                <th scope="col">Login</th>
                <th scope="col">Name</th>
                <th scope="col">Status</th>
                <th scope="col">Actions</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${admin_users}" var="user">
                <tr>
                    <th scope="row">${user.login}</th>
                    <td>${user.name}</td>
                    <td>${user.status.description}</td>
                    <td>
                        <c:choose>
                            <c:when test="${user.status == 'ACTIVE'}">
                                <c:if test="${user.login eq sessionScope.login}">
                                    <a href="/?command=delete&user_login=${user.login}&previousPage=show_user_list">
                                        <button type="button" class="btn btn-danger">Удалить</button>
                                    </a>
                                </c:if>
                            </c:when>
                            <c:otherwise>
                                -
                            </c:otherwise>
                        </c:choose>
                    </td>
                </tr>
            </c:forEach>
            </tbody>
        </table>
    </div>

</body>
</html>
