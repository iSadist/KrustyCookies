package Models;

import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Order {

	private String companyName;
	private int orderID;
	private ArrayList<Product> products;
	private Date expectedDelivery;

	public Order(String company, int id, Date date) {
		companyName = company;
		this.orderID = id;

		expectedDelivery = date;

		products = new ArrayList<Product>();
	}

	public Order(String company, int id) {
		companyName = company;
		this.orderID = id;

		GregorianCalendar c = new GregorianCalendar();
		c.add(Calendar.DATE, 7);
		expectedDelivery = c.getTime();

		products = new ArrayList<Product>();
	}

	public void addProduct(String name, int amount) {
		Product product = new Product(name, amount);
		products.add(product);
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	public int getID() {
		return orderID;
	}

	public String getCompany() {
		return companyName;
	}

	public Date getExpectedDeliveryDate() {
		return expectedDelivery;
	}

}
