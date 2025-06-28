package views.screen.admin.create;

import java.io.IOException;

import controller.AdminMediaController;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import views.screen.popup.PopupScreen;
import entity.media.category.Book;
import exception.EmptyFieldsException;
import exception.InvalidDateException;

public class BookCreateScreen extends SpecificMediaCreateScreen {
	@FXML private TextField authorField;
	@FXML private TextField publisherField;
	@FXML private TextField pagesField;
	@FXML private TextField genreField;
	@FXML private TextField languageField;
	@FXML private ComboBox<String> coverField;
	@FXML private DatePicker dateField;
	
	
	public BookCreateScreen(String screenPath) throws IOException {
		super(screenPath);
		coverField.getItems().addAll("Paperback", "Hardcover");
        coverField.getSelectionModel().select(0);
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
			Book book = new Book(authorField.getText(), publisherField.getText(), coverField.getSelectionModel().getSelectedItem(), dateField.getValue(),
								Integer.parseInt(pagesField.getText()), genreField.getText(), languageField.getText());
			book.validateInfo();
			media.setSpecificMedia(book);
			getController().createMedia(media);
			homeScreen.show();
		} catch (NumberFormatException e) {
			try {
				PopupScreen.error("Pages must be number!");
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
