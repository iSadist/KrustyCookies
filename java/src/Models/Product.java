package Models;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

public class Product {

	private String name;
  	private int amount;

	public Product(String name, int amount) {
		this.name = name;
    this.amount = amount;
	}

	public String getName() {
		return name;
	}

	public int getAmount() {
		return amount;
	}

}
