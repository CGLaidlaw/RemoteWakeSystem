package remotewake.view;

import java.util.regex.Pattern;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleGroup;
import javafx.stage.Stage;
import remotewake.controller.FileOperations;
import remotewake.controller.WakeTarget;
import remotewake.model.RemoteMain;

public class MainMenuController {

	@FXML
	private TextField gatewayIP;

	@FXML
	private RadioButton newConnectionOption;
	@FXML
	private Label newConnLabel;
	@FXML
	private TextField newConnection;
	@FXML
	private Button saveNew;

	@FXML
	private RadioButton savedConnectionOption;
	@FXML
	private Label savedConnLabel;
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
	ToggleGroup optionGroup = new ToggleGroup();
	ObservableList<String> names = FXCollections.observableArrayList();

	Pattern macFormatCheck = Pattern.compile("^([0-9A-Fa-f]{2}[:-]){5}([0-9A-Fa-f]{2})$");
	Pattern ipFormatCheck = Pattern.compile("^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." +
			"([01]?\\d\\d?|2[0-4]\\d|25[0-5])$");

	public void setStage (Stage stage) {
		this.stage = stage;
	}

	public void setMain (RemoteMain main) {
		this.main = main;
	}

	@FXML
	private void initialize () {
		newConnectionOption.setToggleGroup(optionGroup);
		newConnectionOption.setUserData("new");

		newConnectionOption.setSelected(true);
		savedConnectionOption.setToggleGroup(optionGroup);
		savedConnectionOption.setUserData("saved");

		optionGroup.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {

			@Override
			public void changed(ObservableValue<? extends Toggle> observable,
					Toggle oldValue, Toggle newValue) {
				if (optionGroup.getSelectedToggle() != null) {
					if (optionGroup.getSelectedToggle().getUserData().equals("new")) {
						savedConnLabel.setOpacity(0.2);
						if (!savedConnections.isDisabled()) {
							savedConnections.setDisable(true);
						}
						savedConnections.setOpacity(0.2);

						newConnLabel.setOpacity(1.0);
						if (newConnection.isDisabled()) {
							newConnection.setDisable(false);
						}
						newConnection.setOpacity(1.0);
						if (saveNew.isDisabled()) {
							saveNew.setDisable(false);
						}
						saveNew.setOpacity(1.0);
					} else {
						newConnLabel.setOpacity(0.2);
						if (!newConnection.isDisabled()) {
							newConnection.setDisable(true);
						}
						newConnection.setOpacity(0.2);
						if (!saveNew.isDisabled()) {
							saveNew.setDisable(true);
						}
						saveNew.setOpacity(0.2);

						savedConnLabel.setOpacity(1.0);
						if (savedConnections.isDisabled()) {
							savedConnections.setDisable(false);
						}
						savedConnections.setOpacity(1.0);
					}
				}
			}
		});

		FileOperations ops = new FileOperations();
		ops.readFromFile();
		if (!ops.connections.isEmpty()) {
			for (int i = 0; i < ops.connections.size(); i++) {
				names.add((String) ops.connections.get(i).get("connName"));
			}
		}

		savedConnections.setItems(names);
		savedConnections.getSelectionModel().select(0);
	}

	@FXML
	private void saveNewButton () {
		FileOperations ops = new FileOperations();
		ops.readFromFile();
		boolean macExists = true;

		Alert invMac = new Alert(AlertType.ERROR);
		invMac.setTitle("Invalid MAC Address");
		invMac.setHeaderText("Check MAC Address");
		//		invMac.setContentText("Please check the MAC address entered. It should be six pairs separated by '-' or ':'"
		//				+ " within the inclusive range of A-F (a-f) or (0-9) per value.");
		invMac.getDialogPane().setContent(new Label("Please check the MAC address entered. It should\nbe six pairs "
				+ "separated by '-' or ':' within the\ninclusive range of A-F (a-f) or (0-9) per value."));

		Alert alreadyMac = new Alert(AlertType.ERROR);
		alreadyMac.setTitle("Redundant MAC Address");
		alreadyMac.setHeaderText("MAC Address Already Saved");
		//		alreadyMac.setContentText("This MAC Address has already been saved. Please remove the saved address or us it instead.");
		alreadyMac.getDialogPane().setContent(new Label("This MAC Address has already been saved.\nPlease remove "
				+ "the saved address or use it instead."));

		String cleanMAC = newConnection.getText().replace("-", ":");

		if (macFormatCheck.matcher(newConnection.getText()).matches()) {

			for (int i = 0; i < names.size(); i++) {
				if (!ops.connections.isEmpty()) {
					for (int j = 0; j < ops.connections.size(); j++) {
						if (ops.connections.get(j).get("macAddress").equals(cleanMAC)) {
							macExists = true;
							alreadyMac.showAndWait();
							break;
						} else {
							macExists = false;
						}
					}
				}

				if (macExists) {
					break;
				}
			}

			if (!macExists) {
				main.SaveMenu (newConnection.getText());
				ops.readFromFile();
				names.clear();
				for (int i = 0; i < ops.connections.size(); i++) {
					names.add((String) ops.connections.get(i).get("connName"));
				}

				savedConnections.setItems(names);
			}
		} else {
			invMac.showAndWait();
		}
	}

	@FXML
	private void wakeButton () {

		Alert invalidIP = new Alert(AlertType.ERROR);
		invalidIP.setTitle("Incorrect IP");
		invalidIP.setHeaderText("IP Gateway is Invalid");
		invalidIP.getDialogPane().setContent(new Label("This IP Gateway address is invalid. Check\n"
				+ "that the IP address is in the format of\n xxx.xxx.xxx.xxx"));

		Alert invMac = new Alert(AlertType.ERROR);
		invMac.setTitle("Invalid MAC Address");
		invMac.setHeaderText("Check MAC Address");
		//		invMac.setContentText("Please check the MAC address entered. It should be six pairs separated by '-' or ':'"
		//				+ " within the inclusive range of A-F (a-f) or (0-9) per value.");
		invMac.getDialogPane().setContent(new Label("Please check the MAC address entered. It should\nbe six pairs "
				+ "separated by '-' or ':' within the\ninclusive range of A-F (a-f) or (0-9) per value."));

		if (ipFormatCheck.matcher(gatewayIP.getText()).matches()) {
			if (optionGroup.getSelectedToggle().getUserData().equals("new")) {
				if (macFormatCheck.matcher(newConnection.getText()).matches()) {
					String ip = gatewayIP.getText();
					String mac = newConnection.getText();
					new WakeTarget(ip, mac);
				} else {
					invMac.showAndWait();
				}
			} else {
				if (savedConnections.getValue() != null && !savedConnections.getValue().isEmpty()) {
					FileOperations ops = new FileOperations();
					ops.readFromFile();
					for (int i = 0; i < ops.connections.size(); i++) {
						if (ops.connections.get(i).get("connName").equals(savedConnections.getValue())) {
							String ip = gatewayIP.getText();
							String mac = ops.connections.get(i).get("macAddress").toString();
							new WakeTarget(ip, mac);
						}
					}
				}
			}
		} else {
			invalidIP.showAndWait();
		}
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
