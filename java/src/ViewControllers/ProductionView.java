package ViewControllers;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Models.Database;

public class ProductionView extends JTabbedPane {

	private static final long serialVersionUID = 1L;

	public ProductionView(Database db) {

		JPanel searchPane = new SearchPallets(db);
		JPanel blockedPane = new BlockedPalletsView(db);
		JPanel productionPane = new PalletProductionView(db);

		addTab("Search", searchPane);
		addTab("Blocked", new ImageIcon() , blockedPane, "Test");
		addTab("Production", productionPane);

		addChangeListener(new ProductionChangeHandler());
		getSelectedComponent();

	}

	class ProductionChangeHandler implements ChangeListener {
		public void stateChanged(ChangeEvent e) {
			ProductionView rv = (ProductionView) e.getSource();
			BasicSubview rsv = (BasicSubview) rv.getSelectedComponent();
			rsv.switchedState();
		}
	}

}
