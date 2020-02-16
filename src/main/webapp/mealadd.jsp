<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jstl/core_rt" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<html>
<head>
    <title>Meal</title>
</head>
<body>
<h3 align="center"><a href="index.html">Home</a></h3>
<br/>
<br/>
<div align="center">
    <form method="POST" action='meals' name="frmAddUser">
        <c:if test="${!empty meal}">
            <input type="text" readonly="readonly" hidden="hidden" name="id"
                   value="<c:out value="${meal.id}" />"/> <br/>
        </c:if>
        <fmt:parseDate value="${ meal.getDateTime() }" pattern="yyyy-MM-dd'T'HH:mm" var="parsedDateTime"
                       type="both"/>
        Date dd.MM.yyyy HH:mm: <input type="text" name="dateTime"
                                      value="<fmt:formatDate pattern="dd.MM.yyyy HH:mm" value="${parsedDateTime}" />"/>
        <br/>
        Description : <input type="text" name="description" value="<c:out value="${meal.description}" />"/> <br/>
        Calories : <input type="text" name="calories" value="<c:out value="${meal.calories}" />"/> <br/>
        <input type="submit" value="Submit"/>
    </form>
</div>
</body>
</html>