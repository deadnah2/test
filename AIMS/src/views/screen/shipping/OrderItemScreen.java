package views.screen.shipping;

import java.io.File;
import java.io.IOException;

import entity.cart.CartMedia;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import utils.CurrencyFormatter;
import views.screen.FXMLScreen;

public class OrderItemScreen extends FXMLScreen{

	    @FXML
	    private ImageView image;

	    @FXML
	    private Label title;

	    @FXML
	    private Label numOfProd;

	    @FXML
	    private Label price;
	    
	    @FXML
	    private Label rushEligibility;
	    
	    private CartMedia orderMedia;
		private boolean isRush;
	    
	    public OrderItemScreen(String screenPath, CartMedia orderMedia, boolean isRush) throws IOException {
	    	super(screenPath);
			this.orderMedia = orderMedia;
			this.isRush = isRush;
			setMediaInfo();
		}
	    	    
	    private void setMediaInfo() {
	    	title.setText(orderMedia.getMedia().getTitle());
	        price.setText(CurrencyFormatter.format(orderMedia.getPrice()));
	        numOfProd.setText("x" + orderMedia.getQuantity());
	        
	        File file = new File(orderMedia.getMedia().getImageURL());
			Image im = new Image(file.toURI().toString());
			image.setImage(im);
			image.setPreserveRatio(false);
			image.setFitHeight(90);
			image.setFitWidth(83);
			
			rushEligibility.setVisible(isRush && orderMedia.getMedia().getIsSupportRushShipping());
	    }
}
