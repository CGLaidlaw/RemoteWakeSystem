package remotewake.view;

import org.json.simple.JSONObject;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import remotewake.controller.FileOperations;
import remotewake.model.RemoteMain;

public class SaveConnectionController {

	@FXML
	private TextField connectionName;
	@FXML
	private Label macAddress;
	@FXML
	private Button saveButton;
	@FXML
	private Button closeButton;

	Stage stage;
	RemoteMain main;

	public void setStage (Stage stage) {
		this.stage = stage;
	}

	public void setMain (RemoteMain main) {
		this.main = main;
	}

	@SuppressWarnings("unchecked")
	@FXML
	private void saveButtonAction () {
		boolean nameInUse = true;

		Alert nameUsed = new Alert(AlertType.ERROR);
		nameUsed.setTitle("Name In Use");
		nameUsed.setHeaderText("Invalid Name");
		nameUsed.getDialogPane().setContent(new Label("This name has already been used. Please\nselect a different name for this "
				+ "connection."));

		FileOperations ops = new FileOperations();
		ops.readFromFile();
		for (int i = 0; i < ops.connections.size(); i++) {
			if (connectionName.getText().equals(ops.connections.get(i).get("connName"))) {
				nameInUse = true;
				nameUsed.showAndWait();
				connectionName.clear();
				break;
			} else {
				nameInUse = false;
			}
		}

		if (!nameInUse) {
			JSONObject newConnection = new JSONObject();
			String cleanMAC = macAddress.getText().replace("-", ":");
			newConnection.put("connName", connectionName.getText());
			newConnection.put("macAddress", cleanMAC);
			FileOperations saveFile = new FileOperations();
			saveFile.saveToFile(newConnection);

			stage.close();
		}
	}

	@FXML
	private void closeButtonAction () {
		stage.close();
	}

	public void setMAC (String macAddress) {
		this.macAddress.setText(macAddress);
	}
}
