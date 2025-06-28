package dao;

import java.sql.SQLException;
import java.util.List;

import entity.media.Media;
import entity.order.Order;
import entity.order.OrderMedia;

public interface IOrderDAO {
	List<Order> getAllOrder() throws SQLException;
	void changeOrderStatusById(int id, String status) throws SQLException;
	List<OrderMedia> getOrderMediaByOrderId (int id) throws SQLException;
	int createOrder(Order order) throws SQLException;
	void updateOrderIsPaidById(int id) throws SQLException;
}
