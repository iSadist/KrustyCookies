package ViewControllers;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.JToggleButton;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;

import Models.Database;
import Models.Pallet;
import Models.Recipe;

public class BlockedPalletsView extends JPanel implements BasicSubview {

	private static final long serialVersionUID = 1L;

	private Database db;
	private DefaultListModel<String> palletListModel;
	private JList<String> palletList;
	private JComboBox<String> productName;
	private JToggleButton useSpecificProduct;

	public BlockedPalletsView(Database db) {
		this.db = db;

		setLayout(new BorderLayout());
		JPanel listPanel = new JPanel();
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.PAGE_AXIS));

		JPanel inputFields = new JPanel();
		inputFields.setLayout(new BoxLayout(inputFields, BoxLayout.X_AXIS));

		Vector<String> products = new Vector<>();
		for (Recipe r : db.getRecipes()) {
			products.add(r.getName());
		}

		productName = new JComboBox<String>(products);
		productName.setEnabled(false);
		productName.addActionListener(new CheckboxHandler());
		useSpecificProduct = new JToggleButton("Product filter");
		useSpecificProduct.addActionListener(new CheckboxHandler());

		Dimension maximumSize = new Dimension(1000, 100);
		productName.setMaximumSize(maximumSize);
		inputFields.add(new JLabel("Product name: "));
		inputFields.add(productName);
		inputFields.add(useSpecificProduct);

		listPanel.add(inputFields);

		palletListModel = new DefaultListModel<String>();
		palletList = new JList<String>(palletListModel);
		palletList.addMouseListener(new UnblockSelectionHandler());
		JScrollPane palletScrollPane = new JScrollPane(palletList);
		listPanel.add(palletScrollPane);

		add(listPanel, BorderLayout.CENTER);
		fillList();
	}

	private void fillList() {
		palletListModel.clear();
		ArrayList<Pallet> pallets = null;
		pallets = useSpecificProduct.isSelected() ? db.getBlockedPallets(productName.getSelectedItem().toString()) : db.getBlockedPallets();

		for(Pallet p : pallets) {
			String element = p.id + " Product: " + p.productName + " Location: " + p.location + " Created: " + p.inTime + " Distributed: " + p.outTime;
			palletListModel.addElement(element);
		}
	}

	@Override
	public void switchedState() {
		fillList();
	}

	class UnblockSelectionHandler implements MouseListener {

		@Override
		public void mouseClicked(MouseEvent e) {
			int a = JOptionPane.showConfirmDialog(null,
					"Unblock this product?", "Unblock this product?", JOptionPane.YES_NO_OPTION);
			if (a == 0) {
				int endSub = palletList.getSelectedValue().indexOf("Product") - 1;
				db.removeFromBlocked(Integer.valueOf(palletList.getSelectedValue().substring(0, endSub)));
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

}
