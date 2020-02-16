<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3 align="center"><a href="index.html">Home</a></h3>
<h2 align="center">Meals list</h2>
<br/>
<br/>
<c:if test="${!empty meals}">
    <table align="center" border="2px">
        <thead>
        <tr>
            <th>Дата/Время</th>
            <th>Описание</th>
            <th>Калории</th>
            <th colspan=2>Действие</th>
        </tr>
        </thead>
        <c:forEach items="${meals}" var="meal">
            <tr style="${meal.getExcess() ? "color: red" : "color: green"}">
                <td><fmt:parseDate value="${ meal.getDateTime() }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                                   type="both"/>
                    <fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${ parsedDateTime }"/></td>
                <td><c:out value="${meal.getDescription()}"/></td>
                <td><c:out value="${meal.getCalories()}"/></td>
                <td><a href="meals?action=edit&mealId=<c:out value="${meal.getId()}"/>">Редактировать</a></td>
                <td><a href="meals?action=delete&mealId=<c:out value="${meal.getId()}"/>">Удалить</a></td>
            </tr>
        </c:forEach>
    </table>
</c:if>
<div align="center"><a href="meals?action=add">Добавить запись</a></div>
</body>
</html>