package Models;

import java.util.Calendar;
import java.util.Date;
import java.util.ArrayList;
import java.util.GregorianCalendar;

public class Order {

	private String companyName;
	private ArrayList<Product> products;
	private Date expectedDelivery;

	public Order(String company, Date date) {
		companyName = company;

		expectedDelivery = date;
	}

	public Order(String company) {
		companyName = company;

		GregorianCalendar c = new GregorianCalendar();
		c.add(Calendar.DATE, 7);
		expectedDelivery = c.getTime();
	}

	public void addProduct(String name, int amount) {
		Product product = new Product(name, amount);
		products.add(product);
	}

	public ArrayList<Product> getProducts() {
		return products;
	}

	public String getCompany() {
		return companyName;
	}

	public Date getExpectedDeliveryDate() {
		return expectedDelivery;
	}

}
