package ViewControllers;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JPanel;

import Models.Database;

public class DeliveryView extends JPanel implements BasicSubview {

	private Database db;
	private DefaultListModel<String> deliverys;

	public DeliveryView(Database db) {
		this.db = db;

		setLayout(new BorderLayout());
		

		add(new JButton("Click me"));
	}

	@Override
	public void switchedState() {
		System.out.println("DeliveryView showing");

	}
}
