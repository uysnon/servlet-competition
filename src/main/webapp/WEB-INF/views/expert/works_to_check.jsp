<%--
  Created by IntelliJ IDEA.
  User: avgor
  Date: 03.03.2021
  Time: 16:21
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
</head>
<body>
<div class="container container-main containter-top">
    <jsp:include page="../logged/top-bar.jsp"></jsp:include>
    <div class="row">
        <div class="col-12">
            <h1>Список работ к проверке</h1>
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">Номер конкурса</th>
                    <th scope="col">Задание</th>
                    <th scope="col">Участник</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${competitions_to_check_v}" var="competition" varStatus="loop">
                    <tr style="transform: rotate(0);">
                        <th><a href="/?command=show_work_to_check&id=${competition.participationId}"
                               class="stretched-link">${competition.competitionId}</a></th>
                        <td>${competition.task}</td>
                        <td>${competition.participantName}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
    <div class="row">
        <div class="col-12">
            <h1>Список проверенных работ</h1>
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">Номер конкурса</th>
                    <th scope="col">Задание</th>
                    <th scope="col">Участник</th>
                    <th scope="col">Статус проверки</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${competitions_history_v}" var="competition" varStatus="loop">
                    <tr style="transform: rotate(0);">
                        <th><a href="/?command=show_work_to_check&id=${competition.participationId}"
                               class="stretched-link">${competition.competitionId}</a></th>
                        <td>${competition.task}</td>
                        <td>${competition.participantName}</td>
                        <td>${competition.checkStatus}</td>

                        <td>
                            <c:choose>
                                <c:when test="${competition.mark == 'NOT_DEFINED'}">
                                    <span class="not-defined">?</span>
                                </c:when>
                                <c:when test="${competition.mark == 'NEGATIVE'}">
                                    <span class="error">Не одобрено</span>
                                </c:when>
                                <c:when test="${competition.mark == 'POSITIVE'}">
                                    <span class="success">Одобрено</span>
                                </c:when>
                            </c:choose>
                        </td>
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