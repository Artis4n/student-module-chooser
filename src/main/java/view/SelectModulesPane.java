package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import model.Course;
import model.Module;

/**
 * The 'Select Modules Pane' pane.
 */
public final class SelectModulesPane extends GridPane {

	private Label notification;
	private Button reset, remove, add, submit;
	private TextField currentCredits;
	private ListView<Module> unselectedModules;
	private ListView<Module> selectedModules;
	private ObservableList<Module> modulesUnselected = FXCollections.observableArrayList();
	private ObservableList<Module> modulesSelected = FXCollections.observableArrayList();
	private ObservableList<Module> tempSelected = FXCollections.observableArrayList();
	private int credits, creditCheck;

	public SelectModulesPane() {

		this.setAlignment(Pos.CENTER);

		// labels
		Label um = new Label("Unselected Modules");
		Label sm = new Label("Selected Modules");
		Label cc = new Label("Current Credits: ");
		notification = new Label("");

		um.setPadding(new Insets(30, 0, 0, 30));
		sm.setPadding(new Insets(30, 0, 0, 60));
		cc.setPadding(new Insets(30, 0, 0, 330));		

		notification.setPadding(new Insets(30, 0, 0, 120));
		notification.setTextFill(Color.web("#FF0000"));
		notification.setWrapText(true);

		this.add(um, 0, 0);
		this.add(sm, 1, 0);
		this.add(cc, 0, 2);
		this.add(notification, 1, 2);

		// list 1
		unselectedModules = new ListView<>();
		HBox umList = new HBox(unselectedModules);
		modulesUnselected = FXCollections.observableArrayList();
		unselectedModules.setItems(modulesUnselected);
		umList.setPadding(new Insets(0, 0, 0, 30));
		unselectedModules.setPrefSize(REMAINING, REMAINING);

		this.add(umList, 0, 1);

		// list 2
		selectedModules = new ListView<Module>();
		HBox smList = new HBox(selectedModules);
		modulesSelected = FXCollections.observableArrayList();
		selectedModules.setItems(modulesSelected);
		smList.setPadding(new Insets(0, 30, 0, 60));
		selectedModules.setPrefSize(REMAINING, REMAINING);

		this.add(smList, 1, 1);

		// TextField HBox Module Credits
		currentCredits = new TextField();
		HBox cTxtBox = new HBox(currentCredits);
		currentCredits.setEditable(false);
		currentCredits.setPrefSize(60, 20);
		cTxtBox.setPadding(new Insets(30, 0, 0, 0));

		this.add(cTxtBox, 1, 2);

		// reset & remove buttons
		reset = new Button("Reset");
		reset.setPrefSize(80, 30);
		remove = new Button("Remove");
		remove.setPrefSize(80, 30);

		HBox rrBtnBox = new HBox(reset, remove);
		rrBtnBox.setPadding(new Insets(30, 0, 30, 260));
		rrBtnBox.setSpacing(10);
		rrBtnBox.setAlignment(Pos.CENTER_RIGHT);

		this.add(rrBtnBox, 0, 3);

		// add & submit buttons
		add = new Button("Add");
		add.setPrefSize(80, 30);
		submit = new Button("Submit");
		submit.setPrefSize(80, 30);

		HBox asBtnBox = new HBox(add, submit);
		asBtnBox.setPadding(new Insets(30, 0, 30, 10));
		asBtnBox.setSpacing(10);

		this.add(asBtnBox, 1, 3);

	}

	// methods to update the content of this pane

	/**
	 * Method for populating two lists using selected course
	 * @param selected course
	 */
	public void addModulesToLists(Course selectedCourse) {
		clear();
		clearNotification();
		credits = 0;

		selectedCourse.getModulesOnCourse().forEach(n -> {
			if (n.isMandatory() != true) {
				modulesUnselected.add(n);
			} else {
				modulesSelected.add(n);
				credits = credits + n.getCredits();
			}
			currentCredits.setText(String.valueOf(credits));
		});
	}

	/**
	 * Method to reset module lists
	 */
	public void resetModules(){
		modulesSelected.forEach(n-> {
			if(n.isMandatory() != true) {
				modulesUnselected.add(n);
				tempSelected.add(n);
				credits = credits - n.getCredits();
			}
		});

		tempSelected.forEach(x -> {
			modulesSelected.remove(x);
		});

		currentCredits.setText(String.valueOf(credits));
	}

	/**
	 * Method to add modules to selected modules list
	 */
	public void addSelectedModule() {
		if (unselectedModules.getSelectionModel().isEmpty() == false) {
			creditCheck = credits + unselectedModules.getSelectionModel().getSelectedItem().getCredits();

			if (creditCheck < 121) {
				clearNotification();
				credits = creditCheck;
				currentCredits.setText(String.valueOf(credits));

				modulesSelected.add(unselectedModules.getSelectionModel().getSelectedItem());
				modulesUnselected.remove(unselectedModules.getSelectionModel().getSelectedItem());
			} else {
				notification.setText("Not enough credits left to add module.");
			}

		} else {
			notification.setText("Please select a module to add!");
		}
	}

	/**
	 * Method to remove modules from the selected modules list
	 */
	public void removeSelectedModule() {
		if (selectedModules.getSelectionModel().isEmpty() == false) {
			if (selectedModules.getSelectionModel().getSelectedItem().isMandatory() == true) {
				notification.setText("Cannot remove mandatory module!");

			} else {
				clearNotification();
				credits = credits - selectedModules.getSelectionModel().getSelectedItem().getCredits();
				currentCredits.setText(String.valueOf(credits));
				modulesUnselected.add(selectedModules.getSelectionModel().getSelectedItem());
				modulesSelected.remove(selectedModules.getSelectionModel().getSelectedItem());
			}

		} else {
			notification.setText("Please select a module to remove!");
		}
	}

	/**
	 * Method for getting modules selected
	 * @return selected modules
	 */
	public ObservableList<Module> getContentsSelected() {
		return modulesSelected;
	}

	/**
	 * Load modules into unselected module list
	 * @param loadedCourse
	 */
	public void loadUnselectedModules(Course loadedCourse) {

		loadedCourse.getModulesOnCourse().forEach(n -> {
			if (n.isMandatory() != true) {
				modulesUnselected.add(n);
			}
		});
	}

	/**
	 * Remove unselected modules
	 * @param module
	 */
	public void removeUnselectedModules(Module module) {
		modulesUnselected.remove(module);
		credits = 120;
		currentCredits.setText(String.valueOf(credits));
	}

	/**
	 * Clear module lists
	 */
	public void clear() {
		unselectedModules.getItems().clear();
		selectedModules.getItems().clear();
	}

	/**
	 * Clear notification label
	 */
	public void clearNotification() {
		notification.setText("");
	}

	/**
	 * Method to check credit amount
	 * @return credit limit reached
	 */
	public boolean creditCheck() {
		if (credits == 120) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Method to attach the reset modules button handler
	 * @param handler
	 */
	public void addResetHandler(EventHandler<ActionEvent> handler) {
		reset.setOnAction(handler);
	}

	/**
	 * Method to attach the add modules button handler
	 * @param handler
	 */
	public void addAddHandler(EventHandler<ActionEvent> handler) {
		add.setOnAction(handler);
	}

	/**
	 * Method to attach the remove modules button handler
	 * @param handler
	 */
	public void addRemoveHandler(EventHandler<ActionEvent> handler) {
		remove.setOnAction(handler);
	}

	/**
	 * Method to attach the submit modules button handler
	 * @param handler
	 */
	public void addSubmitHandler(EventHandler<ActionEvent> handler) {
		submit.setOnAction(handler);
	}

}
