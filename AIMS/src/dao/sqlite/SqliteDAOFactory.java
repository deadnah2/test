package dao.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;

import dao.*;

public class SqliteDAOFactory implements IDAOFactory{
	private IMediaDAO mediaDAO;
	private IOrderDAO orderDAO;
	private Connection connection;
	private ISpecificMediaDAO bookDAO;
	private ISpecificMediaDAO cdDAO;
	private ISpecificMediaDAO dvdDAO;
	private IUserDAO userDAO;
	private ITransactionDAO transDAO;
	
	public SqliteDAOFactory() throws Exception{
		this.connection = createConnection();
	}
	
	private Connection createConnection() throws Exception{
		Class.forName("org.sqlite.JDBC");
		//delete AIMS in the link if error
        return DriverManager.getConnection("jdbc:sqlite:assets/db/aims.db");
    }

	@Override
	public IMediaDAO getMediaDAO() {
		if (mediaDAO==null) {
			mediaDAO = new SqliteMediaDAO(connection);
		}
		return mediaDAO;
	}

	@Override
	public IOrderDAO getOrderDAO() {
		if (orderDAO==null) {
			orderDAO = new SqliteOrderDAO(connection);
		}
		return orderDAO;
	}
	
	@Override
	public ISpecificMediaDAO getBookDAO() {
		if (bookDAO==null) {
			bookDAO = new SqliteBookDAO(connection);
		}
		return bookDAO;
	}
	
	@Override
	public ISpecificMediaDAO getCDDAO() {
		if (cdDAO==null) {
			cdDAO = new SqliteCdDAO(connection);
		}
		return cdDAO;
	}
	
	@Override
	public ISpecificMediaDAO getDVDDAO() {
		if (dvdDAO==null) {
			dvdDAO = new SqliteDvdDAO(connection);
		}
		return dvdDAO;
	}

	@Override
	public IUserDAO getUserDAO() {
		if(userDAO == null){
			userDAO = new SqliteUserDao(connection);
		}
		return userDAO;
	}
	
	@Override
	public ITransactionDAO getTransDAO() {
		if(transDAO == null){
			transDAO = new SqliteTransactionDAO(connection);
		}
		return transDAO;
	}


}