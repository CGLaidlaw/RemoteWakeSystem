package remotewake.view;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
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

	@FXML
	private void saveButtonAction () {
		System.out.println("Saving new Connection");
	}
	
	@FXML
	private void closeButtonAction () {
		stage.close();
	}
}
