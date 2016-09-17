package view;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;

/**
 * The 'Overview Selection' pane.
 */
public final class OverviewSelectionPane extends GridPane {

	private TextArea stdPrfOverview;
	private Button saveButton;

	public OverviewSelectionPane() {

		// styling
		this.setPadding(new Insets(80, 80, 80, 80));
		this.setVgap(15);
		this.setHgap(20);
		this.setAlignment(Pos.CENTER);

		// create text area
		stdPrfOverview = new TextArea("Overview will appear here");
		stdPrfOverview.setEditable(false);

		HBox spBox = new HBox(stdPrfOverview);
		spBox.setPadding(new Insets(0, 60, 0, 60));
		stdPrfOverview.setPrefSize(REMAINING, REMAINING);

		this.add(spBox, 0, 0);

		//create button
		saveButton = new Button("Save Overview");
		saveButton.setPrefSize(120, 30);

		HBox btnBox = new HBox(saveButton);
		btnBox.setPadding(new Insets(30, 0, 0, 330));

		this.add(btnBox, 0, 1);

	}

	/**
	 * Method updates content of the student profile text area
	 * @param profile overview
	 */
	public void setStudentProfile(String profileOverview) {
		stdPrfOverview.setText(profileOverview);
	}

	/**
	 * Method to save profile to a file in text format
	 */
	public void saveProfileTxt() {
		List<String> lines = Arrays.asList(stdPrfOverview.getText());
		Path file = Paths.get("student-profile.txt");
		try {
			Files.write(file, lines, Charset.forName("UTF-8"));
		} catch (IOException e) {
			// proper error handling needed
		}
	}

	/**
	 * Method to attach the save overview button handler.
	 * 
	 * @param handler
	 */
	public void addSaveOverviewHandler(EventHandler<ActionEvent> handler) {
		saveButton.setOnAction(handler);
	}
}
