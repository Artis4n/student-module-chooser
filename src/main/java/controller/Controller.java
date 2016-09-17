package controller;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import view.CreateProfilePane;
import view.ModuleRootPane;
import view.ModuleMenuBar;
import view.OverviewSelectionPane;
import view.SelectModulesPane;
import model.StudentProfile;
import model.Course;
import model.Module;

/**
 * Controllers process incoming requests, handle user input and interactions, and execute appropriate application logic 
 * This is defined in the ‘Model–view–controller’ architectural pattern.
 */

public class Controller {

	/**
	 * Fields to be used throughout the class.
	 */
	private CreateProfilePane cpp;
	private SelectModulesPane smp;
	private OverviewSelectionPane osp;
	private ModuleMenuBar mmb;
	private StudentProfile model;
	private ModuleRootPane view;

	/**
	 * Creates a new {@link Controller}
	 * @param model The model.
	 * @param view The view.
	 */
	public Controller(ModuleRootPane view, StudentProfile model) {

		// Initialize model and view fields
		this.model = model;
		this.view = view;

		cpp = view.getCreateProfilePane();
		smp = view.getSelectModulesPane();
		osp = view.getOverviewSelectionPane();
		mmb = view.getModuleMenuBar();

		cpp.populateComboBox(setupAndGetCourses());

		this.attachEventHandlers();
	}

	/**
	 * Method for setting up two courses.
	 * @return two courses
	 */
	private Course[] setupAndGetCourses() {
		Module ctec3903 = new Module("CTEC3903", "Software Development Methods", 15, true);
		Module imat3451 = new Module("IMAT3451", "Computing Project", 30, true);
		Module ctec3902_SoftEng = new Module("CTEC3902", "Rigerous Systems", 15, true);
		Module ctec3902_CompSci = new Module("CTEC3902", "Rigerous Systems", 15, false);
		Module ctec3110 = new Module("CTEC3110", "Secure Web Application Development", 15, false);

		Module ctec3426 = new Module("CTEC3426", "Telematics", 15, false);
		Module ctec3604 = new Module("CTEC3604", "Multi-service Networks", 30, false);
		Module ctec3410 = new Module("CTEC3410", "Web Application Penetration Testing", 15, false);
		Module ctec3904 = new Module("CTEC3904", "Functional Software Development", 15, false);
		Module ctec3905 = new Module("CTEC3905", "Front-End Web Development", 15, false);
		Module imat3410 = new Module("IMAT3104", "Database Management and Programming", 15, false);
		Module imat3404 = new Module("IMAT3404", "Mobile Robotics", 15, false);
		Module imat3406 = new Module("IMAT3406", "Fuzzy Logic and Knowledge Based Systems", 15, false);
		Module imat3429 = new Module("IMAT3429", "Privacy and Data Protection", 15, false);
		Module imat3902 = new Module("IMAT3902", "Computing Ethics", 15, false);
		Module imat3426_CompSci = new Module("IMAT3426", "Information Systems Strategy and Services", 30, false);

		Course compSci = new Course("Computer Science");
		compSci.addModule(ctec3903);
		compSci.addModule(imat3451);
		compSci.addModule(ctec3902_CompSci);
		compSci.addModule(ctec3110);
		compSci.addModule(ctec3426);
		compSci.addModule(ctec3604);
		compSci.addModule(ctec3410);
		compSci.addModule(ctec3904);
		compSci.addModule(ctec3905);
		compSci.addModule(imat3410);
		compSci.addModule(imat3404);
		compSci.addModule(imat3406);
		compSci.addModule(imat3429);
		compSci.addModule(imat3902);
		compSci.addModule(imat3426_CompSci);

		Course softEng = new Course("Software Engineering");
		softEng.addModule(ctec3903);
		softEng.addModule(imat3451);
		softEng.addModule(ctec3902_SoftEng);
		softEng.addModule(ctec3110);
		softEng.addModule(ctec3426);
		softEng.addModule(ctec3604);
		softEng.addModule(ctec3410);
		softEng.addModule(ctec3904);
		softEng.addModule(ctec3905);
		softEng.addModule(imat3410);
		softEng.addModule(imat3404);
		softEng.addModule(imat3406);
		softEng.addModule(imat3429);
		softEng.addModule(imat3902);

		Course[] courses = new Course[2];
		courses[0] = compSci;
		courses[1] = softEng;
		return courses;
	}

