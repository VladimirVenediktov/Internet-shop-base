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

@WebServlet({ "/OrderReady", "/finish" })
public class OrderReady extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		Cookie arr_cookie[] = request.getCookies();
		int id_basket = 0;
		
		for (int i=0; i<arr_cookie.length; i++) {
			if (arr_cookie[i].getName().equals("idBasket")) {
				id_basket = Integer.parseInt(arr_cookie[i].getValue());
			}
		}
		try {
			BaseModel.changeOrderStatus("в работе", id_basket);//меняем статус заказа на "В работе"
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//очищаем корзину - удаляем записи с таким id_basket из табл. БД basket
				try {
					BaseModel.clearBasket(Integer.toString(id_basket));
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
		
		//очистим в cookie idBasket, чтобы при оформлении этим же клиентом нового заказа формировалась новая корзина
		/*Cookie cookie = new Cookie("idBasket","");// создали куки
		cookie.setMaxAge(0);//зададим время жизни куки = 0
		response.setContentType("text/html;charset=UTF-8");
		response.addCookie(cookie);// сохранили куки
		*/
		response.setContentType("text/html;charset=UTF-8");
			
		response.getWriter().println("Ваш заказ оформлен. Статус заказа - 'В работе'<br><a href='MyCatalog'>Вернуться к выбору товаров</a>");
	}

}
