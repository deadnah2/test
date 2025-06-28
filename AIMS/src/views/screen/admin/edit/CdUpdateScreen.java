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
import entity.media.category.CD;
import exception.EmptyFieldsException;
import exception.InvalidDateException;

public class CdUpdateScreen extends SpecificMediaUpdateScreen {
	@FXML private Label formTitle;
	@FXML private TextField artistField;
    @FXML private DatePicker dateField;
    @FXML private TextField genreField;
    @FXML private TextField recordField;
    @FXML private TextField tracklistField;
	private CD cd;
	
	public CdUpdateScreen(String screenPath) throws IOException {
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
			CD updatedCd = new CD(artistField.getText(), recordField.getText(), 
					tracklistField.getText(), genreField.getText(), dateField.getValue());
			updatedCd.validateInfo();
			updatedCd.setMediaId(media.getId());
			media.setSpecificMedia(updatedCd);
			getController().updateMedia(media);
			prev.getPrev().show();
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
	
	public CdUpdateScreen setMedia(Media media) {
		this.media = media;
        if (media.getSpecificMedia() instanceof CD) {
            this.cd = (CD) media.getSpecificMedia();
            // Debug: In ra để kiểm tra
            System.out.println("Media ID: " + media.getId());
            System.out.println("Book ID: " + (cd != null ? cd.getId() : "null"));
            System.out.println("Book Media ID: " + (cd != null ? cd.getMediaId() : "null"));
        }
        return this;
    }
	
	public CdUpdateScreen initScreen() {
        try {
            if (cd != null) {
                formTitle.setText("Update CD - ID: " + cd.getId());
                artistField.setText(cd.getArtist());
                dateField.setValue(cd.getReleaseDate());
                genreField.setText(cd.getGenre());
                recordField.setText(cd.getRecordLabel());
                tracklistField.setText(cd.getTrackList());
            }
            return this;
        } catch (Exception e) {
            e.printStackTrace();
            return this;
        }
    }
}
