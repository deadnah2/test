package views.screen.item;

import java.io.File;
import java.io.IOException;

import controller.HomeController;
import entity.media.category.Book;
import entity.media.category.CD;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import utils.CurrencyFormatter;

public class CdDetailScreen extends SpecificMediaDetailScreen{
    @FXML private Label artist;
    @FXML private Label recordLabel;
    @FXML private Label trackList;
    @FXML private Label genre;
    @FXML private Label releaseDate;
    
    @FXML private ImageView mediaImage;
    @FXML private Label numMediaInCart;
    @FXML private Label updateNumMediaInCart;
	public CdDetailScreen(String screenPath) throws IOException {
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
    	CD cd = (CD) media.getSpecificMedia();
    	title.setText(media.getTitle());
    	category.setText(media.getCategory());
    	avail.setText(String.valueOf(media.getQuantity()));
    	price.setText(CurrencyFormatter.format(media.getPrice()));
    	
    	artist.setText(cd.getArtist());
    	recordLabel.setText(cd.getRecordLabel());
    	trackList.setText(cd.getTrackList());
    	releaseDate.setText(cd.getReleaseDate().toString());
    	genre.setText(cd.getGenre());
    	
    	
    	File file = new File(media.getImageURL());
        Image image = new Image(file.toURI().toString());
        mediaImage.setImage(image);
        updateNumMediaInCart();
    }
}
