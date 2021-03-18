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
<head>
</head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>_
<body>


<div class="container container-main containter-top">
    <div class="row">
        <div class="col-9">
            <div>
                <a href="?command=show_create_account_page">
                    <button type="button" class="btn  btn-light block-center">Создать учетную запись</button>
                </a>

                <a href="?command=show_create_account_page">
                    <button type="button" class="btn  btn-light block-center">Создать конкурс</button>
                </a>
                <a href="?command=show_create_account_page">
                    <button type="button" class="btn  btn-light block-center">Список учетных записей
                    </button>
                </a>

                <a href="?command=show_create_account_page">
                    <button type="button" class="btn  btn-light block-center">Список конкурсов
                    </button>
                </a>

            </div>
        </div>

        <div class="col-3 user-account-element">

                <b></span></spn><c:out value="${sessionScope.role}"/></b>
                <br/>
                <c:out value="${sessionScope.login}"/>
                <br/>
                <a href="/?command=logout">
                    <button type="button" class="btn  btn-dark block-center">logout
                    </button></a>
            </div>

    </div>


    <h1>Users list [admin]</h1>
    <h2>Участники</h2>
    <div class="user-list">
        <c:forEach items="${participant_users}" var="user">
            <c:choose>
                <c:when test="${user.status == 'ACTIVE'}">
                    <div class="user active card">
                        <div class="user-info">
                            <span>Логин: ${user.login}</span>
                            <br/>
                            <span>Имя: ${user.name}</span>
                            <br/>
                            <span>Статус: ${user.status.description}</span>
                            <br/>
                        </div>

                        <div class="user-actions">
                            <a href="/?command=block&user_login=${user.login}&previousPage=show_user_list">Заблокировать</a>
                            <br/>
                            <a href="/?command=delete&user_login=${user.login}&previousPage=show_user_list">Удалить</a>
                        </div>
                    </div>
                </c:when>

                <c:when test="${user.status == 'BLOCKED'}">
                    <div class="user blocked">
                        <div class="user-info">
                            <span>Логин: ${user.login}</span>
                            <br/>
                            <span>Имя: ${user.name}</span>
                            <br/>
                            <span>Статус: ${user.status.description}</span>
                            <br/>
                        </div>
                        <div class="user-actions">
                            <a href="/?command=unblock&user_login=${user.login}&previousPage=show_user_list">Разблокировать</a>
                            <br/>
                            <a href="/?command=delete&user_login=${user.login}&previousPage=show_user_list">Удалить</a>
                        </div>
                    </div>
                </c:when>

                <c:when test="${user.status == 'DELETED'}">
                    <div class="user deleted">
                        <div class="user-info">
                            <span>Логин: ${user.login}</span>
                            <br/>
                            <span>Имя: ${user.name}</span>
                            <br/>
                            <span>Статус: ${user.status.description}</span>
                            <br/>
                        </div>
                    </div>
                </c:when>


                <c:otherwise>
                    no status
                </c:otherwise>
            </c:choose>

        </c:forEach>
    </div>

    <h2>Эксперты</h2>
    <div class="user-list">
        <c:forEach items="${expert_users}" var="user">
            <c:choose>
                <c:when test="${user.status == 'ACTIVE'}">
                    <div class="user active">
                        <div class="user-info">
                            <span>Логин: ${user.login}</span>
                            <br/>
                            <span>Имя: ${user.name}</span>
                            <br/>
                            <span>Статус: ${user.status.description}</span>
                            <br/>
                        </div>

                        <div class="user-actions">
                            <a href="/?command=block&user_login=${user.login}&previousPage=show_user_list">Заблокировать</a>
                            <br/>
                            <a href="/?command=delete&user_login=${user.login}&previousPage=show_user_list">Удалить</a>
                        </div>
                    </div>
                </c:when>

                <c:when test="${user.status == 'BLOCKED'}">
                    <div class="user blocked">
                        <div class="user-info">
                            <span>Логин: ${user.login}</span>
                            <br/>
                            <span>Имя: ${user.name}</span>
                            <br/>
                            <span>Статус: ${user.status.description}</span>
                            <br/>
                        </div>
                        <div class="user-actions">
                            <a href="/?command=unblock&user_login=${user.login}&previousPage=show_user_list">Разблокировать</a>
                            <br/>
                            <a href="/?command=delete&user_login=${user.login}&previousPage=show_user_list">Удалить</a>
                        </div>
                    </div>
                </c:when>

                <c:when test="${user.status == 'DELETED'}">
                    <div class="user deleted">
                        <div class="user-info">
                            <span>Логин: ${user.login}</span>
                            <br/>
                            <span>Имя: ${user.name}</span>
                            <br/>
                            <span>Статус: ${user.status.description}</span>
                            <br/>
                        </div>
                    </div>
                </c:when>


                <c:otherwise>
                    no status
                </c:otherwise>
            </c:choose>

        </c:forEach>
    </div>

    <h2>Администраторы</h2>
    <div class="user-list">
        <c:forEach items="${admin_users}" var="user">
            <c:choose>
                <c:when test="${user.status == 'ACTIVE'}">
                    <div class="user active">
                        <div class="user-info">
                            <span>Логин: ${user.login}</span>
                            <br/>
                            <span>Имя: ${user.name}</span>
                            <br/>
                            <span>Статус: ${user.status.description}</span>
                            <br/>
                        </div>

                        <div class="user-actions">
                            <c:if test="${user.login eq sessionScope.login}">
                                <a href="/?command=delete&user_login=${user.login}&previousPage=show_user_list">Удалить</a>
                            </c:if>
                        </div>
                    </div>
                </c:when>

                <c:when test="${user.status == 'BLOCKED'}">
                    <div class="user blocked">
                        <div class="user-info">
                            <span>Логин: ${user.login}</span>
                            <br/>
                            <span>Имя: ${user.name}</span>
                            <br/>
                            <span>Статус: ${user.status.description}</span>
                            <br/>
                        </div>
                    </div>
                </c:when>

                <c:when test="${user.status == 'DELETED'}">
                    <div class="user deleted">
                        <div class="user-info">
                            <span>Логин: ${user.login}</span>
                            <br/>
                            <span>Имя: ${user.name}</span>
                            <br/>
                            <span>Статус: ${user.status.description}</span>
                            <br/>
                        </div>
                    </div>
                </c:when>


                <c:otherwise>
                    no status
                </c:otherwise>
            </c:choose>

        </c:forEach>
    </div>

</body>
</html>
