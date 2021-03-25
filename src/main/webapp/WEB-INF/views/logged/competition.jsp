<%--
  Created by IntelliJ IDEA.
  User: avgor
  Date: 20.03.2021
  Time: 12:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<style type="text/css">
    <%@include file="/resources/all_pages.css" %>
    <%@include file="/lib/bootstrap/css/bootstrap.min.css" %>
</style>


<head>
    <title>Конкурс ${competition_v.id}</title>
</head>
<body>
<div class="container container-main containter-top">
    <jsp:include page="../logged/top-bar.jsp"></jsp:include>
    <h1>Информация о конкурсе</h1>
    <c:catch var="exception">
        <c:if test="${! empty message}">
            <a href="${message.url}">
                <button type="button" class="btn btn-success">${message.content}</button>
            </a>
        </c:if>
    </c:catch>
    <div class="row">
        <c:forEach items="${actions}" var="action" varStatus="loop">
            <div class="col-2">
                <form method="post" action="/">
                    <input type="hidden" name="command" value="${action.name().toLowerCase()}"/>
                    <input type="hidden" name="competition_id" value="${competition_v.id}"/>
                    <button type="submit" class="btn btn-warning btn-dark">
                            ${action.title}
                    </button>
                </form>
            </div>
        </c:forEach>
        <%--        <c:choose>--%>
        <%--            <c:when test="${sessionScope.role.isAdministrator()}">--%>
        <%--                <a href="/?command=block&user_login=${user.login}&previousPage=show_user_list">--%>
        <%--                    <button type="button" class="btn btn-warning">Участвовать</button>--%>
        <%--                </a>--%>
        <%--            </c:when>--%>

        <%--        </c:choose>--%>
    </div>
    <br>
    <div class="row">
        <div class="col-6">
            <p><strong>Номер: </strong>${competition_v.id}</p>
            <p><strong>Задание: </strong>${competition_v.task}</p>
            <p><strong>Статус: </strong>${competition_v.status}</p>
            <p><strong>Количество экспертов: </strong>${competition_v.experts.size()}</p>
            <p><strong>Дата окончания регистрации: </strong>${competition_v.endRegistrationDate}</p>
            <p><strong>Дата окончания отправки решений: </strong>${competition_v.endSendingAnswerDate}</p>
            <p><strong>Стратегия оценивания: </strong>${competition_v.evaluationStrategy}</p>
        </div>
        <div class="col-6">
            <h3>Экспертный состав</h3>
            <br>
            <c:forEach items="${competition_v.getExperts()}" var="expert" varStatus="loop">
                <p><b>${loop.index+1}. </b> ${expert.name}</p>
            </c:forEach>
        </div>
    </div>
    <div class="row">
        <div class="col-12">
            <br>
            <h3>Результаты участников</h3>
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">Участник\Эксперты</th>
                    <c:forEach items="${participants_view.expertNames}" var="expertName" varStatus="loop">
                        <th scope="col">${expertName}</th>
                    </c:forEach>
                    <th>Итоговая оценка</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${participants_view.participationInfos}" var="participationInfo" varStatus="loop">
                    <tr>
                        <td>
                                ${participationInfo.participantName}
                        </td>
                        <c:forEach items="${participationInfo.marks}" var="mark" varStatus="loop">
                            <td>
                                <c:choose>
                                    <c:when test="${mark == 'NOT_DEFINED'}">
                                        <span class="not-defined">?</span>
                                    </c:when>
                                    <c:when test="${mark == 'NEGATIVE'}">
                                        <span class="error">-</span>
                                    </c:when>
                                    <c:when test="${mark == 'POSITIVE'}">
                                        <span class="success">+</span>
                                    </c:when>
                                </c:choose>
                            </td>
                        </c:forEach>
                        <td>
                            <c:choose>
                                <c:when test="${participationInfo.totalMark == 'NOT_DEFINED'}">
                                    <span class="not-defined">?</span>
                                </c:when>
                                <c:when test="${participationInfo.totalMark == 'NEGATIVE'}">
                                    <span class="error">-</span>
                                </c:when>
                                <c:when test="${participationInfo.totalMark == 'POSITIVE'}">
                                    <span class="success">+</span>
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
    </div>
</div>


</body>
</html>
