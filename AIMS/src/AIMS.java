import entity.cart.Cart;

import dao.*;
import dao.sqlite.SqliteDAOFactory;
import javafx.application.Application;
import javafx.stage.Stage;
import utils.Config;
import views.screen.home.HomeScreen;


public class AIMS extends Application {
    public static void main(String[] args){
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
    	try {
        	DAOFactory.initialize(new SqliteDAOFactory());
        	HomeScreen homeScreen = new HomeScreen(Config.HOME_SCREEN_PATH);
        	homeScreen.setHomeScreen(homeScreen);
        	homeScreen.setStage(stage);
        	homeScreen.show();
    	} catch (Exception e) {
    		e.printStackTrace();
    	}
    }
}