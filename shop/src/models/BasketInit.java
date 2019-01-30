package models;

public class BasketInit {
	int id_good;
	String title;
	int price;
	int countGoods;

	public BasketInit(int id_good, String title, int price, int countGoods) {//конструктор класса BasketInit
		this.id_good = id_good;
		this.title = title;
		this.price = price;
		this.countGoods = countGoods;// кол-во штук данного товара
	}

	//геттеры
	public int getId_good() {
		return id_good;
	}

	public int getPrice() {
		return price;
	}

	public String getTitle() {
		return title;
	}

	public int getCountGoods() {
		return countGoods;
	}

	//сеттеры
	public void setPrice(int price) {
		this.price = price;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String toString() {
		return "Basket{" + "title='" + title + "'" + ",price=" + price + "}";

	}

}
