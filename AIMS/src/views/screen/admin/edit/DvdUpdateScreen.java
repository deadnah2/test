package views.screen.admin.edit;

import java.io.IOException;
import java.sql.SQLException;

import controller.AdminMediaController;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import views.screen.popup.PopupScreen;
import entity.media.Media;
import entity.media.category.DVD;
import exception.EmptyFieldsException;
import exception.InvalidDateException;

public class DvdUpdateScreen extends SpecificMediaUpdateScreen {
	@FXML private Label formTitle;
	@FXML private DatePicker dateField;
    @FXML private ComboBox<String> discTypeField;
    @FXML private TextField genreField;
    @FXML private TextField languageField;
    @FXML private TextField lengthField;
    @FXML private TextField studioField;
    @FXML private TextField substitleField;
	private DVD dvd;
	
	public DvdUpdateScreen(String screenPath) throws IOException {
		super(screenPath);
		discTypeField.getItems().addAll("Blu-ray", "HD-DVD");
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
			DVD updatedDVD = new DVD(discTypeField.getSelectionModel().getSelectedItem(), Integer.parseInt(lengthField.getText()), studioField.getText(),
					languageField.getText(), substitleField.getText(), dateField.getValue(), genreField.getText());
			updatedDVD.validateInfo();
			updatedDVD.setMediaId(media.getId());
			media.setSpecificMedia(updatedDVD);
			getController().updateMedia(media);
			prev.getPrev().show();
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public DvdUpdateScreen setMedia(Media media) {
		this.media = media;
        if (media.getSpecificMedia() instanceof DVD) {
            this.dvd = (DVD) media.getSpecificMedia();
            // Debug:
            System.out.println("Media ID: " + media.getId());
            System.out.println("DVD ID: " + (dvd != null ? dvd.getId() : "null"));
            System.out.println("DVD Media ID: " + (dvd != null ? dvd.getMediaId() : "null"));
        }
        return this;
    }
	
	public DvdUpdateScreen initScreen() {
        try {
            if (dvd != null) {
                formTitle.setText("Update DVD - ID: " + dvd.getId());
                dateField.setValue(dvd.getReleaseDate());
                discTypeField.getSelectionModel().select(dvd.getDiscType());
                genreField.setText(dvd.getGenre());
                languageField.setText(dvd.getLanguage());
                lengthField.setText(String.valueOf(dvd.getRuntime()));
                studioField.setText(dvd.getStudio());
                substitleField.setText(dvd.getSubtitle());
            }
            return this;
        } catch (Exception e) {
            e.printStackTrace();
            return this;
        }
    }
}
