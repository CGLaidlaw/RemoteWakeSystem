package remotewake.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import remotewake.view.MainMenuController;

public class FileOperations {

	private String file = System.getProperty("user.dir");
	private String fullPath = file + "/SavedConnections.txt";
	public ArrayList<JSONObject> connections = new ArrayList<JSONObject>();

	public void saveToFile (JSONObject obj) {

		try (FileWriter writer = new FileWriter(fullPath, true)) {
			if (new File(fullPath).exists()) {
				writer.write(obj.toJSONString() + System.lineSeparator());
				connections.add(obj);
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void readFromFile () {
		JSONParser parser = new JSONParser();

		try {
			BufferedReader br = new BufferedReader(new FileReader(new File(fullPath)));
			String output;
			while ((output = br.readLine()) != null) {				
				Object obj = parser.parse(output);
				JSONObject result = (JSONObject) obj;
				connections.add(result);
			}
			br.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
