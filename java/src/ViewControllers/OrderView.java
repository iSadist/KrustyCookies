package ViewControllers;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Iterator;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Models.Database;
import Models.Order;


public class OrderView extends JPanel implements BasicSubview { // TODO: Show orders with many products

	private static final long serialVersionUID = 1L;

	private Database db;
	private DefaultListModel<String> orderListModel;
	private JList<String> orderList;
	private JFormattedTextField startDate;
	private JFormattedTextField endDate;

	public OrderView(Database db) {
		this.db = db;

		setLayout(new BorderLayout());
		JPanel listPanel = new JPanel();
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.PAGE_AXIS));

		orderListModel = new DefaultListModel<String>();
		orderList = new JList<String>(orderListModel);
		JScrollPane recipeScrollPane = new JScrollPane(orderList);
		listPanel.add(recipeScrollPane);

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		startDate = new JFormattedTextField(format);
		endDate = new JFormattedTextField(format);

		startDate.getDocument().addDocumentListener(new DateChangedHandler());
		endDate.getDocument().addDocumentListener(new DateChangedHandler());

		GregorianCalendar c = new GregorianCalendar();
		endDate.setText(format.format(c.getTime()));
		c.add(Calendar.DATE, -7);
		startDate.setText(format.format(c.getTime()));

		JPanel dateSelector = new JPanel();
		dateSelector.setLayout(new BoxLayout(dateSelector, BoxLayout.X_AXIS));
		dateSelector.add(startDate);
		dateSelector.add(new JLabel(" to "));
		dateSelector.add(endDate);

		add(dateSelector, BorderLayout.NORTH);
		add(listPanel, BorderLayout.CENTER);

	}

	public void updateOrderList(String begin, String end) {
		System.out.println("Updating order list");
		orderListModel.clear();
		ArrayList<Order> orders = db.getOrders(begin, end);
		for (Order order : orders) {
			orderListModel.addElement(order.getCompany()); // TODO: Fix this as above
		}
	}

	@Override
	public void switchedState() {
		updateOrderList(startDate.getText(), endDate.getText());
	}

	class DateChangedHandler implements DocumentListener {

		@Override
		public void insertUpdate(DocumentEvent e) {
			action(e);
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			action(e);
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			action(e);
		}

		public void action(DocumentEvent e) {
			if(e.getLength() == 0) {
				System.out.println("warning");
			}
			updateOrderList(startDate.getText(), endDate.getText());
		}

	}

}
