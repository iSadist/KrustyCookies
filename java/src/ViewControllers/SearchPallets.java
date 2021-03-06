package ViewControllers;

import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Vector;
import java.awt.Toolkit;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JToggleButton;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.DocumentFilter;
import javax.swing.text.NumberFormatter;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.AbstractDocument;

import Models.Database;
import Models.Pallet;
import Models.Recipe;

public class SearchPallets extends JPanel implements BasicSubview {

	private static final long serialVersionUID = 1L;

	private Database db;
	private DefaultListModel<String> orderListModel;
	private JList<String> orderList;
	private JFormattedTextField startDate;
	private JFormattedTextField endDate;
	private JTextField palletId;
	private JComboBox<String> productName;
	private JComboBox<String> customerName;
	private JToggleButton useSpecificProduct;
	private JToggleButton useSpecificCustomer;
	private JLabel countLabel;

	public SearchPallets(Database db) {
		this.db = db;

		setLayout(new BorderLayout());
		JPanel listPanel = new JPanel();
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.PAGE_AXIS));

		orderListModel = new DefaultListModel<String>();
		orderList = new JList<String>(orderListModel);
		orderList.addMouseListener(new BlockSelectionHandler());
		JScrollPane recipeScrollPane = new JScrollPane(orderList);
		listPanel.add(recipeScrollPane);

		DateFormat format = new SimpleDateFormat("yyyy-MM-dd");

		startDate = new JFormattedTextField(format);
		endDate = new JFormattedTextField(format);

		GregorianCalendar c = new GregorianCalendar();
		c.add(Calendar.DATE, 1);
		endDate.setText(format.format(c.getTime()));
		c.add(Calendar.DATE, -8);
		startDate.setText(format.format(c.getTime()));

		JPanel dateSelector = new JPanel();
		dateSelector.setLayout(new BoxLayout(dateSelector, BoxLayout.X_AXIS));
		dateSelector.add(new JLabel("Search by date: "));
		dateSelector.add(startDate);
		dateSelector.add(new JLabel(" to "));
		dateSelector.add(endDate);

		JPanel palletSelector = new JPanel();
		palletSelector.setLayout(new BoxLayout(palletSelector, BoxLayout.X_AXIS));
		palletSelector.add(new JLabel("Search by pallet id: "));
		palletId = new JTextField();
		((AbstractDocument)palletId.getDocument()).setDocumentFilter(new NumberFilter());

		palletId.setText("0");
		palletSelector.add(palletId);

		JPanel inputFields = new JPanel();
		inputFields.setLayout(new BoxLayout(inputFields, BoxLayout.Y_AXIS));

		setupProductFilter(inputFields);
		setupCustomerFilter(inputFields);

		JPanel twins = new JPanel(new BorderLayout());
		twins.add(dateSelector, BorderLayout.NORTH);
		twins.add(palletSelector, BorderLayout.SOUTH);
		add(twins, BorderLayout.NORTH);
		add(listPanel, BorderLayout.CENTER);
		add(inputFields, BorderLayout.SOUTH);

		startDate.getDocument().addDocumentListener(new DateChangedHandler());
		endDate.getDocument().addDocumentListener(new DateChangedHandler());
		palletId.getDocument().addDocumentListener(new DateChangedHandler());

	}

	public void fillList() {
		orderListModel.clear();
		if(!palletId.getText().equals("0") && !palletId.getText().equals("")){
			orderListModel.clear();
			Pallet p;
			p = db.getPalletFromId(palletId.getText());

			if (p == null) {
				return;
			}

			String element = p.id + " Product: " + p.productName + " Location: " + p.location + " Created: " + p.inTime + " Distributed: " + p.outTime;
			orderListModel.addElement(element);

		} else {
			ArrayList<Pallet> pallets = null;
			pallets = useSpecificProduct.isSelected() ? db.getPallets(startDate.getText(), endDate.getText(), productName.getSelectedItem().toString()) : db.getPallets(startDate.getText(), endDate.getText());

			if (useSpecificCustomer.isSelected()) {
				for (int i = 0; i < pallets.size(); i++) {
					Pallet current = pallets.get(i);
					String company = db.getCompanyName(current.orderId);
					String selectedCompany = customerName.getSelectedItem().toString();

					if (selectedCompany.equals("NONE")) {
						selectedCompany = "";
					}

					if (!company.equals(selectedCompany)) {
						pallets.remove(i);
						i--;
					}
				}
			}

			for(Pallet p : pallets) {
				String element = p.id + " Product: " + p.productName + " Location: " + p.location + " Created: " + p.inTime + " Distrubuted: " + p.outTime;
				orderListModel.addElement(element);
			}
		}
		countLabel.setText(String.valueOf(orderListModel.size()));
	}

	@Override
	public void switchedState() {
		fillList();
	}

	class BlockSelectionHandler implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			if(orderList.getModel().getSize() > 0){
				int a = JOptionPane.showConfirmDialog(null,
					"Block this product?", "Block this product?", JOptionPane.YES_NO_OPTION);
				if (a == 0) {
					int endSub = orderList.getSelectedValue().indexOf("Product") - 1;
					db.addToBlocked(Integer.valueOf(orderList.getSelectedValue().substring(0, endSub)));
				}
			}
			fillList();
		}

		@Override
		public void mousePressed(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// TODO Auto-generated method stub

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// TODO Auto-generated method stub

		}

	}

	class CheckboxHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			productName.setEnabled(useSpecificProduct.isSelected());
			fillList();
		}

	}

	class CustomerFilterHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			customerName.setEnabled(useSpecificCustomer.isSelected());
			fillList();
		}

	}

	class DateChangedHandler implements DocumentListener {

		@Override
		public void insertUpdate(DocumentEvent e) {
			fillList();
		}

		@Override
		public void removeUpdate(DocumentEvent e) {
			fillList();
		}

		@Override
		public void changedUpdate(DocumentEvent e) {
			fillList();
		}
	}

	class NumberFilter extends DocumentFilter
	{
	    @Override
	    public void insertString(DocumentFilter.FilterBypass fp
	            , int offset, String string, AttributeSet aset)
	                                throws BadLocationException
	    {
	        int len = string.length();
	        boolean isValidInteger = palletId.getText().length() >= 9 ? false : true;

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
	        boolean isValidInteger = palletId.getText().length() >= 9 ? false : true;

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

	// Private methods

	private void setupCustomerFilter(JPanel pane) {
		JPanel customerFilter = new JPanel();
		customerFilter.setLayout(new BoxLayout(customerFilter, BoxLayout.X_AXIS));

		useSpecificCustomer = new JToggleButton("Filter customer");
		useSpecificCustomer.addActionListener(new CustomerFilterHandler());

		Vector<String> customers = new Vector<>();
		for(String customer : db.getCustomers()) {
			String customerName = customer.substring(0, customer.indexOf("-") - 1);
			customers.add(customerName);
		}
		customerName = new JComboBox<String>(customers);
		customerName.setEnabled(false);
		customerName.addActionListener(new CustomerFilterHandler());

		customerFilter.add(customerName);
		customerFilter.add(useSpecificCustomer);

		pane.add(customerFilter);
	}

	private void setupProductFilter(JPanel pane) {
		JPanel productFilter = new JPanel();
		productFilter.setLayout(new BoxLayout(productFilter, BoxLayout.X_AXIS));

		Vector<String> products = new Vector<>();
		for (Recipe r : db.getRecipes()) {
			products.add(r.getName());
		}

		countLabel = new JLabel("0");
		productName = new JComboBox<String>(products);
		productName.addActionListener(new CheckboxHandler());
		productName.setEnabled(false);
		useSpecificProduct = new JToggleButton("Filter product");
		useSpecificProduct.addActionListener(new CheckboxHandler());

		productFilter.add(countLabel);
		productFilter.add(productName);
		productFilter.add(useSpecificProduct);

		pane.add(productFilter);
	}

}
