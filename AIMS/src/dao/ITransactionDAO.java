package dao;

import java.sql.SQLException;

import entity.payment.PaymentTransaction;

public interface ITransactionDAO {

	void createTransaction(PaymentTransaction trans) throws SQLException;

}
