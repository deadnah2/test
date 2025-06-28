package views.screen.admin;
import java.io.IOException;
import java.sql.SQLException;

import controller.AdminMediaController;
import controller.AdminOrderController;
import entity.media.Media;
import entity.order.Order;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import views.screen.BaseScreen;

public class AdminOrderScreen extends BaseScreen{

    public AdminOrderScreen(String screenPath) throws IOException {
		super(screenPath);
		// TODO Auto-generated constructor stub
	}

	@FXML
    private TableColumn<Order, Order> actionColumn;

    @FXML
    private Button backHome;

    @FXML
    private TableColumn<Order, Integer> idColumn;

    @FXML
    private TableColumn<Order, Boolean> isPaidColumn;

    @FXML
    private TableColumn<Order, Boolean> isRushColumn;

    @FXML
    private Button mediaManage;

    @FXML
    private TableView<Order> mediaTableView;

    @FXML
    private TableColumn<Order, String> nameColumn;

    @FXML
    private Button orderManage;

    @FXML
    private TableColumn<Order, String> phoneColumn;

    @FXML
    private TableColumn<Order, String> provinceColumn;

    @FXML
    private TableColumn<Order, String> statusColumn;

    @FXML
    private TableColumn<Order, Integer> totalCostColumn;
    
    @Override
	public AdminOrderController getController() {
		return (AdminOrderController)super.getController();
	}
    
    @Override
	public void show() {
		setOrderInfo();
		super.show();
	}
    
    @FXML
    void handleLogoutAction(ActionEvent event) {
    	homeScreen.show();
    }

    @FXML
    void handleShowMediaScreen(ActionEvent event) {
    	getController().requestMediaScreen(this);
    }
    
    @FXML
    void handleShowUserScreen() {
    	getController().requestUserScreen(this);
    }
    
    BaseScreen getSelf() {
    	return this;
    }
    
    private void setOrderInfo() {
        idColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("id"));
        nameColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("name"));
        phoneColumn.setCellValueFactory((new PropertyValueFactory<Order, String>("phone")));
        provinceColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("province"));
        statusColumn.setCellValueFactory(new PropertyValueFactory<Order, String>("status"));
        totalCostColumn.setCellValueFactory(new PropertyValueFactory<Order, Integer>("totalCost"));
        isRushColumn.setCellValueFactory(new PropertyValueFactory<Order, Boolean>("isRush"));
        isPaidColumn.setCellValueFactory(new PropertyValueFactory<Order, Boolean >("isPaid"));

        actionColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        actionColumn.setCellFactory(param -> new TableCell<Order, Order>() {
            private final Button confirmButton = new Button("Confirm");
            private final Button rejectButton = new Button("Reject");
            private final Button viewButton = new Button("View detail");
            private final HBox container = new HBox(5, confirmButton, rejectButton, viewButton);

            @Override
            protected void updateItem(Order order, boolean empty) {
                super.updateItem(order, empty);
                if (empty || order == null) {
                    setGraphic(null);
                    return;
                }

                if ("pending".equals(order.getStatus())) {
                    confirmButton.setVisible(true);
                    rejectButton.setVisible(true);
                } else {
                    confirmButton.setVisible(false);
                    rejectButton.setVisible(false);
                }
                viewButton.setVisible(true);

                confirmButton.setOnAction(event -> {
                    try {
                        getController().changeOrderStatusById(order.getId(), "confirm");
                        update(); // Refresh the table view to reflect changes
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });

                rejectButton.setOnAction(event -> {
                    try {
                        getController().changeOrderStatusById(order.getId(), "reject");
                        update(); // Refresh the table view to reflect changes
                    } catch (SQLException e) {
                        e.printStackTrace();
                    }
                });
                
                viewButton.setOnAction(event -> {
                	
                    getController().requestOrderDetailScreen(getSelf(), order);
                    
                });

                setGraphic(container);
            }
        });
        update();   

               
    }
    
    private void update() {
    	try {
            mediaTableView.setItems(getController().getAllOrder());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
