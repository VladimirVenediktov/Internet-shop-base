package controllers;

import java.io.IOException;
import java.sql.SQLException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import models.BaseModel;

@WebServlet("/Authorization")
public class Authorization extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	static String fio = "";
	static String phone = "";
 
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		request.getRequestDispatcher("WEB-INF/views/form.html").forward(request,response);
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType("text/html;charset=UTF-8");
				
		fio = request.getParameter("fio");
		phone = request.getParameter("phone");
		//после успешной авторизации появляется эта ссылка
		String goodAnswer = "<h2>"+fio+" c тел. "+phone+", Вы успешно авторизованы</h2><br><a href='Order'>Просмотреть информацию о заказе</a>";
		boolean addUser = false;
		try {
			if (BaseModel.findIdUser(fio, phone)==-1) {//если такого клиента нет в БД, то создаем
				if (BaseModel.addUser(fio, phone)) {//создание нового клиента в БД
					addUser = true;
				}				
			}
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		if (addUser) {
			response.getWriter().print(goodAnswer);
		}
		else {
			response.getWriter().print("Ошибка при регистрации нового клиента");
		}
		
	}
	//геттеры для ФИО и номера телефона
	public static String getFIO() {
		return fio;
	}
	public static String getPhone() {
		return phone;
	}
}

