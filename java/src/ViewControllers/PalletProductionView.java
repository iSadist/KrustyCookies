package ViewControllers;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Vector;

import javax.swing.text.AbstractDocument;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.SpringLayout;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import javax.swing.text.NumberFormatter;

import Models.Database;
import Models.Response;
import Models.Recipe;

public class PalletProductionView extends JPanel implements BasicSubview {

	private static final long serialVersionUID = 1L;
	private Database db;
	//private JTextField palletNumber;
	private JComboBox<String> productName;
	private JButton scanInButton;
	private JLabel messageLabel;

	public PalletProductionView(Database db) {
		this.db = db;

		SpringLayout layout = new SpringLayout();
		setLayout(layout);

		Vector<String> products = new Vector<>();
		for (Recipe r : db.getRecipes()) {
			products.add(r.getName());
		}

		
		JLabel productNameLabel = new JLabel("Product name:");
		productName = new JComboBox<String>(products);
		scanInButton = new JButton("Scan in");
		scanInButton.addActionListener(new ScannerHandler());

		messageLabel = new JLabel("Test message");

		layout.putConstraint(SpringLayout.NORTH, productNameLabel, 15, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.NORTH, scanInButton, 20, SpringLayout.SOUTH, productNameLabel);
		layout.putConstraint(SpringLayout.WEST, scanInButton, 100, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, scanInButton, -100, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, messageLabel, 20, SpringLayout.SOUTH, scanInButton);
		layout.putConstraint(SpringLayout.WEST, messageLabel, 100, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, messageLabel, -100, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.WEST, productName, 10, SpringLayout.EAST, productNameLabel);
		layout.putConstraint(SpringLayout.NORTH, productName, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.EAST, productName, -50, SpringLayout.EAST, this);

		add(productNameLabel);
		add(productName);
		add(scanInButton);
		add(messageLabel);

	}

	class ScannerHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			Response response = db.addPallet(productName.getSelectedItem().toString());
			if (!response.success) {
				messageLabel.setText(response.message);
			} else {
				messageLabel.setText(response.message);
			}
		}

	}

	private void resetState() {
		productName.setSelectedIndex(1);
		messageLabel.setText("");
	}

	@Override
	public void switchedState() {
		resetState();
	}
}
