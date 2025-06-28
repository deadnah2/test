package views.screen.admin;

import java.io.IOException;
import java.sql.SQLException;

import controller.AdminMediaController;
import controller.AdminOrderController;
import entity.order.Order;
import entity.order.OrderMedia;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import views.screen.BaseScreen;

public class AdminOrderDetailScreen extends BaseScreen {

    public AdminOrderDetailScreen(String screenPath, Order order) throws IOException {
		super(screenPath);
		this.order = order;
		System.out.println(order.toString());
	}
    private Order order;

	@FXML
    private Label addressField;

    @FXML
    private Button backHome;

    @FXML
    private Button cancelButton;

    @FXML
    private Label deliveryDate;

    @FXML
    private Label deliveryInstruction;

    @FXML
    private Button mediaManage;

    @FXML
    private Label nameField;

    @FXML
    private Label normalInstruction;

    @FXML
    private Button orderManage;

    @FXML
    private TableColumn<OrderMedia, Integer> orderMediaId;

    @FXML
    private TableColumn<OrderMedia, String> orderMediaImage;

    @FXML
    private TableColumn<OrderMedia, Integer> orderMediaPrice;

    @FXML
    private TableColumn<OrderMedia, Integer> orderMediaQuantity;

    @FXML
    private TableView<OrderMedia> orderMediaTable;

    @FXML
    private TableColumn<OrderMedia, String> orderMediaTitle;

    @FXML
    private TableColumn<OrderMedia, Boolean> orderMediaIsRush;

    @FXML
    private Label orderStatus;


    @FXML
    private Label phoneField;

    @FXML
    private Label provinceField;

    @FXML
    private Label shipTypeField;

    @FXML
    private Label shippingFee;

    @FXML
    private Label totalCost;

    @FXML
    private Label totalPrice;

    @Override
	public void show() {
		setInfo();
		super.show();
	}
    
    void setOrderMediaInfo() {
    	orderMediaId.setCellValueFactory(new PropertyValueFactory<OrderMedia, Integer>("id"));
        orderMediaTitle.setCellValueFactory(new PropertyValueFactory<OrderMedia, String>("title"));
        orderMediaPrice.setCellValueFactory((new PropertyValueFactory<OrderMedia, Integer>("price")));
        orderMediaQuantity.setCellValueFactory(new PropertyValueFactory<OrderMedia, Integer>("quantity"));
        orderMediaIsRush.setCellValueFactory(new PropertyValueFactory<OrderMedia, Boolean>("isRush"));
        orderMediaImage.setCellValueFactory(new PropertyValueFactory<OrderMedia, String >("imageURL"));

    }
    
    void setInfo() {
    	nameField.setText(order.getName());
    	phoneField.setText(order.getPhone());
    	addressField.setText(order.getAddress());
    	provinceField.setText(order.getProvince());
    	if (order.getIsRush()) {
    	shipTypeField.setText("Rush");
    	}
    	else {
    		shipTypeField.setText("Normal");
    	}
    	normalInstruction.setText(order.getInstruction());
    	deliveryInstruction.setText(order.getRushInstruction());
    	totalPrice.setText(Integer.toString(order.getCostVAT()));
    	orderStatus.setText(order.getStatus());
    	shippingFee.setText(Integer.toString(order.getShippingFee()));
    	totalCost.setText(Integer.toString(order.getTotalCost()));
    	setOrderMediaInfo();
    	update();
    }
    
    @Override
	public AdminOrderController getController() {
		return (AdminOrderController)super.getController();
	}
    private void update() {
    	try {
            orderMediaTable.setItems(getController().getAllOrderMedia(order.getId()));
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    @FXML
    void handleRequestBack(ActionEvent event) {
    	prev.show();
    }
}
