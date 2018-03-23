package Models;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Order { // TODO: Add a list containing recipes/products and amounts
                     //       and replace it with the current 'product'

	private String companyName;
	private String product;
	private Date expectedDelivery;

	public Order(String company, String product, Date date) {
		companyName = company;
		this.product = product;

		expectedDelivery = date;
	}

	public Order(String company, String product) {
		companyName = company;
		this.product = product;

		GregorianCalendar c = new GregorianCalendar();
		c.add(Calendar.DATE, 7);
		expectedDelivery = c.getTime();
	}

	public String getCompany() {
		return companyName;
	}

	public String getProduct() {
		return product;
	}

	public Date getExpectedDeliveryDate() {
		return expectedDelivery;
	}

}
