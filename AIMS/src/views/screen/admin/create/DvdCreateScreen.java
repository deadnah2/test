package views.screen.admin.create;

import java.io.IOException;

import controller.AdminMediaController;
import entity.media.category.DVD;
import exception.EmptyFieldsException;
import exception.InvalidDateException;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import views.screen.popup.PopupScreen;

public class DvdCreateScreen extends SpecificMediaCreateScreen{
	@FXML private DatePicker dateField;
    @FXML private ComboBox<String> discTypeField;
    @FXML private TextField genreField;
    @FXML private TextField languageField;
    @FXML private TextField lengthField;
    @FXML private TextField studioField;
    @FXML private TextField substitleField;

	public DvdCreateScreen(String screenPath) throws IOException {
		super(screenPath);
		discTypeField.getItems().addAll("Blu-ray", "HD-DVD");
        discTypeField.getSelectionModel().select(0);
	}
	
	@Override
	public AdminMediaController getController() {
		return (AdminMediaController)super.getController();
	}
	
	@Override
    @FXML
    public void handleCancelAction() {
    	prev.show();
    }
	
	@Override
    @FXML
    public void handleSaveAction() {
    	try {
			DVD dvd= new DVD(discTypeField.getSelectionModel().getSelectedItem(), Integer.parseInt(lengthField.getText()), studioField.getText(),
							languageField.getText(), substitleField.getText(), dateField.getValue(), genreField.getText());
			dvd.validateInfo();
			media.setSpecificMedia(dvd);
			getController().createMedia(media);
			homeScreen.show();
		} catch (NumberFormatException e) {
			try {
				PopupScreen.error("Length must be number!");
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		} catch (EmptyFieldsException | InvalidDateException e) {
			try {
				PopupScreen.error(e.getMessage());
			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
    }

}
