package views.screen;

import java.io.IOException;

import controller.BaseController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Screen;
import javafx.stage.Stage;

public class BaseScreen extends FXMLScreen{
	protected Stage stage;
	protected Scene scene;
	
	protected BaseScreen prev;
	protected BaseScreen homeScreen;
	protected BaseController controller;

	public BaseScreen(String screenPath) throws IOException {
		super(screenPath);
	}
	
	public Stage getStage() {
		return stage;
	}
	
	public void setStage(Stage stage) {
		this.stage = stage;
	}

	public void show() {
		if (scene==null) {
			scene = new Scene(root);
		}
		if (stage==null) {
			stage = new Stage();
		}
		stage.setScene(scene);
		stage.show();
	}
	
	public BaseScreen getPrev() {
		return prev;
	}
	public void setPrevScreen(BaseScreen prev) {
		this.prev = prev;
	}
	
	public BaseController getController() {
		return controller;
	}
	public void setController(BaseController controller) {
		this.controller = controller;
	}
	
	public BaseScreen getHomeScreen() {
		return this.homeScreen;
	}
	public void setHomeScreen(BaseScreen homeScreen) {
		this.homeScreen = homeScreen;
	}
	
	public void setScreenTitle(String title) {
		stage.setTitle(title);
	}
}