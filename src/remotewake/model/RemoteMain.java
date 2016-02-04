package remotewake.model;

import java.io.IOException;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;
import remotewake.view.MainMenuController;
import remotewake.view.SaveConnectionController;

public class RemoteMain extends Application {

	Stage primaryStage;
	
	public RemoteMain() {}

	@Override
	public void start (Stage stage) {
		this.primaryStage = stage;
		primaryStage.setTitle("System Remote Wake");

		MainMenu();
	}

	public void MainMenu () {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(RemoteMain.class.getResource("../view/MainMenu.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();

			Scene scene = new Scene(pane);
			primaryStage.setScene(scene);
			
			MainMenuController controller = loader.getController();
			controller.setMain(this);
			controller.setStage(primaryStage);
			
			primaryStage.show();

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void SaveMenu () {
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(RemoteMain.class.getResource("../view/SaveConnection.fxml"));
			AnchorPane pane = (AnchorPane) loader.load();
			
			Stage stage = new Stage();
			stage.setTitle("Saving New Connection");
			stage.initModality(Modality.WINDOW_MODAL);
			stage.initOwner(primaryStage);
			
			Scene scene = new Scene(pane);
			stage.setScene(scene);
			
			SaveConnectionController controller = loader.getController();
			controller.setMain(this);
			controller.setStage(stage);
			
			stage.showAndWait();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
