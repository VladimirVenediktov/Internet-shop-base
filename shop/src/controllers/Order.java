package controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.BaseModel;

@WebServlet("/Order")
public class Order extends HttpServlet {
	private static final long serialVersionUID = 1L;
   
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		Cookie arr_cookie[] = request.getCookies();
		int id_basket = 0;
		
		for (int i=0; i<arr_cookie.length; i++) {
			if (arr_cookie[i].getName().equals("idBasket")) {
				id_basket = Integer.parseInt(arr_cookie[i].getValue());
			}
		}
		
		//создаем объект "заказ" (статус - новый)
		try {
			BaseModel.createOrder(BaseModel.findIdUser(Authorization.getFIO(), Authorization.getPhone()),id_basket, "новый");
		} catch (ClassNotFoundException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (SQLException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		
		//готовим данные для передачи в order.jsp
		try {
			request.setAttribute("infoOrder", BaseModel.getInfoOrder(id_basket));//общая информация о заказе
			//request.setAttribute("OrderGoods", BaseModel.getOrderGoods(id_basket));//список названия товаров
			request.setAttribute("OrderGoods", BaseModel.getDataFromBasket(Integer.toString(id_basket)));//информация о товарах (список и кол-во)
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		request.getRequestDispatcher("WEB-INF/views/order.jsp").forward(request,response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		doGet(request, response);
	}

}
