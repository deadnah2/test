package views.screen.admin.create;

import java.io.IOException;

import controller.AdminMediaController;
import entity.media.Media;
import exception.EmptyFieldsException;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import views.screen.BaseScreen;
import views.screen.popup.PopupScreen;

public class MediaCreateScreen extends BaseScreen{
	@FXML private TextField titleField;
	@FXML private ComboBox<String> categoryField;
	@FXML private TextField valueField;
	@FXML private TextField priceField;
	@FXML private TextField quantityField;
	@FXML private TextField imagePathField;
	@FXML private TextField weightField;
	@FXML private ComboBox<String> isSupportRushField;

	public MediaCreateScreen(String screenPath) throws IOException {
		super(screenPath);
		
		categoryField.getItems().addAll(Media.getCategoryList());
        categoryField.getSelectionModel().selectFirst();
        isSupportRushField.getItems().addAll("True", "False");
        isSupportRushField.getSelectionModel().select(0);
	}
	
	@Override
	public AdminMediaController getController() {
		return (AdminMediaController)super.getController();
	}
	
	@FXML
	private void handleCancelAction() {
		prev.show();
	}
	
	@FXML
	private void handleSaveAction() {
		try {
			Media media = new Media(titleField.getText(), categoryField.getSelectionModel().getSelectedItem(), Integer.parseInt(priceField.getText()),
					Integer.parseInt(valueField.getText()), Integer.parseInt(quantityField.getText()), imagePathField.getText(),
					isSupportRushField.getSelectionModel().getSelectedItem().equals("True"), Integer.parseInt(weightField.getText()));
			media.validateInfo();
			getController().requestCreateSpecificMedia(media, this);
		} catch (NumberFormatException e) {
			try {
				PopupScreen.error("Price, value, quantity and weight must be number!");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (EmptyFieldsException e) {
			try {
				PopupScreen.error(e.getMessage());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		} 
		
	}
}
