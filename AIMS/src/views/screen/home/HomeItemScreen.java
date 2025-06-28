package views.screen.home;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;

import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.media.Media;
import exception.MediaUnavailableException;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import utils.Config;
import utils.CurrencyFormatter;
import views.screen.FXMLScreen;
import views.screen.popup.PopupScreen;

public class HomeItemScreen extends FXMLScreen{
	@FXML
    protected ImageView mediaImage;

    @FXML
    protected Label title;

    @FXML
    protected Label price;
    
    @FXML
    protected Label category;

    @FXML
    protected Label avail;

    @FXML
    protected Button addToCartBtn;
    
    private Media media;
    private HomeScreen homeScreen;
    private Cart cart;

    public HomeItemScreen(String screenPath, Media media, HomeScreen homeScreen, Cart cart) throws SQLException, IOException{
        super(screenPath);
        this.media = media;
        this.homeScreen = homeScreen;
        this.cart = cart;
        setMediaInfo();
    }
    
    @FXML
    private void handleAddToCart() {
    	try {
        	if (media.getQuantity() <= 0) throw new MediaUnavailableException("Not enough " + media.getTitle());                
        	homeScreen.getController().addMediaToCart(cart, media);
        	homeScreen.updateNumMediaInCart();
            avail.setText(String.valueOf(media.getQuantity()));
            PopupScreen.success("The media " + media.getTitle() + " added to Cart");
        } catch (MediaUnavailableException e) {
            try {
				PopupScreen.error(e.getMessage());
			} catch (IOException e1) {
				e1.printStackTrace();
			}                
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void setMediaInfo(){
        File file = new File(media.getImageURL());
        Image image = new Image(file.toURI().toString());
        mediaImage.setFitHeight(160);
        mediaImage.setFitWidth(152);
        mediaImage.setImage(image);
        
        //System.out.println(media.toString());
        title.setText(media.getTitle());
        price.setText(CurrencyFormatter.format(media.getPrice()));
        category.setText(media.getCategory());
        avail.setText(String.valueOf(media.getQuantity()));
    }
    
    public void setOnClick(EventHandler<MouseEvent> eventHandler) {
        root.setOnMouseClicked(eventHandler);
    }
}
