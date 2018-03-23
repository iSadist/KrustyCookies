package ViewControllers;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Models.Database;

public class OrderDeliveryView extends JTabbedPane {

	private DefaultListModel<String> ingredientListModel;
	private DefaultListModel<String> recipeListModel;
	private Database db;

	public OrderDeliveryView(Database db) {
		this.db = db;

		JPanel orderPane = new OrderView(db);
		JPanel recipePane = new DeliveryView(db);

		addTab("Order", orderPane);
		addTab("Delivery", new ImageIcon() , recipePane, "Test");

		addChangeListener(new OrderChangeHandler());
		getSelectedComponent();
	}
}

class OrderChangeHandler implements ChangeListener {
	public void stateChanged(ChangeEvent e) {
		OrderDeliveryView rv = (OrderDeliveryView) e.getSource();
		BasicSubview rsv = (BasicSubview) rv.getSelectedComponent();
		rsv.switchedState();
	}
}
