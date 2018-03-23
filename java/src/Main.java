import javax.swing.JComponent;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import Models.Database;
import ViewControllers.RawView;

public class Main {
	public static void main(String[] args) {
		Database db = new Database();
		db.openConnection("database.db");

		JFrame gui = new JFrame();
		Object [] options = {"Raw!", "Production"};
		int option = JOptionPane.showOptionDialog(gui, "Chose which program to use.", "Program chooser", JOptionPane.INFORMATION_MESSAGE, JOptionPane.PLAIN_MESSAGE, null, options, options[1]);

		JComponent component = null;

		switch (option) {
		case 0:
			System.out.println("Starting Raw...");
			component = new RawView(db);
			gui.setSize(1250, 350);
			break;
		case 1:
			System.out.println("Starting Production...");
			// component = new ProductionView(db);
			// gui.setSize(600, 700);
			break;
		}
		gui.add(component);
		gui.setVisible(true);
	}

}
