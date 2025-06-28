package views.screen.item;

import java.io.File;
import java.io.IOException;

import controller.HomeController;
import entity.media.category.Book;
import entity.media.category.DVD;
import exception.MediaUnavailableException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utils.CurrencyFormatter;
import views.screen.home.HomeScreen;
import views.screen.popup.PopupScreen;

public class DvdDetailScreen extends SpecificMediaDetailScreen {
	
    @FXML private Label discType;
    @FXML private Label studio;
    @FXML private Label language;
    @FXML private Label subtitle;
    @FXML private ImageView mediaImage;
    @FXML private Label numMediaInCart;
    @FXML private Label releaseDate;
    @FXML private Label genre;
    
    @FXML private Label updateNumMediaInCart;
	public DvdDetailScreen(String screenPath) throws IOException {
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
    	DVD dvd = (DVD) media.getSpecificMedia();
    	title.setText(media.getTitle());
    	category.setText(media.getCategory());
    	avail.setText(String.valueOf(media.getQuantity()));
    	price.setText(CurrencyFormatter.format(media.getPrice()));
    	
    	discType.setText(dvd.getDiscType());
    	studio.setText(dvd.getStudio());
    	subtitle.setText(dvd.getSubtitle());
    	releaseDate.setText(dvd.getReleaseDate().toString());
    	language.setText(dvd.getLanguage());
    	genre.setText(dvd.getGenre());
    	
    	File file = new File(media.getImageURL());
        Image image = new Image(file.toURI().toString());
        mediaImage.setImage(image);
        super.updateNumMediaInCart();
    }
    
    
    
}
