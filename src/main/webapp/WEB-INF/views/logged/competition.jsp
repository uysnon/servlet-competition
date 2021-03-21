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
    <title>Конкурс ${competition.id}</title>
</head>
<body>
<div class="container container-main containter-top">
    <jsp:include page="../logged/top-bar.jsp"></jsp:include>
    <h1>Информация о конкурсе</h1>
    <br>
    <div class="row">
        <div class="col-6">
            <p><b>Номер: </b>${competition_v.id}</p>
            <p><b>Задание: </b>${competition_v.task}</p>
            <p><b>Статус: </b>${competition_v.status}</p>
            <p><b>Количество экспертов: </b>${competition_v.experts.size()}</p>
            <p><b>Дата окончания регистрации: </b>${competition_v.endRegistrationDate}</p>
            <p><b>Дата окончания регистрации: </b>${competition_v.endRegistrationDate}</p>
            <p><b>Дата окончания отправки решений: </b>${competition_v.endSendingAnswerDate}</p>
            <p><b>Стратегия оценивания: </b>${competition_v.evaluationStrategy}</p>
        </div>
        <div class="col-6">
            <h3>Экспертный состав</h3>
            <br>
            <c:forEach items="${competition_v.getExperts()}" var="expert" varStatus="loop">
                <p><b>${loop.index+1}. </b> ${expert.name}</p>
            </c:forEach>
        </div>
    </div>
</div>


</body>
</html>
