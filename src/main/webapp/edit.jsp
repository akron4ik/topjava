<%--
  Created by IntelliJ IDEA.
  User: aron4ik
  Date: 2019-06-09
  Time: 20:18
  To change this template use File | Settings | File Templates.
--%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
<c:if test="${empty meal.id}">
    <title>Add new meal</title>
</c:if>
<c:if test="${!empty meal.id}">
    <title>Edit meal</title>
</c:if>

</head>


<body>
<h3><a href="meals">Meals</a></h3>
<hr>


<form method="POST" name="addMeal">


    <input type="hidden" readonly="readonly" name="id"
                value="<c:out value="${meal.id}" />"/>
    <p><label>Описание</label>
        <input type="text" name="description"
               value="<c:out value="${meal.description}"/>"/></p>

    <p><label>Калории</label>
        <input type="text" name="calories"
               value="<c:out value="${meal.calories}"/>"/></p>

    <p><label>Дата и время</label>
        <input type="datetime" name="date" placeholder="${meal.dateTime.format(format)}"
               value="${meal.dateTime.format(format)}"/></p>



    <c:if test="${empty meal.id}">
        <input type="submit" value="Добавить">
    </c:if>
    <c:if test="${!empty meal.id}">
        <input type="submit" value="Редактировать">
    </c:if>

</form>
</body>
</html>
