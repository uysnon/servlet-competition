<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<div class="top-bar row">
    <div class="col-9">
        <div>
            <c:forEach items="${sessionScope.navigationCommands}" var="command">
                <a href="?command=${command}">
                    <button type="button" class="btn  btn-light block-center">${command.getTitle()}</button>
                </a>
            </c:forEach>
            <%--            <a href="?command=show_create_account_page">--%>
            <%--                <button type="button" class="btn  btn-light block-center">Создать учетную запись</button>--%>
            <%--            </a>--%>

            <%--            <a href="?command=show_create_account_page">--%>
            <%--                <button type="button" class="btn  btn-light block-center">Создать конкурс</button>--%>
            <%--            </a>--%>
            <%--            <a href="?command=show_create_account_page">--%>
            <%--                <button type="button" class="btn  btn-light block-center">Список учетных записей--%>
            <%--                </button>--%>
            <%--            </a>--%>

            <%--            <a href="?command=show_create_account_page">--%>
            <%--                <button type="button" class="btn  btn-light block-center">Список конкурсов--%>
            <%--                </button>--%>
            <%--            </a>--%>

        </div>
    </div>

    <div class="col-3 user-account-element">

        <b></span></spn><c:out value="${sessionScope.role}"/></b>
        <br/>
        <c:out value="${sessionScope.login}"/>
        <br/>
        <a href="/?command=logout">
            <button type="button" class="btn  btn-dark block-center">logout
            </button>
        </a>
    </div>

</div>