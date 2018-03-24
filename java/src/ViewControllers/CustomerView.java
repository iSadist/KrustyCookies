package ViewControllers;

import java.awt.BorderLayout;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import java.util.ArrayList;

import Models.Database;

public class CustomerView extends JPanel implements BasicSubview {

	private Database db;
	private DefaultListModel<String> deliverys;

	private DefaultListModel<String> customerListModel;
	private JList<String> customerList;

	public CustomerView(Database db) {
		this.db = db;

		setLayout(new BorderLayout());

		customerListModel = new DefaultListModel<String>();
		customerList = new JList<String>(customerListModel);
		JScrollPane customerScrollPane = new JScrollPane(customerList);

		add(customerScrollPane, BorderLayout.CENTER);
		add(new JButton("Click me"), BorderLayout.SOUTH);
	}

	public void updateCustomerList() {
		customerListModel.clear();
		ArrayList<String> customers = this.db.getCustomers();
		for(String s : customers) {
			customerListModel.addElement(s);
		}
	}

	@Override
	public void switchedState() {
		this.updateCustomerList();
	}
}
