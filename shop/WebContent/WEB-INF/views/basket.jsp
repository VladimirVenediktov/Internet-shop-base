<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>Корзина</title>
		
		<style type="text/css">
		TABLE {
			text-align: center;
			width: 50%;
			height: 30%;
			background: #99ff99; /* Цвет фона таблицы */
			border: 2px solid #000; /* Рамка вокруг таблицы */
			border-collapse: collapse; /* Без двойных линий */
		}
		
		TD, TH {
			padding: 5px; /* Поля вокруг текста */
			border: 1px solid #000; /* Рамка вокруг ячеек */
		}
		</style>
		
		<script src="http://code.jquery.com/jquery-1.8.3.js"> </script><!-- подкл. jquery -->
		<script>
			function deleteFromBasket(id){
				var dataStr = "id_good="+id;//строка запроса	
				$.ajax(
					{
						type:"POST",
						url:"Basket",
						data:dataStr,
						success:function(msg){
							$("h2").html(msg);
							window.location.href = "Basket";//после удаления обращаемся к сервлету для обновления таблицы
						}
					}		
				);
			}
		</script>
		<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%><!--подкл. библиотеку jstl-->
	</head>
	<body>
		<h1>Корзина товаров</h1>
		<table>
			<tr>
				<th>№</th>
				<th>наименование</th>
				<th>стоимость за шт. (руб)</th>
				<th>кол-во (шт)</th>
				<th>удалить</th>
			</tr>
			<c:forEach items="${basket}" varStatus="num" var="b">
				<tr>
					<td>${num.count}</td>
					<td>${b.title}</td>
					<td>${b.price}</td>
					<td>${b.countGoods}</td>
					<td><button onclick="deleteFromBasket(${b.id_good})">удалить</button></td>
				</tr>
			</c:forEach>
		</table>
		
		<h2></h2>

		<a href="Authorization" style="font-size:25px">Оформить заказ</a>
	</body>
</html>