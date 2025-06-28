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
import entity.media.category.Book;
import exception.EmptyFieldsException;
import exception.InvalidDateException;

public class BookUpdateScreen extends SpecificMediaUpdateScreen {
	@FXML private Label formTitle;
	@FXML private TextField authorField;
	@FXML private TextField publisherField;
	@FXML private TextField pagesField;
	@FXML private TextField genreField;
	@FXML private TextField languageField;
	@FXML private ComboBox<String> coverField;
	@FXML private DatePicker dateField;
	private Book book;
	
	public BookUpdateScreen(String screenPath) throws IOException {
		super(screenPath);
		coverField.getItems().addAll("Paperback", "Hardcover");
	}
	
	@Override
	public AdminMediaController getController() {
		return (AdminMediaController)super.getController();
	}
	
	@Override
	@FXML
	public  void handleCancelAction() {
		prev.show();
	}
	
	@Override
	@FXML
	public  void handleSaveAction() {
		try {
			Book updatedBook = new Book(authorField.getText(), publisherField.getText(), coverField.getSelectionModel().getSelectedItem(), dateField.getValue(),
								Integer.parseInt(pagesField.getText()), genreField.getText(), languageField.getText());
			updatedBook.validateInfo();
			updatedBook.setMediaId(media.getId());
			media.setSpecificMedia(updatedBook);
			getController().updateMedia(media);
			prev.getPrev().show();
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public BookUpdateScreen setMedia(Media media) {
		this.media = media;
        if (media.getSpecificMedia() instanceof Book) {
            this.book = (Book) media.getSpecificMedia();
            // Debug: In ra để kiểm tra
            System.out.println("Media ID: " + media.getId());
            System.out.println("Book ID: " + (book != null ? book.getId() : "null"));
            System.out.println("Book Media ID: " + (book != null ? book.getMediaId() : "null"));
        }
        return this;
    }
	
	public BookUpdateScreen initScreen() {
        try {
            if (book != null) {
                formTitle.setText("Update Book - ID: " + book.getId());
                authorField.setText(book.getAuthor());
                publisherField.setText(book.getPublisher());
                pagesField.setText(String.valueOf(book.getPages()));
                genreField.setText(book.getGenre());
                languageField.setText(book.getLanguage());
                coverField.getSelectionModel().select(book.getCoverType());
                dateField.setValue(book.getPublicationDate());
            }
            return this;
        } catch (Exception e) {
            e.printStackTrace();
            return this;
        }
    }
}
