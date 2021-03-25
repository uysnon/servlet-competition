<%--
  Created by IntelliJ IDEA.
  User: avgor
  Date: 25.03.2021
  Time: 20:42
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
    <title>Участие в №${competition_v.id}</title>
</head>
<body>
<div class="container container-main containter-top">
    <jsp:include page="../logged/top-bar.jsp"></jsp:include>
    <h1>Участие в конкурсе №${competition_v.id}</h1>
    <c:catch var="exception">
        <c:if test="${! empty message}">
            <a href="${message.url}">
                <button type="button" class="btn btn-success">${message.content}</button>
            </a>
        </c:if>
    </c:catch>
    <h2>Информация о конкурсе</h2>
    <div class="col-6">
        <p><strong>Задание: </strong>${competition_v.task}</p>
        <p><strong>Дата окончания отправки решений: </strong>${competition_v.endSendingAnswerDate}</p>
        <p><strong>Стратегия оценивания: </strong>${competition_v.evaluationStrategy}</p>
        <br>
        <strong>Ответ пользователя</strong>
        <form method="post" action="/">
            <input type="hidden" name="command" value="${competition_participation.id}"/>
            <textarea class="form-control" id="input_answer" rows="3"></textarea>
            <c:if test="${empty competition_participation.answer}">
                <button type="submit" class="btn btn-warning btn-dark">
                    Отправить
                </button>
            </c:if>
        </form>
    </div>
    <div class="col-6">
        <table class="table">
            <thead>
            <tr>
                <th>Эксперт</th>
                <th>Оценка</th>
                <th>Комментарий</th>
            </tr>
            </thead>
            <tbody>
            <c:forEach items="${decisions}" var="decision">
                <tr>
                    <td>${decision.expert.name}</td>
                    <td>
                        <c:choose>
                            <c:when test="${decision.mark == 'NOT_DEFINED'}">
                                <span class="not-defined">?</span>
                            </c:when>
                            <c:when test="${decision.mark == 'NEGATIVE'}">
                                <span class="error">-</span>
                            </c:when>
                            <c:when test="${decision.mark == 'POSITIVE'}">
                                <span class="success">+</span>
                            </c:when>
                        </c:choose>
                    </td>
                    <td>
                            ${decision.comment}
                    </td>
                </tr>

            </c:forEach>
            </tbody>

        </table>
    </div>

</div>


</body>
</html>
