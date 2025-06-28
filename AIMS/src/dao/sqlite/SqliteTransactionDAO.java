package dao.sqlite;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;

import dao.ITransactionDAO;
import entity.payment.PaymentTransaction;

public class SqliteTransactionDAO implements ITransactionDAO{
	private Connection connection;

	public SqliteTransactionDAO(Connection connection) {
		this.connection = connection;
	}
	
	@Override
	public void createTransaction(PaymentTransaction trans) throws SQLException{
		String sql = "INSERT INTO PaymentTransaction (id, order_id, paid_time, amount, content) VALUES (?, ?, ?, ?, ?)";
		PreparedStatement pstmt = connection.prepareStatement(sql);
		pstmt.setString(1, trans.getId());
		pstmt.setInt(2, trans.getOrderID());
		pstmt.setString(3, trans.getPaidTime().toString());
		pstmt.setInt(4, trans.getAmount());
		pstmt.setString(5, trans.getContent());
		pstmt.executeUpdate();
	}
}
