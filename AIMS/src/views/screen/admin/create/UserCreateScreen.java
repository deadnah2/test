package views.screen.admin.create;

import controller.AdminUserController;
import entity.user.User;
import exception.InvalidEmailException;
import exception.InvalidNumberException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import utils.EmailValidator;
import utils.PhoneValidator;
import views.screen.BaseScreen;
import views.screen.popup.PopupScreen;

import java.io.IOException;


public class UserCreateScreen extends BaseScreen {

    @FXML
    private Label formTitle;

    @FXML
    private TextField textEmail;

    @FXML
    private TextField textName;

    @FXML
    private TextField textPhone;

    @FXML
    void handleCancelAction(ActionEvent event) {
        prev.show();
    }

    @FXML
    void handleSaveAction(ActionEvent event) throws IOException {
        try {
            String email = textEmail.getText();
            if (!EmailValidator.isValidEmail(email))
                throw new InvalidEmailException("Invalid Email");

            String phone = textPhone.getText();
            if (!PhoneValidator.isValidPhoneNumber(phone))
                throw new InvalidNumberException("Invalid Phone Number");

            String name = textName.getText();

            getController().createUser(new User(name, email, phone));
            homeScreen.show();
        } catch (InvalidNumberException | InvalidEmailException e) {
            PopupScreen.error(e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    public UserCreateScreen(String screenPath) throws IOException {
        super(screenPath);
    }

    @Override
    public AdminUserController getController(){
        return (AdminUserController) super.getController();
    }
}
