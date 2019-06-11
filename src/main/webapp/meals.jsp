<%--
  Created by IntelliJ IDEA.
  User: aron4ik
  Date: 2019-06-07
  Time: 22:40
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<head>
    <title>Meals</title>
</head>
<body>
<h3><a href="index.html">Home</a></h3>
<hr>
<h2>Meals</h2>

<table>
    <thead>
    <tr>
        <th>Дата/Время</th>
        <th>Описание</th>
        <th>Калории</th>
    </tr>
    </thead>
    <c:forEach var="meal" items="${meals}">
        <tr style="${meal.excess ? "color:#ff0000" : "color: #32CD32"}">

            <td><c:out value="${meal.dateTime.format(format)}"/></td>
            <td><c:out value="${meal.description}"/></td>
            <td><c:out value="${meal.calories}"/></td>
            <td>
                <td><a href="meals?action=edit&id=<c:out value="${meal.id}"/>">Редактировать</a></td>
                <td><a href="meals?action=delete&id=<c:out value="${meal.id}"/>">Удалить</a></td>
        </tr>
    </c:forEach>
</table>
<p><a href="meals?action=add">Добавить Еду</a></p>

</body>
</html>
