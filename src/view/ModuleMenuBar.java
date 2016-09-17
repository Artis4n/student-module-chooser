package view;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Menu;
import javafx.scene.control.MenuBar;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SeparatorMenuItem;
import javafx.scene.input.KeyCombination;

/**
 * The menu bar that is added to the view.
 */
public final class ModuleMenuBar extends MenuBar {

	private final MenuItem loadStudentData, saveStudentData, exit, about;

	/**
	 * Creates a new {@link MenuBar}.
	 */
	public ModuleMenuBar() {

		Menu menu;

		// Build the first menu on the menu bar
		menu = new Menu("File");

		loadStudentData = new MenuItem("_Load Student Data");
		loadStudentData.setAccelerator(KeyCombination.keyCombination("SHORTCUT+L"));
		menu.getItems().add(loadStudentData);

		saveStudentData = new MenuItem("Save Student Data");
		saveStudentData.setAccelerator(KeyCombination.keyCombination("SHORTCUT+S"));
		menu.getItems().add(saveStudentData);

		// add a separator
		menu.getItems().add(new SeparatorMenuItem());

		exit = new MenuItem("Exit");
		exit.setAccelerator(KeyCombination.keyCombination("SHORTCUT+X"));
		menu.getItems().add(exit);

		// add the menu to this menubar

		this.getMenus().add(menu);

		// -----------Adding Another Button--------------------//
		menu = new Menu("Help");

		// 'About' menu item
		about = new MenuItem("About");
		about.setAccelerator(KeyCombination.keyCombination("SHORTCUT+A"));
		menu.getItems().add(about);

		this.getMenus().add(menu);

	}

	/**
	 * Method to attach the Save Profile handler
	 * 
	 * @param handler
	 */
	public void addSaveProfileHandler(EventHandler<ActionEvent> handler) {
		saveStudentData.setOnAction(handler);
	}

	/**
	 * Method to attach the Load Profile handler
	 * 
	 * @param handler
	 */
	public void addLoadStudentDataHandler(EventHandler<ActionEvent> handler) {
		loadStudentData.setOnAction(handler);
	}

	/**
	 * Method to attach the About handler
	 * 
	 * @param handler
	 */
	public void addAboutHandler(EventHandler<ActionEvent> handler) {
		about.setOnAction(handler);
	}

	/**
	 * Method to attach the Exit handler
	 * 
	 * @param handler
	 */
	public void addExitHandler(EventHandler<ActionEvent> handler) {
		exit.setOnAction(handler);
	}
}