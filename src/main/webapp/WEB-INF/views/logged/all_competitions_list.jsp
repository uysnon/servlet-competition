<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
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
    <jsp:include page="top-bar.jsp"></jsp:include>
    <div class="row">
        <div class="col-12">
            <h1>Список научных конкурсов</h1>
            <table class="table">
                <thead>
                <tr>
                    <th scope="col">Номер</th>
                    <th scope="col">Задание</th>
                    <th scope="col">Статус</th>
                    <th scope="col">Регистрация, до</th>
                    <th scope="col">Ответы принимаются, до</th>
                </tr>
                </thead>
                <tbody>
                <c:forEach items="${competitions_v}" var="competition" varStatus="loop">
                    <tr style="transform: rotate(0);">
                        <th><a href="/?command=show_competition&id=${competition.id}"
                               class="stretched-link">${competition.id}</a></th>
                        <td>${competition.task}</td>
                        <td>${competition.status}</td>
                        <td>${competition.endRegistrationDate}</td>
                        <td>${competition.endSendingAnswerDate}</td>
                    </tr>
                </c:forEach>
                </tbody>
            </table>
        </div>
    </div>
</div>

</body>
</html>
