package views.screen.invoice;

import java.io.IOException;
import views.screen.invoice.InvoiceItemScreen;
import java.time.format.DateTimeFormatter;

import controller.PayOrderController;
import entity.cart.CartMedia;
import entity.order.Order;
import views.screen.BaseScreen;
import views.screen.shipping.OrderItemScreen;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Config;
import utils.CurrencyFormatter;

public class InvoiceScreen extends BaseScreen{

    @FXML
    private Label labelAddress;

    @FXML
    private Label labelProvince;

    @FXML
    private Label labelInstruction;

    @FXML
    private Label labelName;

    @FXML
    private Label labelPhone;

    @FXML
    private Label labelShippingFee;

    @FXML
    private Label labelSubTotal;

    @FXML
    private Label labelTotal;
    
    @FXML
    private Label labelTime;
    
    @FXML
    private Label labelRushInstruction;
    
    @FXML
    private Label labelTimeKey;
    
    @FXML
    private Label labelRushInstructionKey;

    @FXML
    private VBox viewItems;
    
	private Order order;
	
	public InvoiceScreen(String screenPath, Order order) throws IOException {
		super(screenPath);
		this.order = order;
		setInvoiceInfo();
		updateMediaItems();	
		
	}
	
	private void updateMediaItems() {
    	viewItems.getChildren().clear();
    	try {
    		for (CartMedia m : order.getMediaList()) {
        		InvoiceItemScreen invoiceItemScreen = new InvoiceItemScreen(Config.INVOICE_ITEM_SCREEN_PATH, m, order.getInfo().isRush());
        		viewItems.getChildren().add(invoiceItemScreen.getRoot());
        	}
    	} catch(IOException e) {
    		e.printStackTrace();
    	}
    	
    }

    private void setInvoiceInfo() {
		labelName.setText(order.getInfo().getName());
        labelAddress.setText(order.getInfo().getAddress());
        labelProvince.setText(order.getInfo().getProvince());
        labelPhone.setText(order.getInfo().getPhone());
        labelInstruction.setText(order.getInfo().getInstruction());
        labelSubTotal.setText(CurrencyFormatter.format(order.getCostVAT()));
        labelShippingFee.setText(CurrencyFormatter.format(order.getShippingFee()));
        labelTotal.setText(CurrencyFormatter.format(order.getTotalCost()));
        if (order.getInfo().isRush()) {
        	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        	labelTime.setText(order.getInfo().getRushTime().format(formatter));
        	labelRushInstruction.setText(order.getInfo().getRushInstruction());
        	showRushShippingFields();
        } else {
        	hideRushShippingFields();
        } 
    }
    
    private void showRushShippingFields() {
        labelTime.setVisible(true);
        labelRushInstruction.setVisible(true);
        labelTimeKey.setVisible(true);
        labelRushInstructionKey.setVisible(true);
    }
    
    private void hideRushShippingFields() {
        labelTime.setVisible(false);
        labelRushInstruction.setVisible(false);
        labelTimeKey.setVisible(false);
        labelRushInstructionKey.setVisible(false);
    }
	@FXML
    void confirmInvoice(ActionEvent event) {
		PayOrderController payOrderController = new PayOrderController();
		payOrderController.payOrder(order, this.homeScreen);
    }

}
