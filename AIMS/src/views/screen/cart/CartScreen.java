package views.screen.cart;


import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ResourceBundle;

import controller.PlaceOrderController;
import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.media.Media;
import exception.EmptyCartException;
import exception.MediaUnavailableException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import utils.Config;
import utils.CurrencyFormatter;
import views.screen.BaseScreen;
import views.screen.home.HomeScreen;
import views.screen.popup.PopupScreen;
import views.screen.shipping.ShippingScreen;

public class CartScreen extends BaseScreen{
	
	@FXML private ImageView aimsImage;

	@FXML private Label pageTitle;

	@FXML VBox itemList;

	@FXML private Label labelAmount;

	@FXML private Label labelSubtotal;

	@FXML private Label labelVAT;

	@FXML private Button btnPlaceOrder;
	
	private Cart cart;
	
	public CartScreen(String screenPath, Cart cart) throws IOException {
		super(screenPath);
		this.cart = cart;
		
		updateCart();
	}
	
	@FXML
	private void handlePlaceOrder() {
		System.out.println("place order");
		
		PlaceOrderController placeOrderController = new PlaceOrderController();
		try {
			placeOrderController.placeOrder(this.cart, this);
		} catch (MediaUnavailableException | EmptyCartException e) {
			try {
				PopupScreen.error(e.getMessage());
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}
	
	public void updateCart() {
		itemList.getChildren().clear();
		
		try {
			for (CartMedia cm : cart.getMediaList()) {
				CartItemScreen itemScreen = new CartItemScreen(Config.CART_ITEM_SCREEN_PATH, this, cm);

				// add spinner
				itemList.getChildren().add(itemScreen.getRoot());
			}
			updateCartCost();
		} catch (IOException e) {
            e.printStackTrace();
        }		
	}
	
	public void updateCartCost() {
		cart.updateCost();
		
		labelSubtotal.setText(CurrencyFormatter.format(cart.getCostNoVAT()));
		labelVAT.setText(CurrencyFormatter.format(cart.calculateVAT()));
		labelAmount.setText(CurrencyFormatter.format(cart.getCostVAT()));
	}
		
	public Cart getCart() {
		return this.cart;
	}
	
	@FXML
	private void handleBackToHome() {
		System.out.println("Hello");
		((HomeScreen)homeScreen).showWithoutFetching();
	}
	
}
