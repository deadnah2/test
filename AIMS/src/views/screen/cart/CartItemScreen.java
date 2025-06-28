package views.screen.cart;

import java.io.File;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Arrays;
import java.util.logging.Logger;

import entity.cart.Cart;
import entity.cart.CartMedia;
import exception.MediaUnavailableException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.Spinner;
import javafx.scene.control.SpinnerValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import utils.CurrencyFormatter;
import views.screen.FXMLScreen;
import views.screen.popup.PopupScreen;


public class CartItemScreen extends FXMLScreen{	
	@FXML
	protected HBox hboxMedia;

	@FXML
	protected ImageView image;

	@FXML
	protected VBox description;

	@FXML
	protected Label labelOutOfStock;

	@FXML
	protected VBox spinnerFX;

	@FXML
	protected Label title;

	@FXML
	protected Label price;

	@FXML
	protected Label currency;

	@FXML
	protected Button btnDelete;
	
	private CartMedia cartMedia;
	private Spinner<Integer> spinner;
	private CartScreen cartScreen;

	public CartItemScreen(String screenPath, CartScreen cartScreen, CartMedia cartMedia) throws IOException {
		super(screenPath);		
		this.cartScreen = cartScreen;
		this.cartMedia = cartMedia;
		hboxMedia.setAlignment(Pos.CENTER);
		setMediaInfo();
	}
	
	private void setMediaInfo() {
		title.setText(cartMedia.getMedia().getTitle());
		price.setText(CurrencyFormatter.format(cartMedia.calculateTotal()));
		File file = new File(cartMedia.getMedia().getImageURL());
		Image im = new Image(file.toURI().toString());
		image.setImage(im);
		image.setPreserveRatio(false);
		image.setFitHeight(110);
		image.setFitWidth(92);

		// add delete button
		btnDelete.setOnMouseClicked(e -> {
			cartScreen.getCart().removeCartMedia(cartMedia); // update user cart
			cartScreen.updateCart(); // re-display user cart
		});

		initializeSpinner();
	}

	private void initializeSpinner(){
		SpinnerValueFactory<Integer> valueFactory = new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 100, cartMedia.getQuantity());
		spinner = new Spinner<Integer>(valueFactory);
		spinner.setOnMouseClicked( event -> {
			int numOfProd = this.spinner.getValue();
			int difference = numOfProd - cartMedia.getQuantity();
			int remainQuantity = cartMedia.getMedia().getQuantity();
			try {
				if (difference > remainQuantity) throw new MediaUnavailableException("Not enough " + title.getText() + " in stock");
				// update quantity of cartMedia
				cartMedia.setQuantity(numOfProd);
				//update avail quantity of media
				cartMedia.getMedia().setQuantity(remainQuantity - difference);
				// update the total of mediaCart
				price.setText(CurrencyFormatter.format(cartMedia.calculateTotal()));
				// update subtotal and amount of Cart
				cartScreen.updateCartCost();
			} catch (MediaUnavailableException e) {
				
	            try {
	            	spinner.getValueFactory().setValue(cartMedia.getQuantity());;
					PopupScreen.error(e.getMessage());
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}                		
		});
		spinnerFX.setAlignment(Pos.CENTER);
		spinnerFX.getChildren().add(this.spinner);
	}
}

