package views.screen.item;

import java.io.IOException;

import controller.HomeController;
import entity.cart.Cart;
import entity.media.Media;
import exception.MediaUnavailableException;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import views.screen.BaseScreen;
import views.screen.home.HomeScreen;
import views.screen.popup.PopupScreen;

public class SpecificMediaDetailScreen extends BaseScreen{
	protected Media media;
	protected Cart cart;
	
	@FXML protected Label title;
    @FXML protected Label avail;
    @FXML protected Label category;
    @FXML protected Label price;
    
    
    @FXML private Label numMediaInCart;
	
	public SpecificMediaDetailScreen(String screenPath) throws IOException {
		super(screenPath);
	}
	
	public Media getMedia() {
		return media;
	}
	
	public void setMedia(Media media) {
		this.media = media;
	}

	public Cart getCart() {
		return cart;
	}

	public void setCart(Cart cart) {
		this.cart = cart;
	}
	
	public HomeController getController() {
    	return (HomeController)super.getController();
    }
	
	@FXML
    void handleAddToCart() {
    	try {
        	if (media.getQuantity() <= 0) throw new MediaUnavailableException("Not enough " + media.getTitle());                
        	getController().addMediaToCart(cart, media);
        	avail.setText(String.valueOf(media.getQuantity()));
        	updateNumMediaInCart();
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

    @FXML
    void handleBackToHome() {
    	((HomeScreen)homeScreen).showWithoutFetching();
    }
    
    @FXML
    private void handleCartRequest() {
    	getController().requestToCart(cart, this);
    }

    protected void updateNumMediaInCart(){
    	numMediaInCart.setText(String.valueOf(cart.getMediaList().size()) + " media");
    }
}
