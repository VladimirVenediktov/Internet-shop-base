<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>Каталог товаров</title>
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
			function addBasket(id){
				var dataStr = "id_good="+id;//строка запроса	
				//alert(id);
				$.ajax(
					{
						type:"POST",
						url:"catalog",//либо можно указать сервлет как MyCatalog
						data:dataStr,
						success:function(msg){
							$("h1").html(msg);						
						}
					}		
				);
			}
	</script>
	
	<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%> <!--подкл. библиотеку jstl-->
	</head>
	<body>
		<h1></h1><!--здесь размещается сообщение msg из ajax-->
		<table>
			<caption style="font-size:25px">Электронный каталог товаров</caption>
			<tr>
				<th>id товара</th>
				<th>наименование</th>
				<th>стоимость за шт. (руб)</th>
				<th></th>
			</tr>
			<c:forEach items="${goods}" varStatus="num" var="good">
				<tr>
					<td>${num.count}</td>
					<td>${good.title}</td>
					<td>${good.price}</td>
					<td><a onclick="addBasket(${good.id})" href='#'>Добавить в корзину</a></td>
				</tr>
			</c:forEach>
		</table>
		<a href="Basket" title="В корзину"><img width="100 px" src="basket.png"></a>
	</body>
</html>