package view;

import java.time.LocalDate;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import model.Course;
import model.Name;
import model.StudentProfile;

/**
 * The 'create profile' pane.
 */
public final class CreateProfilePane extends GridPane {

	private ComboBox<Course> cboCourses;
	private Label notification;
	private TextField txtPnumber, txtFirstname, txtSurname, txtEmail;
	private Button btnCreateProfile;
	private DatePicker datePicker = new DatePicker();
	private LocalDate selectedDate;
	private Name studentName = new Name();

	public CreateProfilePane() {
		// styling
		this.setPadding(new Insets(80, 80, 80, 80));
		this.setVgap(15);
		this.setHgap(20);
		this.setAlignment(Pos.CENTER);

		// create labels
		Label lblCourse = new Label("Select course: ");
		Label lblPnumber = new Label("Input P number: ");
		Label lblFirstname = new Label("Input first name: ");
		Label lblSurname = new Label("Input surname: ");
		Label lblEmail = new Label("Input email: ");
		Label lblDate = new Label("Input date: ");

		// create notification label
		notification = new Label("");
		notification.setTextFill(Color.web("#FF0000"));

		// setup combobox
		cboCourses = new ComboBox<Course>(); // will be populated via method
		// towards end of class

		// setup text fields
		txtPnumber = new TextField();
		txtFirstname = new TextField();
		txtSurname = new TextField();
		txtEmail = new TextField();

		// initialise create profile button
		btnCreateProfile = new Button("Create Profile");
		
		//make datepicker uneditable 
		datePicker.setEditable(false);

		// add controls and labels to container
		this.add(lblCourse, 0, 0);
		this.add(cboCourses, 1, 0);

		this.add(lblPnumber, 0, 1);
		this.add(txtPnumber, 1, 1);

		this.add(lblFirstname, 0, 2);
		this.add(txtFirstname, 1, 2);

		this.add(lblSurname, 0, 3);
		this.add(txtSurname, 1, 3);

		this.add(lblEmail, 0, 4);
		this.add(txtEmail, 1, 4);

		this.add(lblDate, 0, 5);
		this.add(datePicker, 1, 5);

		this.add(new HBox(), 0, 6);
		this.add(btnCreateProfile, 1, 6);

		this.add(notification, 1, 7);
	}

	/**
	 * Method to allow the controller 
	 * to populate the combobox with courses.
	 * 
	 * @param courses
	 */
	public void populateComboBox(Course[] courses) {
		cboCourses.getItems().addAll(courses);
		cboCourses.getSelectionModel().select(0);
	}


	/**
	 * Method to retrieve the course selection.
	 * 
	 * @return
	 */
	public Course getSelectedCourse() {
		return cboCourses.getSelectionModel().getSelectedItem();
	}

	/**
	 * Method to retrieve the PNumber input
	 * and validates using regex.
	 * e.g: (p14174332 = pass, x122343224yxs = fail)
	 * 
	 * @return pNumber of student
	 */
	public String getPNumberInput() {
		String pNumber = "";

		if (txtPnumber.getText().matches("^[Pp][0-9]{8}[a-zA-Z{1}]?$")) {
			pNumber = txtPnumber.getText();
			notification.setText("");
		} else {
			notification.setText("Please check your PNumber is correct!");
		}
		return pNumber;
	}

	/**
	 * Method to retrieve the input of student name
	 * and validates using regex. 
	 * e.g: (joe = pass, joe1 = fail )
	 * 
	 * @return student name
	 */
	public Name getStudentNameInput() {
		if(txtFirstname.getText().matches("[a-zA-Z]+\\.?") && txtSurname.getText().matches("[a-zA-Z]+\\.?")) {	
			studentName = new Name(txtFirstname.getText(), txtSurname.getText());
			notification.setText("");  
		} else {
			studentName = new Name("Incorrect", "Name");
			notification.setText("Please check your first & second are correct!"); 
		}

		return studentName;
	}

	/**
	 * Method to retrieve the email input
	 * and validates using regex. 
	 * e.g: (joe.bloggs@email.com = pass, joebloggs.email.com = fail)
	 * 
	 * @return email selected by user
	 */
	public String getEmailInput() {
		String email = "";

		if (txtEmail.getText().matches("^(.+)@(.+)$")) {
			email = txtEmail.getText();
			notification.setText("");
		} else {
			notification.setText("Please check your email is valid!");
		}

		return email;
	}

	/**
	 * Method to retrieve the date input.
	 * 
	 * @return date selected by the user
	 */
	public LocalDate getDateInput() {
		datePicker.setOnAction(event -> {
			datePicker.getValue();

			if (datePicker.valueProperty().isNull() != null) {
				selectedDate = datePicker.getValue();
				notification.setText("");
			} else {
				notification.setText("Please check your date is valid!");
				selectedDate = null;
			}
		});

		if (datePicker.valueProperty().isNull() != null) {
			selectedDate = datePicker.getValue();
			notification.setText("");
		} else {
			notification.setText("Please check your date is valid!");
			selectedDate = null;
		}
		return selectedDate;
	}

	/**
	 * Validation to check fields.
	 * 
	 * @return true if invalid and false if valid
	 */
	public boolean checkFields() {
		boolean result = false;

		if (getSelectedCourse().equals(null) || getPNumberInput().isEmpty()|| getStudentNameInput().getFirstName().toString() == "Incorrect" 
				|| getEmailInput().equals("") || getDateInput() == null) {
			result = true;
		}
		return result;
	}

	/**
	 * Updates fields from a student profile.
	 * 
	 * @param studentProfile
	 */
	public void updateFields(StudentProfile studentProfile) {
		cboCourses.setValue(studentProfile.getCourse());  
		datePicker.setValue(studentProfile.getDate());
		txtEmail.setText(studentProfile.getEmail());
		txtFirstname.setText(studentProfile.getStudentName().getFirstName());
		txtSurname.setText(studentProfile.getStudentName().getFamilyName());
		txtPnumber.setText(studentProfile.getpNumber());
	}

	/**
	 * Method to attach the create profile button handler.
	 * 
	 * @param handler
	 */
	public void addCreateProfileHandler(EventHandler<ActionEvent> handler) {
		btnCreateProfile.setOnAction(handler);
	}

}
