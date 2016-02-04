package remotewake.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import remotewake.model.RemoteMain;

public class MainMenuController {

	@FXML
	private TextField newConnection;
	@FXML
	private Button saveNew;

	@FXML
	private ChoiceBox<String> savedConnections;

	@FXML
	private Label statusLabel;

	@FXML
	private Button close;
	@FXML
	private Button statusCheck;
	@FXML
	private Button remoteWake;

	RemoteMain main;
	Stage stage;

	public void setStage (Stage stage) {
		this.stage = stage;
	}

	public void setMain (RemoteMain main) {
		this.main = main;
	}

	@FXML
	private void initialize () {
		System.out.println("Initializing, set Choice Box");
	}

	@FXML
	private void saveNewButton () {
		main.SaveMenu ();
	}

	@FXML
	private void wakeButton () {
		System.out.println("Wake Target System");
	}

	@FXML
	private void statusButton () {
		System.out.println("Status Check Button");
	}

	@FXML
	private void closeButton () {
		System.exit(0);
	}
}
