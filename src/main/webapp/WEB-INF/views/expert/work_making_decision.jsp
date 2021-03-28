<%--
  Created by IntelliJ IDEA.
  User: avgor
  Date: 25.03.2021
  Time: 21:25
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
    <title>Проверка №${competition_v.participationId}</title>
</head>
<body>
<div class="container container-main containter-top">
    <jsp:include page="../logged/top-bar.jsp"></jsp:include>
    <div class="row">
        <h1>Проверка
            <a href="/?command=show_competition&id=${competition_v.competitionId}">
                конкурса №${competition_v.competitionId}
            </a>
        </h1>
        <c:catch var="exception">
            <c:if test="${! empty message}">
                <span class="error">${message}</span>
            </c:if>
        </c:catch>
    </div>
    <div class="row">

        <div class="col-6">
            <h2>Работа для оценивания</h2>

            <p><strong>Задание: </strong>${competition_v.task}</p>
            <p><strong>Участник: </strong>${competition_v.participantName}</p>
            <p><strong>Ответ участника: </strong>${competition_v.answer}</p>

        </div>
        <div class="col-6">
            <h3>Ваше решение:</h3>
            <c:if test="${competition_v.isChecked()}">
                <div>
                    <p><strong>Статус: </strong>${competition_v.checkStatus}</p>
                    <c:catch var="exception">
                        <c:if test="${! empty competition_v.comment}">
                            <p><strong>Комментарий: </strong>${competition_v.comment}</p>
                        </c:if>
                    </c:catch>
                </div>
            </c:if>
            <c:if test="${not competition_v.isChecked()}">
                <form method="post" action="/">
                    <input type="hidden" name="command" value="make_decision"/>
                    <input type="hidden" name="id" value="${competition_v.participationId}">

                    <label for="input_comment">Комментарий</label>
                    <textarea
                            class="form-control"
                            name="comment"
                            id="input_comment">

                </textarea>

                    <br>
                    <button type="submit" name="mark" value="positive" class="btn btn-success">
                        Одобрить
                    </button>

                    <input type="hidden" name="id" value="${competition_v.participationId}">
                    <button type="submit" name="mark" value="negative" class="btn btn-danger">
                        Забраковать
                    </button>
                </form>
            </c:if>

        </div>
    </div>


</div>


</body>
</html>
