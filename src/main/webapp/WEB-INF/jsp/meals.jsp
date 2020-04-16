<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ taglib prefix="fn" uri="http://topjava.javawebinar.ru/functions" %>
<html>
<jsp:include page="fragments/headTag.jsp"/>
<body>
<script type="text/javascript" src="resources/js/topjava.common.js" defer></script>
<script type="text/javascript" src="resources/js/topjava.meals.js" defer></script>
<jsp:include page="fragments/bodyHeader.jsp"/>
<div class="jumbotron">
    <div class="container">
        <section>
            <h3><spring:message code="meal.title"/></h3>

            <form id="dateTimeForm">
                <div class="form-row">
                    <dl class="col">
                        <dt><spring:message code="meal.startDate"/>:</dt>
                        <dd><input type="date" class="form-control" name="startDate" id="startDate"></dd>
                    </dl>
                    <dl class="col">
                        <dt><spring:message code="meal.endDate"/>:</dt>
                        <dd><input type="date" class="form-control" name="endDate" id="endDate"></dd>
                    </dl>
                </div>
                <div class="form-row">
                    <dl class="col">
                        <dt><spring:message code="meal.startTime"/>:</dt>
                        <dd><input type="time" class="form-control" name="startTime" id="startTime"></dd>
                    </dl>
                    <dl class="col">
                        <dt><spring:message code="meal.endTime"/>:</dt>
                        <dd><input type="time" class="form-control" name="endTime" id="endTime"></dd>
                    </dl>
                </div>
            </form>
            <div>
                <button class="btn btn-primary" onclick="updateMealTable()">
                    <spring:message code="meal.filter"/></button>
                <button class="btn btn-primary" onclick="clearForm()">
                    <spring:message code="common.cancel"/></button>
            </div>
            <hr>
            <button class="btn btn-primary" onclick="add()">
                <span class="fa fa-plus"></span>
                <spring:message code="common.add"/>
            </button>
            <hr>
            <table class="table table-striped" id="datatable">
                <thead>
                <tr>
                    <th><spring:message code="meal.dateTime"/></th>
                    <th><spring:message code="meal.description"/></th>
                    <th><spring:message code="meal.calories"/></th>
                    <th></th>
                    <th></th>
                </tr>
                </thead>
                <c:forEach items="${meals}" var="meal">
                    <jsp:useBean id="meal" scope="page" type="ru.javawebinar.topjava.to.MealTo"/>
                    <tr data-mealExceed="${meal.excess}">
                        <td>
                                <%--${meal.dateTime.toLocalDate()} ${meal.dateTime.toLocalTime()}--%>
                                <%--<%=TimeUtil.toString(meal.getDateTime())%>--%>
                                <%--${fn:replace(meal.dateTime, 'T', ' ')}--%>
                                ${fn:formatDateTime(meal.dateTime)}
                        </td>
                        <td>${meal.description}</td>
                        <td>${meal.calories}</td>
                        <td><a><span class="fa fa-pencil"></span></a></td>
                        <td><a class="delete" id="${meal.id}"><span class="fa fa-remove"></span></a></td>
                    </tr>
                </c:forEach>
            </table>
        </section>
    </div>
</div>
<div class="modal fade" tabindex="-1" id="editRow">
    <div class="modal-dialog">
        <div class="modal-content">
            <div class="modal-header">
                <h4 class="modal-title"><spring:message code="meal.add"/></h4>
                <button type="button" class="close" data-dismiss="modal">&times;</button>
            </div>
            <div class="modal-body">
                <form id="detailsForm">
                    <input type="hidden" id="id" name="id">

                    <div class="form-group">
                        <label for="datetime" class="col-form-label"><spring:message code="meal.dateTime"/></label>
                        <input type="datetime-local" class="form-control" id="datetime" name="datetime"
                               placeholder="<spring:message code="meal.dateTime"/>">
                    </div>

                    <div class="form-group">
                        <label for="description" class="col-form-label"><spring:message
                                code="meal.description"/></label>
                        <input type="text" class="form-control" id="description" name="description"
                               placeholder="<spring:message code="meal.description"/>">
                    </div>

                    <div class="form-group">
                        <label for="calories" class="col-form-label"><spring:message code="meal.calories"/></label>
                        <input type="number" class="form-control" id="calories" name="calories"
                               placeholder="<spring:message code="meal.calories"/>">
                    </div>
                </form>
            </div>
            <div class="modal-footer">
                <button type="button" class="btn btn-secondary" data-dismiss="modal">
                    <span class="fa fa-close" aria-hidden="true"></span>
                    <spring:message code="common.cancel"/>
                </button>
                <button type="button" class="btn btn-primary" onclick="save()">
                    <span class="fa fa-check" aria-hidden="true"></span>
                    <spring:message code="common.save"/>
                </button>
            </div>
        </div>
    </div>
</div>
<jsp:include page="fragments/footer.jsp"/>

</body>
</html>

