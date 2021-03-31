<%--
  Created by IntelliJ IDEA.
  User: avgor
  Date: 20.03.2021
  Time: 14:45
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>
<html>
<style type="text/css">
    <%@include file="/resources/all_pages.css" %>
    <%@include file="/lib/bootstrap/css/bootstrap.min.css" %>
    <%@include file="/resources/create_competition.css" %>

</style>
<head>
    <title>Создание конкурса</title>
</head>
<body>
<fmt:requestEncoding value="UTF-8"/>
<div class="container container-main containter-top">
    <jsp:include page="../logged/top-bar.jsp"></jsp:include>
    <div class="row">
        <div class="col-12">

            <form class="registration-form" method="post" action="/">
                <input type="hidden" name="command" value="create_competition"/>
                <div class="container-my">
                    <h1>Создание конкурса</h1>
                    <c:catch var="exception">
                        <c:if test="${! empty success_creation}">
                            <span class="success">${success_creation}</span>
                        </c:if>
                    </c:catch>

                    <p>Пожалуйста, заполните поля ниже, чтобы создать конкурс</p>

                    <div class="form-group">
                        <label for="exampleFormControlTextarea1" class="col-form-label"><strong>Задание</strong></label>
                        <textarea name="task" class="form-control" id="exampleFormControlTextarea1" rows="3"
                                  required><c:out value="${task}"/></textarea>

                    </div>

                    <div class="form-group row">
                        <strong>Окончание регистрации</strong>
                        <br>
                        <span class="error">
                            ${end_registration_date_validation_result.description}
                        </span>

                        <div class="col-4">
                            <label for="end-registration-date-input">Дата</label>
                            <input name="end-registration-date" class="form-control" type="date"
                                   value="${end_registration_date}"
                                   id="end-registration-date-input">
                        </div>
                        <div class="col-4">
                            Время
                            <input name="end-registration-time" class="form-control" type="time"
                                   value="${end_registration_time}"
                                   id="end-registration-time-input">
                        </div>
                    </div>

                    <div class="form-group row">
                        <strong>Крайний срок отправки ответов</strong>
                        <br>
                        <span class="error">
                            ${end_sending_answers_date_validation_result.description}
                        </span>
                        <div class="col-4">
                            <label for="end-sending-answers-date-input">Дата</label>
                            <input name="end-sending-answers-date" class="form-control" type="date"
                                   value="${end_sending_answers_date}"
                                   id="end-sending-answers-date-input">
                        </div>
                        <div class="col-4">
                            <label for="end-sending-answers-time-input">Время</label>
                            <input name="end-sending-answers-time" class="form-control" type="time"
                                   value="${end_sending_answers_time}"
                                   id="end-sending-answers-time-input">
                        </div>
                    </div>

                    <strong>Экспертный состав</strong>
                    <br>
                    <span class="error">${experts_validation_result.description}</span>
                    <div class="form-group row">
                        <select id="selected-experts" name="selected-experts" class="form-control" size="5" multiple
                                aria-label="laaabel">
                            <c:forEach items="${experts}" var="expert">
                                <option
                                        value="${expert.id}"
                                    ${selected_experts.contains(expert.id) eq true ? 'selected' : ''}
                                >
                                        ${expert.name} @${expert.login}
                                </option>
                            </c:forEach>
                        </select>
                    </div>

                    <strong>Стратегия оценивания</strong>
                    <br>
                    <span class="error">${strategy_value_validation_result.description}</span>
                    <div class="form-group row">
                        <div class="col-8">
                            <select name="selected-strategy-name" class="form-select" aria-label="laaabel">
                                <c:forEach items="${strategies}" var="strategy">
                                    <option value="${strategy.name()}"
                                        ${strategy == selected_strategy ? 'selected' : ''}
                                    >
                                            ${strategy.title}</option>
                                </c:forEach>
                            </select>
                        </div>
                        <div class="col-4">
                            <input name="strategy-value" type="text" class="form-control" id="formGroupExampleInput"
                                   placeholder="Величина стратегии" value="${strategy_value}">
                        </div>
                    </div>


                    <button type="submit" class="registerbtn myButton">Создать</button>
                </div>
            </form>
        </div>
    </div>


</body>
</html>
