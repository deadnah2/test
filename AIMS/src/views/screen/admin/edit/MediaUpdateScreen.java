package views.screen.admin.edit;

import java.io.IOException;
import java.sql.SQLException;

import controller.AdminMediaController;
import entity.media.Media;
import exception.EmptyFieldsException;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextField;
import javafx.scene.control.Label;
import views.screen.BaseScreen;
import views.screen.popup.PopupScreen;

public class MediaUpdateScreen extends BaseScreen{
	@FXML private Label formTitle;
	@FXML private TextField titleField;
	@FXML private ComboBox<String> categoryField;
	@FXML private TextField valueField;
	@FXML private TextField priceField;
	@FXML private TextField quantityField;
	@FXML private TextField imagePathField;
	@FXML private TextField weightField;
	@FXML private ComboBox<String> isSupportRushField;
	private Media media;

	public MediaUpdateScreen(String screenPath) throws IOException {
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
	private void handleSaveAction() throws SQLException {
		try {		
			this.media.setTitle(titleField.getText());
			this.media.setCategory(categoryField.getSelectionModel().getSelectedItem());
			this.media.setPrice(Integer.parseInt(priceField.getText()));
			this.media.setValue(Integer.parseInt(valueField.getText()));
			this.media.setQuantity(Integer.parseInt(quantityField.getText()));
			this.media.setImageURL(imagePathField.getText());
			this.media.setIsSupportRushShipping(isSupportRushField.getSelectionModel().getSelectedItem().equals("True"));
			this.media.setWeight(Integer.parseInt(weightField.getText()));
			
			this.media.validateInfo();		
			getController().requestUpdateSpecificMedia(this.media ,this);
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
	
	public MediaUpdateScreen setMedia(Media media) {
        this.media = media;
        return this;
    }
	
	public MediaUpdateScreen initScreen() throws IOException {
        try{
            formTitle.setText("Update Media - ID: " + media.getId());
            titleField.setText(media.getTitle());
            categoryField.getSelectionModel().select(media.getCategory());
            priceField.setText(String.valueOf(media.getPrice()));
            valueField.setText(String.valueOf(media.getValue()));
            quantityField.setText(String.valueOf(media.getQuantity()));
            imagePathField.setText(media.getImageURL());
            isSupportRushField.getSelectionModel().select(media.getIsSupportRushShipping()? "True" : "False");
            weightField.setText(String.valueOf(media.getWeight()));
            return this;
        } catch (Exception e){
            e.printStackTrace();
            return this;
        }
    }
}
