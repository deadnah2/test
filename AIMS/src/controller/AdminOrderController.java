package controller;

import java.io.IOException;
import java.sql.SQLException;

import dao.DAOFactory;
import dao.IMediaDAO;
import dao.IOrderDAO;
import entity.order.Order;
import entity.order.OrderMedia;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.Config;
import views.screen.BaseScreen;
import views.screen.admin.AdminMediaScreen;
import views.screen.admin.AdminOrderDetailScreen;
import views.screen.admin.AdminOrderScreen;
import views.screen.admin.AdminUserScreen;

public class AdminOrderController extends BaseController{
	private IOrderDAO orderDAO = DAOFactory.getInstance().getOrderDAO();
	
	public AdminOrderController() {
		// TODO Auto-generated constructor stub
	}
	
	public void requestMediaScreen(BaseScreen prevScreen) {
		try {
			AdminMediaScreen adminMediaScreen = new AdminMediaScreen(Config.ADMIN_MEDIA_SCREEN_PATH);
			adminMediaScreen.setStage(prevScreen.getStage());
			adminMediaScreen.setController(new AdminMediaController());
			adminMediaScreen.setHomeScreen(prevScreen.getHomeScreen());
			adminMediaScreen.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ObservableList<Order> getAllOrder() throws SQLException {
		return FXCollections.observableArrayList(orderDAO.getAllOrder());
	}
	
	public ObservableList<OrderMedia> getAllOrderMedia(int id) throws SQLException {
		return FXCollections.observableArrayList(orderDAO.getOrderMediaByOrderId(id));
	}
	
	public void changeOrderStatusById(int id, String status ) throws SQLException {
		orderDAO.changeOrderStatusById(id, status);
	}
	
	public void requestOrderDetailScreen(BaseScreen prevScreen, Order order) {
		try {
			AdminOrderDetailScreen adminOrderDetailScreen = new AdminOrderDetailScreen(Config.ADMIN_ORDER_DETAIL_SCREEN_PATH,order);
			adminOrderDetailScreen.setStage(prevScreen.getStage());
			adminOrderDetailScreen.setController(new AdminOrderController());
			adminOrderDetailScreen.setPrevScreen(prevScreen);
			adminOrderDetailScreen.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void requestOrderScreen(BaseScreen prevScreen) {
		try {
			AdminOrderScreen adminOrderScreen = new AdminOrderScreen(Config.ADMIN_ORDER_SCREEN_PATH);
			adminOrderScreen.setStage(prevScreen.getStage());
			adminOrderScreen.setController(new AdminOrderController());
			adminOrderScreen.setHomeScreen(prevScreen.getHomeScreen());
			adminOrderScreen.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void requestUserScreen(BaseScreen prevScreen) {
		try {
			AdminUserScreen userScreen = new AdminUserScreen(Config.ADMIN_USER_SCREEN_PATH);
			userScreen.setController(new AdminUserController());
			userScreen.setHomeScreen(prevScreen.getHomeScreen());
			userScreen.setStage(prevScreen.getStage());
			userScreen.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }

}
