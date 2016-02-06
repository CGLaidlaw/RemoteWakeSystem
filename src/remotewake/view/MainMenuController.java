package remotewake.view;

import java.util.regex.Pattern;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import remotewake.controller.FileOperations;
import remotewake.model.RemoteMain;

public class MainMenuController {

	@FXML
	private TextField newConnection;
	@FXML
	private Button saveNew;

	@FXML
	public ChoiceBox<String> savedConnections;

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
	ObservableList<String> names = FXCollections.observableArrayList();
	
	Pattern macFormatCheck = Pattern.compile("^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$");

	public void setStage (Stage stage) {
		this.stage = stage;
	}

	public void setMain (RemoteMain main) {
		this.main = main;
	}

	@FXML
	private void initialize () {
		System.out.println("Initializing, set Choice Box");
		FileOperations ops = new FileOperations();
		ops.readFromFile();
		for (int i = 0; i < ops.connections.size(); i++) {
			names.add((String) ops.connections.get(i).get("connName"));
		}
		
		savedConnections.setItems(names);
	}

	@FXML
	private void saveNewButton () {
		
		Alert alert = new Alert(AlertType.ERROR);
		alert.setTitle("Invalid MAC Address");
		alert.setHeaderText("Check MAC Address");
		alert.setContentText("Please check the MAC address entered. It should be six pairs separated by '-' or ':'"
				+ " within the inclusive range of A-F (a-f) or (0-9) per value.");
		
		if (macFormatCheck.matcher(newConnection.getText()).matches()) {
			main.SaveMenu (newConnection.getText());
		} else {
			alert.showAndWait();
		}
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
