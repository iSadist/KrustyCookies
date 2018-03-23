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
import Models.Recipe;

public class PalletProductionView extends JPanel implements BasicSubview {

	private static final long serialVersionUID = 1L;
	private Database db;
	private JTextField palletNumber;
	private JComboBox<String> productName;
	private JButton scanInButton;

	public PalletProductionView(Database db) {
		this.db = db;


		SpringLayout layout = new SpringLayout();
		setLayout(layout);

		Vector<String> products = new Vector<>();
		for (Recipe r : db.getRecipes()) {
			products.add(r.getName());
		}

		JLabel palletNumberLabel = new JLabel("Pallet number:");
		palletNumber = new JTextField();

		((AbstractDocument)palletNumber.getDocument()).setDocumentFilter(
                new NumberFilter());

		JLabel productNameLabel = new JLabel("Product name:");
		productName = new JComboBox<String>(products);
		scanInButton = new JButton("Scan in");
		scanInButton.addActionListener(new ScannerHandler());

		layout.putConstraint(SpringLayout.NORTH, palletNumberLabel, 15, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.NORTH, productNameLabel, 20, SpringLayout.SOUTH, palletNumberLabel);
		layout.putConstraint(SpringLayout.NORTH, scanInButton, 20, SpringLayout.SOUTH, productNameLabel);
		layout.putConstraint(SpringLayout.WEST, scanInButton, 100, SpringLayout.WEST, this);
		layout.putConstraint(SpringLayout.EAST, scanInButton, -100, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.NORTH, palletNumber, 10, SpringLayout.NORTH, this);
		layout.putConstraint(SpringLayout.WEST, palletNumber, 10, SpringLayout.EAST, palletNumberLabel);
		layout.putConstraint(SpringLayout.EAST, palletNumber, -50, SpringLayout.EAST, this);
		layout.putConstraint(SpringLayout.WEST, productName, 10, SpringLayout.EAST, productNameLabel);
		layout.putConstraint(SpringLayout.NORTH, productName, 10, SpringLayout.SOUTH, palletNumber);
		layout.putConstraint(SpringLayout.EAST, productName, -50, SpringLayout.EAST, this);

		add(palletNumberLabel);
		add(palletNumber);
		add(productNameLabel);
		add(productName);
		add(scanInButton);

	}

	class ScannerHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			boolean success = db.addPallet(Integer.valueOf(palletNumber.getText()), productName.getSelectedItem().toString());
			if (!success) {
				palletNumber.setBackground(Color.red);
			} else {
				palletNumber.setBackground(Color.green);
			}
		}

	}

	@Override
	public void switchedState() {
		palletNumber.setText("");
		productName.setSelectedIndex(1);
	}

	class NumberFilter extends DocumentFilter
	{
	    @Override
	    public void insertString(DocumentFilter.FilterBypass fp
	            , int offset, String string, AttributeSet aset)
	                                throws BadLocationException
	    {
	        int len = string.length();
	        boolean isValidInteger = palletNumber.getText().length() >= 9 ? false : true;

	        for (int i = 0; i < len; i++)
	        {
	            if (!Character.isDigit(string.charAt(i)))
	            {
	                isValidInteger = false;
	                break;
	            }
	        }
	        if (isValidInteger)
	            super.insertString(fp, offset, string, aset);
	        else
	            Toolkit.getDefaultToolkit().beep();
	    }

	    @Override
	    public void replace(DocumentFilter.FilterBypass fp, int offset
	                    , int length, String string, AttributeSet aset)
	                                        throws BadLocationException
	    {
	        int len = string.length();
	        boolean isValidInteger = palletNumber.getText().length() >= 9 ? false : true;

	        for (int i = 0; i < len; i++)
	        {
	            if (!Character.isDigit(string.charAt(i)))
	            {
	                isValidInteger = false;
	                break;
	            }
	        }
	        if (len > 10) {
	        	isValidInteger = false;
	        }

	        if (isValidInteger)
	            super.replace(fp, offset, length, string, aset);
	        else
	            Toolkit.getDefaultToolkit().beep();
	    }
	}
}
