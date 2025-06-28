package views.screen.admin.create;

import java.io.IOException;

import controller.AdminMediaController;
import entity.media.category.CD;
import exception.EmptyFieldsException;
import exception.InvalidDateException;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import views.screen.popup.PopupScreen;

public class CdCreateScreen extends SpecificMediaCreateScreen{
		@FXML private TextField artistField;
	    @FXML private DatePicker dateField;
	    @FXML private TextField genreField;
	    @FXML private TextField recordField;
	    @FXML private TextField tracklistField;
	    
	    public CdCreateScreen(String screenPath) throws IOException {
			super(screenPath);
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
				CD cd = new CD(artistField.getText(), recordField.getText(), tracklistField.getText(), genreField.getText(), dateField.getValue());
				cd.validateInfo();
				media.setSpecificMedia(cd);
				getController().createMedia(media);
				homeScreen.show();
			} catch (EmptyFieldsException | InvalidDateException e) {
				try {
					PopupScreen.error(e.getMessage());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			
		}
	   
}
