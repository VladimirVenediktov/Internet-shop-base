package models;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.sql.PreparedStatement;


public class BaseModel {
   public static ArrayList<Goods> goods = new ArrayList<Goods>();//создадим список товаров (будем брать их из БД)
   public static ArrayList<BasketInit> basket = new ArrayList<BasketInit>();//создадим список товаров, имеющихся в корзине покупателя
   
   //соединение с БД
   public static Connection getConnection() throws ClassNotFoundException, SQLException {
	   Class.forName("com.mysql.jdbc.Driver");
	   String url = "jdbc:mysql://localhost/shoponline";
	   return DriverManager.getConnection(url,"root","");
   }
   
   //метод для наполнения коллекции товарами из БД
   public static ArrayList<Goods> getGoods() throws ClassNotFoundException, SQLException{	   
	   Connection c = getConnection();
	   PreparedStatement ps = c.prepareStatement("select * from goods");
	   ResultSet rs = ps.executeQuery();//запускаем запрос
	   
	   goods.clear();
	   while(rs.next()) {
		   int id = rs.getInt("id");
		   String title = rs.getString("title");
		   int price = rs.getInt("price");
		   goods.add(new Goods(id,title,price));//добавляем товары в список 
	   }	   
	   return goods;
   }
   
   //метод для добавления товара в корзину
   public static boolean addToBasket(int id_good, String id_basket) throws ClassNotFoundException, SQLException {
	   Connection c = getConnection();
	   PreparedStatement ps = c.prepareStatement("insert into basket(id_good, id_basket) values("+id_good+", '"+id_basket+"')");
	   if(ps.executeUpdate()>0) {
		   return true;
	   }
	   return false;	   
   }
   
   //метод для получения названия товара по его id_good
   public static String getGoodNameById(int id_good) throws ClassNotFoundException, SQLException {
	   String name = "";
	   Connection c = getConnection();
	   PreparedStatement ps = c.prepareStatement("SELECT title FROM GOODS WHERE id = '"+id_good+"'");
	   ResultSet rs = ps.executeQuery();
	   if (rs.next()) {
		   name = rs.getString(1);
	   }   
	   return name;
   }
   
   //метод для наполнения коллекции товарами из корзины (уникальные товары)
   public static ArrayList<BasketInit> getDataFromBasket(String id_basket) throws ClassNotFoundException, SQLException {
	   Connection c = getConnection();
	   //PreparedStatement ps = c.prepareStatement("select goods.id as id_good, goods.title as title,goods.price as price from goods inner join basket on goods.id=basket.id_good where basket.id_basket = '"+id_basket+"'");//Отложенные вызовы - более защищенный запрос
	   PreparedStatement ps = c.prepareStatement("select distinct goods.id as id_good, goods.title as title,goods.price as price from goods inner join basket on goods.id=basket.id_good where basket.id_basket = '"+id_basket+"'");
	   ResultSet rs = ps.executeQuery();//запускаем запрос
	   basket.clear();
	   while(rs.next()) {
		   int id_good = rs.getInt("id_good");
		   String title = rs.getString("title");
		   int price = rs.getInt("price");
		   int countGoods = countGoods(rs.getInt("id_good"), id_basket);
		   basket.add(new BasketInit(id_good, title,price, countGoods));
	   }
	return basket;  
   }
   
   //метод для поиска максимального id_user в БД
   public static int findMaxIdUser() throws ClassNotFoundException, SQLException {
	   Connection c = getConnection();
	   PreparedStatement ps = c.prepareStatement("SELECT MAX(id_user) FROM users");//SQL-запрос для нахождения макс. id
	   ResultSet rs = ps.executeQuery();//запускаем запрос
	   int max_id = 0;
	   if (rs.next()) {
		   max_id = rs.getInt(1);
	   }   
	   return max_id;
   }
   
   /*
   //поиск клиента по ФИО и номеру телефона в БД
   public static boolean findUser(String fio, String phone) throws ClassNotFoundException, SQLException {
	Connection c = getConnection();
	PreparedStatement ps = c.prepareStatement("SELECT id_user FROM USERS WHERE fio = '"+fio+"' and phone = '"+phone+"'");
	ResultSet rs = ps.executeQuery();
	if (rs.next()) {
		return true;
	}
	else {
	return false;
	}
   }
   */
   
   //поиск id_user по ФИО и номеру телефона в БД
   public static int findIdUser(String fio, String phone) throws ClassNotFoundException, SQLException {
	   Connection c = getConnection();
	   PreparedStatement ps = c.prepareStatement("SELECT id_user FROM USERS WHERE fio = '"+fio+"' and phone = '"+phone+"'");
	   ResultSet rs = ps.executeQuery();
	   if (rs.next()) {
		   return rs.getInt(1);
	   }
	   else {
		   return -1;
	   }
   }
   
   //авторизация/регистрация - добавляем клиента в БД
   public static boolean addUser(String fio, String phone) throws ClassNotFoundException, SQLException {
	   int id = findMaxIdUser()+1;
	   Connection c = getConnection();
	   PreparedStatement ps1 = c.prepareStatement("INSERT INTO `users`(`id_user`, `fio`, `phone`) VALUES ('"+id+"', '"+fio+"', '"+phone+"')");//запрос для создания нового клиента в БД с id = макс.+1
	   if(ps1.executeUpdate()>0) {
		   return true;
	   }
	   else {
		   return false;
	   }
   }
 
