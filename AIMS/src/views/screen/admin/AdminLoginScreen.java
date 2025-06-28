package views.screen.admin;

import java.io.IOException;
import java.sql.SQLException;

import controller.AdminLoginController;
import controller.HomeController;
import exception.FailedLoginException;
import javafx.fxml.FXML;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import views.screen.BaseScreen;
import views.screen.popup.PopupScreen;

public class AdminLoginScreen extends BaseScreen{
	@FXML
    private TextField email;

    @FXML
    private PasswordField password;

    public AdminLoginScreen(String screenPath) throws IOException{
        super(screenPath);
    }
    
    @Override
    public AdminLoginController getController() {
    	return (AdminLoginController)super.getController();
    }
    
    @FXML
    void login() throws IOException, InterruptedException, SQLException {
        try {
            getController().authenticateLogin(email.getText(), password.getText(), this);
            PopupScreen.success("Login Successfully!");
        } catch (FailedLoginException e) {
            PopupScreen.error(e.getMessage());
            e.printStackTrace();
        }
    }

}
