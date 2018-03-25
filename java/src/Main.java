import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
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

		JFrame gui = new JFrame("Krusty Cookies");
		JPanel guiPanel = new JPanel();
		guiPanel.setLayout(new BorderLayout());

		guiPanel.add(new ProductionView(db), BorderLayout.WEST);
		guiPanel.add(new OrderDeliveryView(db), BorderLayout.EAST);
		guiPanel.add(new RawView(db), BorderLayout.SOUTH);

		gui.add(guiPanel);
		gui.setSize(2000, 1000);
		gui.setVisible(true);
		guiPanel.setVisible(true);
	}

}
