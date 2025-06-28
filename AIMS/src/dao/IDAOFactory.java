package dao;

public interface IDAOFactory {
	IMediaDAO getMediaDAO();
	IOrderDAO getOrderDAO();
	ISpecificMediaDAO getBookDAO();
	ISpecificMediaDAO getCDDAO();
	ISpecificMediaDAO getDVDDAO();
	IUserDAO getUserDAO();
	ITransactionDAO getTransDAO();
}
