package views.screen.item;

import java.io.File;
import java.io.IOException;

import controller.HomeController;
import entity.media.category.Book;
import exception.MediaUnavailableException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utils.CurrencyFormatter;
import views.screen.home.HomeScreen;
import views.screen.popup.PopupScreen;

public class BookDetailScreen extends SpecificMediaDetailScreen{
	
//	@FXML private Label title;
//    @FXML private Label avail;
//    @FXML private Label category;
//    @FXML private Label price;
    
    @FXML private Label author;
    @FXML private Label coverType;
    @FXML private Label genre;
    @FXML private Label language;
    @FXML private ImageView mediaImage;
//    @FXML private Label numMediaInCart;
    @FXML private Label pages;
    @FXML private Label publicationDate;
    @FXML private Label publisher;
  
    @FXML private Label updateNumMediaInCart;
    
    public BookDetailScreen(String screenPath) throws IOException {
		super(screenPath);
	}
    
    @Override
    public HomeController getController() {
    	return (HomeController)super.getController();
    }
    
    @Override
    public void show() {
    	setMediaInfo();
    	super.show();
    }
    
    private void setMediaInfo() {
    	if (media==null) return;
    	Book book = (Book) media.getSpecificMedia();
    	title.setText(media.getTitle()); 	
    	category.setText(media.getCategory());
    	avail.setText(String.valueOf(media.getQuantity()));
    	price.setText(CurrencyFormatter.format(media.getPrice()));
    	
    	author.setText(book.getAuthor());
    	coverType.setText(book.getCoverType());
    	publisher.setText(book.getPublisher());
    	publicationDate.setText(book.getPublicationDate().toString());
    	pages.setText(String.valueOf(book.getPages()));
    	language.setText(book.getLanguage());
    	genre.setText(book.getGenre());
    	
    	File file = new File(media.getImageURL());
        Image image = new Image(file.toURI().toString());
        mediaImage.setImage(image);
        super.updateNumMediaInCart();
    }

//    @FXML
//    void handleAddToCart() {
//    	try {
//        	if (media.getQuantity() <= 0) throw new MediaUnavailableException("Not enough " + media.getTitle());                
//        	getController().addMediaToCart(cart, media);
//        	avail.setText(String.valueOf(media.getQuantity()));
//        	updateNumMediaInCart();
//            PopupScreen.success("The media " + media.getTitle() + " added to Cart");
//        } catch (MediaUnavailableException e) {
//            try {
//				PopupScreen.error(e.getMessage());
//			} catch (IOException e1) {
//				e1.printStackTrace();
//			}                
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//    }
//
//    @FXML
//    void handleBackToHome() {
//    	((HomeScreen)homeScreen).showWithoutFetching();
//    }
//    
//    @FXML
//    private void handleCartRequest() {
//    	getController().requestToCart(cart, this);
//    }
//
//    private void updateNumMediaInCart(){
//    	numMediaInCart.setText(String.valueOf(cart.getMediaList().size()) + " media");
//    }

}
