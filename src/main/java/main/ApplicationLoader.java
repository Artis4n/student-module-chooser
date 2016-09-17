package main;

import controller.Controller;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.StudentProfile;
import view.ModuleRootPane;

/**
 * The Student Module Chooser application provides the ability for students to enter their profile information 
 * and select modules for their final year which can be saved and reloaded 
 * This was developed by using the ‘Model–view–controller’ architectural pattern.
 */
public class ApplicationLoader extends Application {

	private ModuleRootPane view;

	@Override
	public void init() {
		// create model and view and pass their references to the controller
		StudentProfile model = new StudentProfile();

		view = new ModuleRootPane();
		new Controller(view, model);
	}

	@Override
	public void start(Stage stage) throws Exception {
		stage.setWidth(1000);
		stage.setHeight(600);
		stage.setTitle("Final Year Module Chooser Tool");
		stage.setScene(new Scene(view));
		stage.show();
	}

	/**
	 * The initial entry point for this program.
	 * @param args The arguments passed to the program.
	 */
	public static void main(String[] args) {
		launch(args);
	}

}
