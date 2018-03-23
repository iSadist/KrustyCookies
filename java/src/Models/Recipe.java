package Models;

public class Recipe {

	private String name;
	private String text;
	private boolean inProduction;

	public Recipe(String name, String text, boolean inProduction) {
		this.name = name;
		this. text = text;
		this.inProduction = inProduction;
	}

	public String getName() {
		return name;
	}

	public String getText() {
		return text;
	}

	public boolean isInProduction() {
		return inProduction;
	}

}
