package Models;

import java.sql.*;
import java.util.Date;
import java.util.ArrayList;

public class Database {

	static private int cookiesPerPallet = 5400;
	static private int cookiesPerRecipe = 100;

	private Connection conn;

	public Database() {
		conn = null;
	}

	public boolean openConnection(String filename) {
		try {
            Class.forName("org.sqlite.JDBC");
            System.out.println(Class.forName("org.sqlite.JDBC"));
            conn = DriverManager.getConnection("jdbc:sqlite:" + filename);
            System.out.println(conn);
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            return false;
        }
        return true;
	}

	public boolean isConnected() {
		return conn != null;
	}

	public void close() {
		try {
			conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public String findCustomer(String address) {
		Statement stmt = null;
		String query = "SELECT company_name FROM customers " +
					   "WHERE address = '" + address + "'";
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			if(rs.next()) {
				return rs.getString("company_name");
			} else {
				return "";
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return "";
		}
	}

	public ArrayList<Ingredient> getIngredients() {
		ArrayList<Ingredient> list = new ArrayList<Ingredient>();

		Statement stmt = null;
		String query = "SELECT i.name, i.type, sum(u.amount) as amount "
				+ "FROM ingredients AS i "
				+ "LEFT OUTER JOIN ingredient_updates AS u "
				+ "ON i.name = u.ingredient_name "
				+ "GROUP BY i.name";
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				String name = rs.getString("name");
				int amount = rs.getInt("amount");
				Ingredient ingredient = new Ingredient(name, amount, null, null, 0);
				list.add(ingredient);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	public Ingredient getIngredient(String name) {
		Ingredient i = null;
		String query = "SELECT i.name, i.type, sum(u.amount) AS amount, u.day, u.amount AS updated_amount  "
				+ "FROM ingredients AS i "
				+ "LEFT OUTER JOIN ingredient_updates AS u "
				+ "ON i.name = u.ingredient_name "
				+ "GROUP BY i.name "
				+ "HAVING i.name = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, name);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				i = new Ingredient(rs.getString("name"), rs.getInt("amount"), rs.getString("type"), rs.getString("day"), rs.getInt("updated_amount"));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	public void updateIngredient(String ingredient, int value) {
		String query = "INSERT INTO ingredient_updates "
				+ "VALUES (?, CURRENT_TIMESTAMP, ?)";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, ingredient);
			ps.setInt(2, value);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}


	public ArrayList<Recipe> getRecipes() {
		ArrayList<Recipe> list = new ArrayList<Recipe>();

		Statement stmt = null;
		String query = "SELECT * FROM recipes";
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				String name = rs.getString("product_name");
				String text = rs.getString("instructions");
				boolean inProduction = rs.getBoolean("in_production");
				Recipe recipe = new Recipe(name, text, inProduction);
				list.add(recipe);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	public boolean recipeInProduction(String recipe) {
		String query = "SELECT in_production FROM recipes "
				+ "WHERE product_name = ?";
		boolean inProduction = true;
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, recipe);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
				inProduction = rs.getBoolean("in_production");
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return inProduction;
	}

	public ArrayList<Ingredient> getIngredientsForRecipe(String recipe) {
		ArrayList<Ingredient> ingredients = new ArrayList<Ingredient>();
		String query = "SELECT * FROM contains "
				+ "WHERE product_name = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, recipe);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Ingredient i = new Ingredient(rs.getString("ingredient_name"), rs.getInt("amount"), rs.getString("type"), null, 0);
				ingredients.add(i);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return ingredients;
	}

	public void addIngredient(Ingredient i) {
		String query = "INSERT INTO ingredients VALUES (?,?)";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, i.getName());
			ps.setString(2, i.getMessure());
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void updateIngredientToRecipe(String ingredient, String recipe, int amount) {
		String query = "UPDATE contains "
				+ "SET amount = ? "
				+ "WHERE ingredient_name = ? "
				+ "AND product_name = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, amount);
			ps.setString(2, ingredient);
			ps.setString(3,recipe);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addIngredientToRecipe(String ingredient, String recipe, int amount) {
		String messure = getIngredient(ingredient).getMessure();
		String query = "INSERT INTO contains VALUES (?,?,?,?)";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, recipe);
			ps.setString(2, ingredient);
			ps.setInt(3, amount);
			ps.setString(4, messure);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void removeIngredientFromRecipe(String ingredient, String recipe) {
		String query = "DELETE FROM contains "
				+ "WHERE product_name = ? "
				+ "AND ingredient_name = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, recipe);
			ps.setString(2, ingredient);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void addRecipe(String name) {
		String query = "INSERT INTO recipes (product_name) VALUES (?)";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, name);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void setProductionState(boolean inProduction, String recipe) {
		String query = "UPDATE recipes "
				+ "SET in_production = ? "
				+ "WHERE product_name = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setBoolean(1, inProduction);
			ps.setString(2, recipe);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public Object[] getMessurements() {
		Object[] strings = {"g", "dl", "st", "kg", "l", "pound"};
		return strings;
	}

	public ArrayList<Order> getOrders(String begin, String end) { // TODO: This one has to be changed to get amount the right way with recipes_order
		ArrayList<Order> orders = new ArrayList<Order>();           // Also, each order needs to display the different products.
		String query = "SELECT * FROM orders "
				+ "WHERE delivery_date BETWEEN ? AND ?";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, begin);
			ps.setString(2, end);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				String company = rs.getString("company_name");
				String product = rs.getString("product");
				// int amount = rs.getInt("amount");
				orders.add(new Order(company, product)); // TODO: Add list with products
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return orders;
	}

	public ArrayList<Pallet> getBlockedPallets() {
		ArrayList<Pallet> list = new ArrayList<Pallet>();
		Statement stmt = null;
		String query = "SELECT * FROM pallets "
				+ "WHERE pallet_id IN "
				+ "(SELECT pallet_id FROM blocked)";
		try {
			stmt = conn.createStatement();
			ResultSet rs = stmt.executeQuery(query);
			while(rs.next()) {
				Pallet p = new Pallet();
				p.id = rs.getInt("pallet_id");
				p.productName = rs.getString("product_name");
				p.location = rs.getString("location");
				p.inTime = rs.getString("in_time");
				p.outTime = rs.getString("out_time");
				p.reciever = rs.getString("receiver");
				list.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	public ArrayList<Pallet> getBlockedPallets(String productName) {
		ArrayList<Pallet> list = new ArrayList<Pallet>();
		String query = "SELECT * FROM pallets "
				+ "WHERE pallet_id IN "
				+ "(SELECT pallet_id FROM blocked) "
				+ "AND product_name = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, productName);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Pallet p = new Pallet();
				p.id = rs.getInt("pallet_id");
				p.productName = rs.getString("product_name");
				p.location = rs.getString("location");
				p.inTime = rs.getString("in_time");
				p.outTime = rs.getString("out_time");
				p.reciever = rs.getString("receiver");
				list.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	public void addToBlocked(int id) {
		String query = "INSERT INTO blocked "
				+ "VALUES (?)";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public void removeFromBlocked(int id) {
		String query = "DELETE FROM blocked "
				+ "WHERE pallet_id = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			ps.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public ArrayList<Pallet> getPallets(String begin, String end) {
		ArrayList<Pallet> list = new ArrayList<Pallet>();
		String query = "SELECT * FROM pallets "
				+ "WHERE in_time BETWEEN ? AND ?";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, begin);
			ps.setString(2, end);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Pallet p = new Pallet();
				p.id = rs.getInt("pallet_id");
				p.productName = rs.getString("product_name");
				p.location = rs.getString("location");
				p.inTime = rs.getString("in_time");
				p.outTime = rs.getString("out_time");
				p.reciever = rs.getString("receiver");
				list.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	public ArrayList<Pallet> getPallets(String begin, String end, String name) {
		ArrayList<Pallet> list = new ArrayList<Pallet>();
		String query = "SELECT * FROM pallets "
				+ "WHERE in_time BETWEEN ? AND ? "
				+ "AND product_name = ?";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setString(1, begin);
			ps.setString(2, end);
			ps.setString(3, name);
			ResultSet rs = ps.executeQuery();
			while(rs.next()) {
				Pallet p = new Pallet();
				p.id = rs.getInt("pallet_id");
				p.productName = rs.getString("product_name");
				p.location = rs.getString("location");
				p.inTime = rs.getString("in_time");
				p.outTime = rs.getString("out_time");
				p.reciever = rs.getString("receiver");
				list.add(p);
			}
		} catch (SQLException e) {
			e.printStackTrace();
			return null;
		}
		return list;
	}

	public boolean addPallet(int id, String productName) {
//		Updating Raw Materials
		ArrayList<Ingredient> ingredients = getIngredientsForRecipe(productName);
		for (Ingredient ingredient : ingredients) {
			updateIngredient(ingredient.getName(), ingredient.getAmount() * cookiesPerPallet/cookiesPerRecipe);
		}

		//Inserting to pallets
		String query = "INSERT INTO pallets (pallet_id, product_name, receiver) "
				+ "VALUES (?,?, 'NONE')";
		try {
			PreparedStatement ps = conn.prepareStatement(query);
			ps.setInt(1, id);
			ps.setString(2, productName);
			int result = ps.executeUpdate();
			return result != 0;
		} catch (SQLException e) {
			e.printStackTrace();
			return false;
		}
	}
}