	/**
	 * Method to attach all the event handlers.
	 */
	private void attachEventHandlers() {
		cpp.addCreateProfileHandler(new CreateProfileHandler());
		smp.addResetHandler(new ResetHandler());
		smp.addAddHandler(new AddHandler());
		smp.addRemoveHandler(new RemoveHandler());
		smp.addSubmitHandler(new SubmitHandler());
		osp.addSaveOverviewHandler(new SaveOverviewHandler());
		mmb.addSaveProfileHandler(new SaveProfileHandler());
		mmb.addLoadStudentDataHandler(new LoadStudentDataHandler());
		mmb.addAboutHandler(new AboutHandler());
		mmb.addExitHandler(new ExitHandler());
	}

	/**
	 * Handler for creating profile.
	 */
	private class CreateProfileHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {

			if (cpp.checkFields() == true) {
				Alert alert = new Alert(AlertType.ERROR);
				alert.setTitle("Profile Warning");
				alert.setHeaderText("Incorrect profile");
				alert.setContentText("Please enter check your details to continue!");

				alert.showAndWait();
			} else {
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Profile Confirmation");
				alert.setHeaderText("Confirm profile details");
				alert.setContentText("Are you ok with your profile details?");

				Optional<ButtonType> result = alert.showAndWait();
				if (result.get() == ButtonType.OK) {

					model.setCourse(cpp.getSelectedCourse());
					model.setpNumber(cpp.getPNumberInput());
					model.setStudentName(cpp.getStudentNameInput());
					model.setEmail(cpp.getEmailInput());
					model.setDate(cpp.getDateInput());

					smp.addModulesToLists(cpp.getSelectedCourse());

					view.changeTab(1);
				}
			}
		}

	}

	/**
	 * Handler for Reseting modules.
	 */
	private class ResetHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {
			smp.resetModules();
		}

	}

	/**
	 * Handler for Adding selected modules.
	 */
	private class AddHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {
			smp.addSelectedModule();
		}

	}

	/**
	 * Handler for Removing selected modules.
	 */
	private class RemoveHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {
			smp.removeSelectedModule();
		}

	}

	/**
	 * Handler for Submitting modules.
	 */
	private class SubmitHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {

			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Submit");
			alert.setHeaderText("Submitting Modules...");
			alert.setContentText("Are you ok with your selected modules?");

			Optional<ButtonType> result = alert.showAndWait();
			if (result.get() == ButtonType.OK) {

				if (smp.creditCheck() == true) { // check if credits == 120
					// add selected modules to student profile
					smp.getContentsSelected().forEach(n -> model.addSelectedModule(n));

					// loading overview Textbox
					osp.setStudentProfile(setOverviewString());

					//change to overview tab
					view.changeTab(2);
				} else {
					alertDialogBuilder(AlertType.ERROR, "Error", "Load Failed", "Please have 120 credits to continue.");
				}
			}
		}
	}

	/**
	 * Handler for Saving student profile to text file.
	 */
	private class SaveOverviewHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Save Overview");
			alert.setHeaderText("Saving profile to text file...");
			alert.setContentText("Are you sure you want to save the current overview?");

			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == ButtonType.OK) {
				osp.saveProfileTxt();
			}

		}

	}

	/**
	 * Handler for Saving student profile to data file.
	 */
	private class SaveProfileHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Save");
			alert.setHeaderText("Saving profile to dat file...");
			alert.setContentText("Are you sure you want to save the current profile?");

			Optional<ButtonType> result = alert.showAndWait();

			if (result.get() == ButtonType.OK) {
				try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream("student-profile.dat"));) {
					if(model.getSelectedModules() != null) {
						oos.writeObject(model);
						oos.flush();
						alertDialogBuilder(AlertType.INFORMATION, "Save", "Save success",
								"Profile saved to student-profile.dat");
					}
				} catch (IOException ioExcep) {
					ioExcep.printStackTrace();
				}
			}
		}
	}

	/**
	 * Handler for Loading student profile from data file.
	 */
	private class LoadStudentDataHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {
			Alert alertSaving = new Alert(AlertType.CONFIRMATION);
			alertSaving.setTitle("Load");
			alertSaving.setHeaderText("Loading profile from dat file...");
			alertSaving.setContentText("Are you sure you want to load a saved profile?");

			Optional<ButtonType> result = alertSaving.showAndWait();

			if (result.get() == ButtonType.OK) {
				try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream("student-profile.dat"));) {

					model = (StudentProfile) ois.readObject();
					
					//updates profile pane					
					cpp.updateFields(model);

					//updates select modules pane
					smp.clear();
					// adds selected modules to selected module list
					model.getSelectedModules().forEach(smp.getContentsSelected()::add);
					
					//model.getSelectedModules().forEach(n -> System.out.println(n));

					// adds unselected modules to unselected module list
					smp.loadUnselectedModules(cpp.getSelectedCourse());
					model.getSelectedModules().forEach(n -> smp.removeUnselectedModules(n));

					// updates overview
					osp.setStudentProfile(setOverviewString());

					// success alert  
					alertDialogBuilder(AlertType.INFORMATION, "Load", "Load success",
							"Profile loaded from student-profile.dat");
				} catch (IOException ioExcep) {
					Alert alert = new Alert(AlertType.ERROR);
					alert.setTitle("Error Loading");
					alert.setHeaderText("Error loading profile");
					alert.setContentText("Ooops, there was an error loading your profile!");

					alert.showAndWait();
				} catch (ClassNotFoundException c) {
					System.out.println("Class Not found");
				}
			}
		}

	}

	/**
	 * Handler for About option in menu bar.
	 */
	private class AboutHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {
			alertDialogBuilder(AlertType.INFORMATION, "About", "About", "Program for picking final year modules.");
		}

	}

	/**
	 * Handler for Exit option in menu bar.
	 */
	private class ExitHandler implements EventHandler<ActionEvent> {

		@Override
		public void handle(ActionEvent e) {
			Platform.exit();
		}

	}

	/**
	 * Creates string to send to overview.
	 * @return string for overview
	 */
	public String setOverviewString() {

		List<String> modulesArray = new ArrayList<String>();

		smp.getContentsSelected().forEach(n -> {
			String mandatory;

			if(n.isMandatory() == true) {
				mandatory = "yes";
			} else { 
				mandatory = "no";
			}

			modulesArray.add(" \nModule code: " + n.getModuleCode() + " Credits: " + n.getCredits() + "\nMandatory on your course? "
					+ mandatory + "\nModule name: " + n.getModuleName() + "\n");
		});

		String selectedModules = Arrays.asList(modulesArray).toString().substring(1).replace(", ", "").replace("[", "").replace("]", "");

		String studentProfile = "Name: " + model.getStudentName() + "\nPNo: " + model.getpNumber() + "\nEmail: " + model.getEmail() + "\nDate: " + model.getDate() 
		+ "\nCourse: " + model.getCourse() + "\n\nSelected modules:" + "\n=========" + selectedModules;

		return studentProfile;
	}

	/**
	 * Helper method to build dialogs.
	 * @param type
	 * @param title
	 * @param header
	 * @param content
	 */
	private void alertDialogBuilder(AlertType type, String title, String header, String content) {
		Alert alert = new Alert(type);
		alert.setTitle(title);
		alert.setHeaderText(header);
		alert.setContentText(content);
		alert.showAndWait();
	}
}