   	// метод для поиска максимального id_basket в общей корзине (в БД)
	public static int findMaxIdBasket() throws ClassNotFoundException, SQLException {
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement("SELECT MAX(id_basket) FROM basket");// SQL-запрос для нахождения макс. id_basket
		ResultSet rs = ps.executeQuery();// запускаем запрос
		int max_id = 0;
		if (rs.next()) {
			max_id = rs.getInt(1);
		}
		return max_id;
	}
   
	//метод для компоновки заказа отдельного покупателя
	public static boolean createOrder(int id_user, int id_basket, String status) throws ClassNotFoundException, SQLException {
	   Connection c = getConnection();
	   PreparedStatement ps1 = c.prepareStatement("INSERT INTO `orders`(`id_user`, `id_basket`, `status`) VALUES ('"+id_user+"', '"+id_basket+"', '"+status+"')");
	   if(ps1.executeUpdate()>0) {
		   return true;
	   }
	   return false;
	}
   
   /*
	// метод для поиска id_user из табл. orders по ФИО
	public static int findIdUser(String fio) throws ClassNotFoundException, SQLException {

		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement("SELECT id_user FROM users WHERE fio='" + fio + "'");// SQL-запрос для нахождения id_user
		ResultSet rs = ps.executeQuery();// запускаем запрос
		int id_user = 0;
		if (rs.next()) {
			id_user = rs.getInt(1);
		}
		return id_user;
	}
	*/
	
	// метод для создания объекта класса заказ (для последующего отображения инф. о заказе)
	public static Order getInfoOrder(int id_basket) throws ClassNotFoundException, SQLException {
		Connection c = getConnection();
		PreparedStatement ps1 = c.prepareStatement(
				"select orders.id as 'order number', orders.status, users.fio, users.phone from orders inner join users on orders.id_user=users.id_user where orders.id_basket = '"
						+ id_basket + "'");
		ResultSet rs = ps1.executeQuery();// запускаем запрос
		int id = 0;
		String status = "";
		String fio = "";
		String phone = "";
		ArrayList<String> goodsList = null;//список товаров данного заказа

		while (rs.next()) {
			id = rs.getInt("order number");
			status = rs.getString("status");
			fio = rs.getString("fio");
			phone = rs.getString("phone");
			goodsList = getOrderGoods(id_basket);
		}
		Order orderForJSP = new Order(id, goodsList, fio, phone, status);
		return orderForJSP;

	}
	   
	//метод для создания списка названия товаров заказа по id_basket
	public static ArrayList<String> getOrderGoods(int id_basket) throws ClassNotFoundException, SQLException{
		ArrayList<String> goodsList = new ArrayList<String>();
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement("SELECT id_good FROM basket WHERE id_basket = '"+id_basket+"'");
		ResultSet rs = ps.executeQuery();
		while (rs.next()) {
			String s = getGoodNameById(rs.getInt(1));//поиск названия товара по id_good
			goodsList.add(s);
		}   	   
		return goodsList;
	}
	   
	//метод для удаления записей с данным id_basket из табл. БД basket
	public static int clearBasket(String id_basket) throws ClassNotFoundException, SQLException {
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement("DELETE from basket WHERE id_basket = '"+id_basket+"'");
		int rs = ps.executeUpdate();
		return rs;//возвращает кол-во удаленных записей
	}
	   
	//метод, меняющий статус заказа на "В работе"
	public static boolean changeOrderStatus(String newStatus, int id_basket) throws ClassNotFoundException, SQLException {   
		Connection c = getConnection();
		PreparedStatement ps1 = c.prepareStatement("UPDATE orders set status = '"+newStatus+"' WHERE id_basket = '"+id_basket+"'");
		if(ps1.executeUpdate()>0) {
			return true;
		}
		else {
			return false;
		}  
	}
   
	//метод для подсчета кол-ва штук данного товара (выбранной корзины)
	public static int countGoods(int id_good, String id_basket) throws SQLException, ClassNotFoundException {
		Connection c = getConnection();
		PreparedStatement ps = c.prepareStatement("SELECT COUNT(*) from basket WHERE id_good = '"+id_good+"' and id_basket = '"+id_basket+"'");
		ResultSet rs = ps.executeQuery();
		int count = 1;
		while (rs.next()) {
			count = rs.getInt(1);
		}
		return count;
	}
	   
	//метод для удаления товара из корзины
	public static boolean deleteFromBasket(int id_good) throws ClassNotFoundException, SQLException {   
		Connection c = getConnection();
		PreparedStatement ps1 = c.prepareStatement("DELETE from basket WHERE id_good = '"+id_good+"'");
		if(ps1.executeUpdate()>0) {
			return true;
		}
		else {
			return false;
		}
	}
	/*
	public static void main(String[] args) throws ClassNotFoundException, SQLException { 
		//тест
		//System.out.println(addToBasket(4, "777"));
		//System.out.println(findMaxIdUser());
		//System.out.println(findMaxIdBasket());
		//System.out.println(findIdUser("Sidorov"));
		//System.out.println(getInfoOrder(788).goods);
		//System.out.println(findUser("Vladimir", "424242"));
		//System.out.println(getOrderGoods(781)); 
		//System.out.println(clearBasket("789"));
		//System.out.println(Authorization.getTest());
		//changeOrderStatus("В РАБОТЕ", 800);
		//System.out.println(countGoods(4,5));
		//System.out.println(getDataFromBasket("800")); 
	}
	*/	 
}   


  
   

