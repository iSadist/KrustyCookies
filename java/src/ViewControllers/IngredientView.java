package ViewControllers;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Models.Database;
import Models.Ingredient;

public class IngredientView extends JPanel implements BasicSubview {

	private static final long serialVersionUID = 1L;

	private Database db;
	private DefaultListModel<String> ingredientListModel;
	private String messurement = "g";
	private JList<String> ingredientList;
	private JTextField[] ingredientFields;
	private JTextField updateAmountField;
	private JLabel messurementLabel1;
	private JLabel messurementLabel2;

	public IngredientView(Database db) {
		this.db = db;

		setLayout(new BorderLayout());
		ingredientListModel = new DefaultListModel<String>();
		ingredientList = new JList<String>(ingredientListModel);
		ingredientList.addListSelectionListener(new IngredientSelectionHandler());
		JScrollPane ingredientScrollPane = new JScrollPane(ingredientList);
		add(ingredientScrollPane, BorderLayout.LINE_START);

		JPanel ingredientDetail = new JPanel();
		ingredientDetail.setLayout(new GridLayout(4, 3));

		ingredientFields = new JTextField[3];
		ingredientFields[0] = new JTextField("0");
		ingredientFields[1] = new JTextField("1970-01-01");
		ingredientFields[2] = new JTextField("100");

		ingredientFields[0].setEnabled(false);
		ingredientFields[1].setEnabled(false);
		ingredientFields[2].setEnabled(false);

		ingredientDetail.add(new JLabel("Amount in store"));
		ingredientDetail.add(ingredientFields[0]);

		messurementLabel1 = new JLabel(messurement);
		ingredientDetail.add(messurementLabel1);

		ingredientDetail.add(new JLabel("Latest updated"));
		ingredientDetail.add(ingredientFields[1]);
		ingredientDetail.add(new JLabel("to this ingridient"));

		ingredientDetail.add(new JLabel("Latest update amount"));
		ingredientDetail.add(ingredientFields[2]);

		messurementLabel2 = new JLabel(messurement);
		ingredientDetail.add(messurementLabel2);

		JLabel updateLabel = new JLabel("Update this ingredient by");
		ingredientDetail.add(updateLabel);

		updateAmountField = new JTextField();
		ingredientDetail.add(updateAmountField);

		JButton updateIngredientButton = new JButton("Update");
		updateIngredientButton.addActionListener(new UpdateButtonActionListener());
		ingredientDetail.add(updateIngredientButton);

		add(ingredientDetail, BorderLayout.CENTER);

		JButton addNewIngredient = new JButton("Add new ingredient");
		addNewIngredient.addActionListener(new newIngredientActionListener(this));
		add(addNewIngredient, BorderLayout.SOUTH);

		getIngredients();
	}

	public void getIngredients() {
		ingredientListModel.removeAllElements();
		for(Ingredient i: db.getIngredients()) {
			ingredientListModel.addElement(i.getName());
		}
	}

	public void setIngredientDetail(String ingredient) {
		if(ingredient != null) {
			Ingredient i = db.getIngredient(ingredient);
			messurement = i.getMessure();
			ingredientFields[0].setText(String.valueOf(i.getAmount()));
			ingredientFields[1].setText(i.getLastDate());
			ingredientFields[2].setText(String.valueOf(i.getLastAmount()));
			messurementLabel1.setText(messurement);
			messurementLabel2.setText(messurement);
		}

		//If ingredient already exists show a message
	}

	public void addIngredient() {
		String name = JOptionPane.showInputDialog("Ingredient name:");

		Object[] messurements = db.getMessurements();
		String messureType = (String)JOptionPane.showInputDialog(
		                    this,
		                    "Select the messurement type for the ingredient",
		                    "Messurement type",
		                    JOptionPane.PLAIN_MESSAGE,
		                    null,
		                    messurements,
		                    "g");
		db.addIngredient(new Ingredient(name, 0, messureType, null, 0));
		getIngredients();
	}

	public void updateIngredient(String ingredient, int amount) {
		db.updateIngredient(ingredient, amount);
		setIngredientDetail(ingredient);
	}

	@Override
	public void switchedState() {
		getIngredients();
	}

	class newIngredientActionListener implements ActionListener {

		private IngredientView iv;

		public newIngredientActionListener(IngredientView iv) {
			this.iv = iv;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			iv.addIngredient();
		}
	}

	class IngredientSelectionHandler implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			setIngredientDetail(ingredientList.getSelectedValue());
		}

	}

	class UpdateButtonActionListener implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			int value = Integer.valueOf(updateAmountField.getText());
			updateIngredient(ingredientList.getSelectedValue(),value);
			updateAmountField.setText("0");
		}

	}
}
