package view;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.control.TabPane.TabClosingPolicy;
import javafx.scene.layout.VBox;

/**
 * The view in the ‘Model–view–controller’ architectural pattern 
 * primary role is to display the model data, and sends user actions to the controller.
 * It is independent of both the model and the controller; and does not deal 
 * with any of the logic or functions in the program.
 */
public final class ModuleRootPane extends VBox {

	private CreateProfilePane cpp;
	private SelectModulesPane smp;
	private OverviewSelectionPane osp;
	private ModuleMenuBar mmp;

	private TabPane rootTabs;

	public ModuleRootPane() {

		mmp = new ModuleMenuBar();
		cpp = new CreateProfilePane();
		smp = new SelectModulesPane();
		osp = new OverviewSelectionPane();

		// create tabs with panes added
		rootTabs = new TabPane();
		rootTabs.setTabClosingPolicy(TabClosingPolicy.UNAVAILABLE);
		Tab t1 = new Tab("Create Profile", cpp);
		Tab t2 = new Tab("Select Modules", smp);
		Tab t3 = new Tab("Overview Selection", osp);

		rootTabs.getTabs().addAll(t1, t2, t3);

		// add tabs to tab pane
		this.getChildren().addAll(mmp, rootTabs);

	}

	/**
	 * Method allows tab to be changed
	 * 
	 * @param index of tab
	 */
	public void changeTab(int index) {
		rootTabs.getSelectionModel().select(index);
	}

	// These methods provide a public interface for the root pane and allow each
	// of the sub-containers to be accessed by the controller.

	/**
	 * Gets the menu bar interface
	 * 
	 * @return menu bar
	 */
	public ModuleMenuBar getModuleMenuBar() {
		return mmp;
	}

	/**
	 * Gets the create profile interface
	 * 
	 * @return create profile pane
	 */
	public CreateProfilePane getCreateProfilePane() {
		return cpp;
	}

	/**
	 * Gets the selected module interface
	 * 
	 * @return select modules pane
	 */
	public SelectModulesPane getSelectModulesPane() {
		return smp;
	}

	/**
	 * Gets the overview selection interface
	 * 
	 * @return overview selection pane
	 */
	public OverviewSelectionPane getOverviewSelectionPane() {
		return osp;
	}
}
