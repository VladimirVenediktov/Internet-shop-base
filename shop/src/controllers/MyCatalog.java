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

@WebServlet({ "/MyCatalog", "/catalog" })
public class MyCatalog extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// проверим есть ли куки с name "idBasket"
		if (request.getCookies() != null) {
			Cookie arr_cookie[] = request.getCookies();
			for (int i = 0; i < arr_cookie.length; i++) {
				if (arr_cookie[i].getName().equals("idBasket")) {
					doPost(request, response);
				}
			}
		}

		else {
			// создадим куки с id_basket нового потенциального покупателя
			String id_basket = "";
			try {// находим макс. значение id_basket в тек. момент, создаем новое на 1 больше
				id_basket = Integer.toString(BaseModel.findMaxIdBasket() + 1);
			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			String name = "idBasket";
			Cookie cookie = new Cookie(name, id_basket);// создали куки
			//cookie.setMaxAge(60);//зададим время жизни куки
			response.setContentType("text/html;charset=UTF-8");
			response.addCookie(cookie);// сохранили куки

			doPost(request, response);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		if (request.getParameter("id_good") == null) {
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");

			try {// готовим данные о товарах для передачи в jsp-файл, где 1-ый параметр - имя ("goods") для обращения к коллекции в jsp
				request.setAttribute("goods", BaseModel.getGoods());
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (SQLException e) {
				e.printStackTrace();
			}

			request.getRequestDispatcher("WEB-INF/views/goods.jsp").forward(request, response);// перенаправляем ответ в jsp
		
		} else {//если выбрали товар
			int id_good = Integer.parseInt(request.getParameter("id_good"));
			String id_basket = "";

			// уже есть куки, берем id_basket из них
			Cookie arr_cookie[] = request.getCookies();
			for (int i = 0; i < arr_cookie.length; i++) {
				if (arr_cookie[i].getName().equals("idBasket")) {
					id_basket = arr_cookie[i].getValue();
				}
			}

			if (id_good > 0) {
				try {
					if (BaseModel.addToBasket(id_good, id_basket)) {
						request.setCharacterEncoding("UTF-8");
						response.setCharacterEncoding("UTF-8");
						response.getWriter().print("Товар " + BaseModel.getGoodNameById(id_good) + " успешно добавлен в корзину!");// ответ сервера, который принимаем в jsp (msg)
					}
				} catch (ClassNotFoundException | SQLException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
