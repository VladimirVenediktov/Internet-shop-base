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

@WebServlet("/Basket")
public class Basket extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");

			try {
				Cookie arr_cookie[] = request.getCookies();
				String id_basket = "";
				for (int i = 0; i < arr_cookie.length; i++) {
					if (arr_cookie[i].getName().equals("idBasket")) {
						id_basket = arr_cookie[i].getValue();
					}
				}
				request.setAttribute("basket", BaseModel.getDataFromBasket(id_basket));// готовим данные для передачи в jsp-файл

			} catch (ClassNotFoundException | SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			request.getRequestDispatcher("WEB-INF/views/basket.jsp").forward(request, response);//перенаправляем ответ в jsp
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		int id_good = Integer.parseInt(request.getParameter("id_good"));
		try {
			BaseModel.deleteFromBasket(id_good);
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
