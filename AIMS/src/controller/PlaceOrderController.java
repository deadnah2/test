package controller;

import java.io.IOException;
import java.util.List;

import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.order.Order;
import entity.shipping.DeliveryInfo;
import exception.AddressNotSupportRushOrderException;
import exception.MediaNotSupportRushOrderException;
import exception.MediaUnavailableException;
import exception.EmptyCartException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import utils.Config;
import views.screen.BaseScreen;
import views.screen.invoice.InvoiceScreen;
import views.screen.shipping.ShippingScreen;

public class PlaceOrderController extends BaseController{
	private BaseScreen homeScreen;
	 public void placeOrder(Cart cart, BaseScreen prevScreen) throws MediaUnavailableException, EmptyCartException{
	        
	        cart.checkProductAvai();
	        if (cart.getMediaList().isEmpty()) throw new EmptyCartException("Your cart is empty!");
			try {
				Order order = new Order(cart.getMediaList(), cart.getCostNoVAT(), cart.getCostVAT());
				BaseScreen shippingScreen = new ShippingScreen(Config.SHIPPING_SCREEN_PATH, order);
				shippingScreen.setPrevScreen(prevScreen);
				homeScreen = prevScreen.getHomeScreen();
				shippingScreen.setStage(prevScreen.getStage());
		        shippingScreen.setHomeScreen(prevScreen.getHomeScreen());
		        shippingScreen.setScreenTitle("Shipping screen");
		        shippingScreen.setController(this);
		        
		        shippingScreen.show();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	        
	    }
	
	public void validateRushShipping(String province, List<CartMedia> cartMediaList) throws AddressNotSupportRushOrderException, MediaNotSupportRushOrderException{
		if (!DeliveryInfo.validateRushAddress(province)) throw new AddressNotSupportRushOrderException(province + " not support rush order");
		if (Order.getNumberOfRushShippingProduct(cartMediaList)==0) throw new MediaNotSupportRushOrderException("No products support rush order");
		
    }
	
	
	public void requestInvoice(Order order) {
        try {
            BaseScreen invoiceScreen = new InvoiceScreen(Config.INVOICE_SCREEN_PATH, order);
            invoiceScreen.setStage(homeScreen.getStage());
            invoiceScreen.setHomeScreen(homeScreen);
            invoiceScreen.setScreenTitle("Invoice screen");
	        invoiceScreen.setController(this);
	        invoiceScreen.show();
        } catch (Exception e) {
            e.printStackTrace(); 
        }
    }

}
