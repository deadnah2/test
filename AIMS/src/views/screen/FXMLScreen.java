package views.screen;

import java.io.IOException;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.stage.Stage;

public class FXMLScreen {
	protected Parent root;

	public FXMLScreen(String screenPath) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(screenPath));
		loader.setController(this);
		this.root = loader.load();
	}
	
	public Parent getRoot() {
		return this.root;
	}
}
