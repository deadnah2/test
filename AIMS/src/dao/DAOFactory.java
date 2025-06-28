package dao;

public class DAOFactory {
	private static IDAOFactory instance;

    private DAOFactory() {}

    public static void initialize(IDAOFactory factory) {
        if (instance == null) {
            instance = factory;
        }
    }

    public static IDAOFactory getInstance() {
        if (instance == null) {
            throw new IllegalStateException("DAOFactory is not initialized.");
        }
        return instance;
    }
}
