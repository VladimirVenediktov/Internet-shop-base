package models;

public class Goods {// класс товары
	int id;
	String title;
	int price;

	public Goods(int id, String title, int price) {// конструктор класса товары
		this.id = id;
		this.title = title;
		this.price = price;

	}

	// геттеры
	public int getId() {
		return id;
	}

	public int getPrice() {
		return price;
	}

	public String getTitle() {
		return title;
	}

	// сеттеры
	public void setId(int id) {
		this.id = id;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	// переопределяем метод toString(), для приемлемого отображения объектов класса
	public String toString() {
		return "Goods{" + "id=" + id + ",title='" + title + "'" + ",price=" + price + "}";
	}
}
