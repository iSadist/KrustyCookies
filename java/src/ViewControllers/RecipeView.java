package ViewControllers;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Vector;

import javax.swing.BoxLayout;
import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.NumberFormatter;

import Models.Database;
import Models.Ingredient;
import Models.Recipe;

public class RecipeView extends JPanel implements BasicSubview {

	private static final long serialVersionUID = 1L;

	private Database db;
	private DefaultListModel<String> recipeListModel;
	private JList<String> recipeList;
	private GridLayout detailsLayout;
	private JPanel detailsPane;
	private JScrollPane detailsScroll;
	private JPanel recipeDetails;
	private int detailsRows = 0;

	public RecipeView(Database db) {
		this.db = db;

		setLayout(new BorderLayout());
		JPanel listPanel = new JPanel();
		listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.PAGE_AXIS));

		JLabel recipesLabel = new JLabel("Recipes");
		listPanel.add(recipesLabel);
		recipeListModel = new DefaultListModel<String>();
		recipeList = new JList<String>(recipeListModel);
		recipeList.addListSelectionListener(new RecipeSelectionHandler());
		JScrollPane recipeScrollPane = new JScrollPane(recipeList);
		listPanel.add(recipeScrollPane);
		JButton newRecipeButton = new JButton("New Recipe");
		newRecipeButton.addActionListener(new newRecipeHandler());
		listPanel.add(newRecipeButton);
		add(listPanel, BorderLayout.LINE_START);

		recipeDetails = new JPanel();
		recipeDetails.setLayout(new BoxLayout(recipeDetails, BoxLayout.PAGE_AXIS));

		JPanel labelsView = new JPanel();
		labelsView.setLayout(new FlowLayout(FlowLayout.LEADING, 80, 0));
		JLabel ingredientLabel = new JLabel("Ingredient");
		JLabel amountLabel = new JLabel("Amount");
		labelsView.add(ingredientLabel);
		labelsView.add(amountLabel);
		recipeDetails.add(labelsView);
		/**
		 * Details
		**/

		detailsPane = new JPanel();
		detailsLayout = new GridLayout(detailsRows, 5, 5, 5);
		detailsPane.setLayout(detailsLayout);

		detailsScroll = new JScrollPane(detailsPane);
		recipeDetails.add(detailsScroll);

		JButton newIngredientButton = new JButton("New Ingredient");
		newIngredientButton.addActionListener(new newIngredientHandler());
		recipeDetails.add(newIngredientButton);

		add(recipeDetails, BorderLayout.CENTER);
	}

	public void updateView() {
		detailsScroll.updateUI();
		detailsPane.updateUI();
		recipeDetails.updateUI();
	}

	public void addNewIngredientToRecipe(String ingredient, String amount, String messure, boolean editable, boolean newField) {
		detailsRows++;
		detailsLayout.setRows(detailsRows);
		detailsPane.setLayout(detailsLayout);

		Vector<String> ingredients = new Vector<String>();
		for(Ingredient i : db.getIngredients()) {
			ingredients.add(i.getName());
		}

		JLabel messureLabel = new JLabel(messure);

		JComboBox<String> ingredientField = new JComboBox<String>(ingredients);
		ingredientField.setSelectedItem(ingredient);
		ingredientField.addActionListener(new IngredientSelectionHandler(messureLabel));

		NumberFormatter format = new NumberFormatter();
		JFormattedTextField amountField = new JFormattedTextField(format);
		amountField.setText(amount);

		ingredientField.setEnabled(newField);
		amountField.setEnabled(newField);

		detailsPane.add(ingredientField);
		detailsPane.add(amountField);
		detailsPane.add(messureLabel);

		JButton updateButton = new JButton("Update");
		JButton removeButton = new JButton("Remove");
		updateButton.setEnabled(editable);
		if (newField) {
			updateButton.setText("Save");
		}

		updateButton.addActionListener(new updateIngredient(ingredientField, amountField, newField));
		removeButton.addActionListener(new RemoveIngredientHandler(ingredientField));
		detailsPane.add(updateButton);
		detailsPane.add(removeButton);
		updateView();
	}

	public void deleteIngredientFromRecipe(String ingredient, String recipe) {
		db.removeIngredientFromRecipe(ingredient, recipe);

	}

	public void getRecipes() {
		recipeListModel.removeAllElements();
		for(Recipe r: db.getRecipes()) {
			recipeListModel.addElement(r.getName());
		}
	}

	@Override
	public void switchedState() {
		getRecipes();
	}

	public void fillDetailsView() {
		detailsPane.removeAll();
		detailsRows = 0;
		detailsLayout.setRows(detailsRows);

		boolean inProduction = db.recipeInProduction(recipeList.getSelectedValue());

		ArrayList<Ingredient> ingredients = db.getIngredientsForRecipe(recipeList.getSelectedValue());
		for(Ingredient i : ingredients) {
			addNewIngredientToRecipe(i.getName(), String.valueOf(i.getAmount()), i.getMessure(), !inProduction, false);
		}
	}

	class newRecipeHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			String recipeName = JOptionPane.showInputDialog("Recipe name:");
			db.addRecipe(recipeName);
			getRecipes();
		}
	}

	class newIngredientHandler implements ActionListener {

		@Override
		public void actionPerformed(ActionEvent e) {
			if (!db.recipeInProduction(recipeList.getSelectedValue())) {
				addNewIngredientToRecipe("", "", "", true, true);
			}
		}
	}

	class updateIngredient implements ActionListener {

		private JComboBox<String> ingredientField;
		private JTextField amountField;
		private boolean updating = false;
		private boolean newField;

		public updateIngredient(JComboBox<String> ingredient, JTextField amount, boolean newField) {
			ingredientField = ingredient;
			amountField = amount;
			this.newField = newField;
			updating = newField;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JButton button = (JButton) e.getSource();
			updating = !updating;

			if (amountField.getText().equals("")) {
				amountField.setText("0");
			}

			if(db.recipeInProduction(recipeList.getSelectedValue())) {
				fillDetailsView();
				return;
			}

			if (updating) {
				button.setText("Save");
			} else {
				button.setText("Update");
				if (newField) {
					db.addIngredientToRecipe(ingredientField.getSelectedItem().toString(), recipeList.getSelectedValue(), Integer.valueOf(amountField.getText()));
					newField = false;
				} else {
					db.updateIngredientToRecipe(ingredientField.getSelectedItem().toString(), recipeList.getSelectedValue(), Integer.valueOf(amountField.getText()));
				}
			}
			ingredientField.setEnabled(false);
			amountField.setEnabled(updating);
		}
	}

	class RemoveIngredientHandler implements ActionListener {

		private JComboBox<String> ingredient;

		public RemoveIngredientHandler(JComboBox<String> ingredient) {
			this.ingredient = ingredient;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			deleteIngredientFromRecipe(ingredient.getSelectedItem().toString(), recipeList.getSelectedValue());
			fillDetailsView();
			updateView();
		}

	}

	class RecipeSelectionHandler implements ListSelectionListener {

		@Override
		public void valueChanged(ListSelectionEvent e) {
			fillDetailsView();
			updateView();
		}
	}

	class IngredientSelectionHandler implements ActionListener {

		private JLabel label;

		public IngredientSelectionHandler(JLabel label) {
			this.label = label;
		}

		@Override
		public void actionPerformed(ActionEvent e) {
			JComboBox<String> box = (JComboBox<String>) e.getSource();
			if (box.getSelectedItem() != null) {
				label.setText(db.getIngredient(box.getSelectedItem().toString()).getMessure());
			}
		}
	}
}
