package views.screen.shipping;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import controller.PayOrderController;
import controller.PlaceOrderController;
import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.order.Order;
import entity.shipping.DeliveryInfo;
import exception.AddressNotSupportRushOrderException;
import exception.EmptyFieldsException;
import exception.MediaNotSupportRushOrderException;
import views.screen.BaseScreen;
import views.screen.popup.PopupScreen;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import subsystem.vnpay.VNPayController;
import utils.Config;
import utils.CurrencyFormatter;

public class ShippingScreen extends BaseScreen {
	@FXML private Label pageTitle;

	@FXML private TextField name;

	@FXML private TextField phone;

	@FXML private ComboBox<String> province;

	@FXML private TextField address;

	@FXML private TextField instruction;

	@FXML private RadioButton chooseRushShip;
	
	@FXML private RadioButton chooseNormalShip;

	@FXML private DatePicker time;

	@FXML private TextField rushInstruction;

	@FXML private TextField email;

	@FXML private Label VAT;

	@FXML private Label noVAT;

	@FXML private Label shippingFees;

	@FXML private Label total;

	@FXML private VBox vboxItems;
	
	@FXML private Button btnConfirm;
	
	@FXML private Label lbtime;
	
	@FXML private Label lbrushInstruction;

	private Order order;
    
    public ShippingScreen(String screenPath, Order order) throws IOException {
    	super(screenPath);
        this.order = order;
        
        this.province.getItems().addAll(Config.PROVINCES);
        ToggleGroup shippingToggleGroup = new ToggleGroup();
        chooseRushShip.setToggleGroup(shippingToggleGroup);
        chooseNormalShip.setToggleGroup(shippingToggleGroup);

        // Set default to normal shipping
        chooseNormalShip.setSelected(true);
        hideRushShippingFields();
        
        updateOrderCost();
        updateMediaItems();

        shippingToggleGroup.selectedToggleProperty().addListener((observable, oldValue, newValue) -> {
        	if (oldValue==newValue) return;
            if (newValue == chooseRushShip) {
            	if (province.getValue()==null) {
            		System.out.println("Province must not be empty");
            		chooseNormalShip.setSelected(true);
            		chooseRushShip.setSelected(false);
            		return;
            	}
            	try {
					((PlaceOrderController) controller).validateRushShipping(province.getValue(), order.getMediaList());
					showRushShippingFields();
					updateOrderCost();
					updateMediaItems();
				} catch (AddressNotSupportRushOrderException | MediaNotSupportRushOrderException e) {
					e.printStackTrace();
					try {
						PopupScreen.error(e.getMessage());
					} catch (IOException e1) {
						e1.printStackTrace();
					}  
					chooseNormalShip.setSelected(true);
            		chooseRushShip.setSelected(false);
				}
            } else {
                hideRushShippingFields();
                updateOrderCost();
                updateMediaItems();
            }
        });
        
        province.valueProperty().addListener(new ChangeListener<String>() {
            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
            	if (chooseNormalShip.isSelected()) {
            		updateOrderCost();
            		return;
            	}
            	try {
					((PlaceOrderController) controller).validateRushShipping(province.getValue(), order.getMediaList());
					showRushShippingFields();
				} catch (AddressNotSupportRushOrderException | MediaNotSupportRushOrderException e) {
					e.printStackTrace();
					chooseNormalShip.setSelected(true);
            		chooseRushShip.setSelected(false);
				} finally {
					updateOrderCost();
				}
            }
        });
    }

    private void showRushShippingFields() {
        lbtime.setVisible(true);
        lbrushInstruction.setVisible(true);
        time.setVisible(true);
        rushInstruction.setVisible(true);
    }

    private void hideRushShippingFields() {
        lbtime.setVisible(false);
        lbrushInstruction.setVisible(false);
        time.setVisible(false);
        rushInstruction.setVisible(false);
    }
    private int getfee() {
    	noVAT.setText(CurrencyFormatter.format(order.getCostNoVAT()));
    	VAT.setText(CurrencyFormatter.format(order.getCostVAT()));
    	int fee;
    	if (chooseNormalShip.isSelected()) {
    		fee = order.calculateNormalShippingFee(province.getValue());
    	} else {
    		fee = order.calculateRushShippingFee(province.getValue());
    	}
    	shippingFees.setText(CurrencyFormatter.format(fee));
		int totalAmount = order.calculateTotal(fee);
		total.setText(CurrencyFormatter.format(totalAmount));
		return totalAmount*1000;
    }
    private void updateOrderCost() {
    	noVAT.setText(CurrencyFormatter.format(order.getCostNoVAT()));
    	VAT.setText(CurrencyFormatter.format(order.getCostVAT()));
    	int fee;
    	if (chooseNormalShip.isSelected()) {
    		fee = order.calculateNormalShippingFee(province.getValue());
    	} else {
    		fee = order.calculateRushShippingFee(province.getValue());
    	}
    	shippingFees.setText(CurrencyFormatter.format(fee));
		total.setText(CurrencyFormatter.format(order.calculateTotal(fee)));
    }
    
    private void updateMediaItems() {
    	vboxItems.getChildren().clear();
    	try {
    		for (CartMedia m : order.getMediaList()) {
        		OrderItemScreen orderItemScreen = new OrderItemScreen(Config.ORDER_ITEM_SCREEN_PATH, m, !(chooseNormalShip.isSelected()));
        		vboxItems.getChildren().add(orderItemScreen.getRoot());
        	}
    	} catch(IOException e) {
    		e.printStackTrace();
    	}
    	
    }
    
    @FXML
    void confirmOrder(MouseEvent event) {
    	DeliveryInfo info = new DeliveryInfo(email.getText(), name.getText(), phone.getText(), province.getValue(), address.getText(), 
    			instruction.getText(), !(chooseNormalShip.isSelected()), time.getValue(), rushInstruction.getText());
    	try {
			info.validateInfo();
			order.setInfo(info);
			//System.out.println(order.toString());
			((PlaceOrderController) controller).requestInvoice(order);
			
		} catch (Exception e) {
			try {
				PopupScreen.error(e.getMessage());
			} catch (IOException e1) {
				e1.printStackTrace();
			}  
		}
    }
}
