package remotewake.view;

import org.json.simple.JSONObject;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
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
		System.out.println("Saving new Connection");
		JSONObject newConnection = new JSONObject();
		newConnection.put("connName", connectionName.getText());
		newConnection.put("macAddress", macAddress.getText());
		FileOperations saveFile = new FileOperations();
		saveFile.saveToFile(newConnection);
		
		stage.close();
	}
	
	@FXML
	private void closeButtonAction () {
		stage.close();
	}
	
	public void setMAC (String macAddress) {
		this.macAddress.setText(macAddress);
	}
}
