package Models;

public class Ingredient {

	private String name;
	private int amount;
	private String messure;

	private String lastUpdated;
	private int lastAmount;

	public Ingredient(String n, int a, String m, String lu, int la) {
		name = n;
		amount = a;
		messure = m;
		lastUpdated = lu;
		lastAmount = la;
	}

	public String getName() {
		return name;
	}

	public String getMessure() {
		return messure;
	}

	public int getAmount() {
		return amount;
	}

	public String getLastDate() {
		return lastUpdated;
	}

	public int getLastAmount() {
		return lastAmount;
	}

}
