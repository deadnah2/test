package views.screen.admin.edit;

import controller.AdminUserController;
import entity.user.User;
import exception.InvalidEmailException;
import exception.InvalidNumberException;
import exception.UserNotFoundException;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import utils.EmailValidator;
import utils.PhoneValidator;
import views.screen.BaseScreen;
import views.screen.popup.PopupScreen;

import java.io.IOException;

public class EditUserScreen extends BaseScreen {
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
            if(user == null)
                throw new UserNotFoundException("User Not Found!");
            int id = this.user.getId();
            String email = textEmail.getText();
            if (!EmailValidator.isValidEmail(email))
                throw new InvalidEmailException("Invalid Email");

            String phone = textPhone.getText();
            if (!PhoneValidator.isValidPhoneNumber(phone))
                throw new InvalidNumberException("");

            String name = textName.getText();

            getController().updateUser(new User(id, name, email, phone));
            homeScreen.show();
        } catch (InvalidNumberException | InvalidEmailException | UserNotFoundException e) {
            PopupScreen.error(e.getMessage());
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private User user;

    public EditUserScreen setUser(User user) {
        this.user = user;
        return this;
    }

    public EditUserScreen(String screenPath) throws IOException {
        super(screenPath);
    }

    public EditUserScreen initScreen() throws IOException {
        try{
            if(this.user == null)
                throw new UserNotFoundException("Not found user");
            formTitle.setText("ID: " + user.getId());
            textName.setText(user.getName());
            textEmail.setText(user.getEmail());
            textPhone.setText(user.getPhone());
            return this;
        }catch (UserNotFoundException e){
            PopupScreen.error(e.getMessage());
            return this;
        } catch (Exception e){
            e.printStackTrace();
            return this;
        }
    }

    @Override
    public AdminUserController getController(){
        return (AdminUserController) super.getController();
    }
}
