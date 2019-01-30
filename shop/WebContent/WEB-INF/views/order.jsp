<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Информация о вашем заказе</title>
		<style type="text/css">
		   TABLE {
		    text-align: center;
		    width: 50%; 
		    height: 30%;
		    border: 1px solid #000; /* Рамка вокруг таблицы */
		    border-collapse: collapse;/* Без двойных линий */
		   }
		   TD, TH {
		    padding: 1px; /* Поля вокруг текста */
		    border: 1px solid #000; /* Рамка вокруг ячеек */
		   }
		  </style>
		<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
	</head>
	<body>
		<table bgcolor= "#99ff99">
			<caption>Информация о вашем заказе:</caption>
			<tr>
				<th>номер заказа</th><th>ФИО покупателя</th><th>контактный телефон</th><th>статус заказа</th>
			</tr>
			<tr>
				<td>
					<c:out value="${infoOrder.id}"/>
				</td>
				<td>
					<c:out value="${infoOrder.fio}"/>
				</td>
				<td>
					<c:out value="${infoOrder.phone}"/>
				</td>
				<td>
					<c:out value="${infoOrder.status}" />
				</td>
			</tr>
		</table>
		<br>
		<table bgcolor= "#ff5c33">
		<caption>Список товаров заказа:</caption>
		<tr>
				<th>#</th><th>наименование</th><th>кол-во (шт)</th>
			</tr>
			<c:forEach items="${OrderGoods}" varStatus="num" var="OrderGood">
				<tr>
					<td width="10%">${num.count}</td>
					<td>${OrderGood.title}</td>		
					<td>${OrderGood.countGoods}</td>				
				</tr>
			</c:forEach>	
		</table>
		
		<a href="OrderReady">Завершить оформление данного заказа</a>
		
	</body>
</html>