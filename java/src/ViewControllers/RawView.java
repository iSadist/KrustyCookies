package ViewControllers;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Models.Database;

public class RawView extends JTabbedPane {

	private static final long serialVersionUID = 1L;

	private DefaultListModel<String> ingredientListModel;
	private DefaultListModel<String> recipeListModel;

	private Database db;

	public RawView(Database db) {
		this.db = db;

		JPanel ingredientPane = new IngredientView(db);
		JPanel recipePane = new RecipeView(db);

		addTab("Ingredients", ingredientPane);
		addTab("Recipes", new ImageIcon() , recipePane, "Test");

		addChangeListener(new RawChangeHandler());
		getSelectedComponent();
	}
}


class RawChangeHandler implements ChangeListener {
	public void stateChanged(ChangeEvent e) {
		RawView rv = (RawView) e.getSource();
		BasicSubview rsv = (BasicSubview) rv.getSelectedComponent();
		rsv.switchedState();
	}
}
