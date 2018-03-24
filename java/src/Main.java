import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Models.Database;
import ViewControllers.RawView;
import ViewControllers.ProductionView;
import ViewControllers.OrderDeliveryView;

public class Main {
	public static void main(String[] args) {
		Database db = new Database();
		db.openConnection("database.db");

		JFrame raw = new JFrame("Program 3: raw materials and recipies");
		JFrame production = new JFrame("Program 1: production, blocking & searching");
		JFrame order = new JFrame("Program 2: orders & deliveries");

		raw.add(new RawView(db));
		production.add(new ProductionView(db));
		order.add(new OrderDeliveryView(db));

		raw.setSize(1250, 350);
		production.setSize(600, 700);
		order.setSize(900, 500);

		raw.setVisible(true);
		production.setVisible(true);
		order.setVisible(true);
	}

}
