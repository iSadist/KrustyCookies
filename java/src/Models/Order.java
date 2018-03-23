package Models;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Order { // TODO: Add a list containing recipes/products and amounts
                     //       and replace it with the current 'product'

	private String companyName;
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

	public String getCompany() {
		return companyName;
	}

	public Date getExpectedDeliveryDate() {
		return expectedDelivery;
	}

}
