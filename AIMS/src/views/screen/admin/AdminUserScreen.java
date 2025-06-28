package views.screen.admin;

import java.io.IOException;

import controller.AdminUserController;
import entity.user.User;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import views.screen.BaseScreen;


public class AdminUserScreen extends BaseScreen {

    @FXML
    private Button backHome;

    @FXML
    private Button btnCreate;

    @FXML
    private TableColumn<User, String> colEmail;

    @FXML
    private TableColumn<User, Integer> colId;

    @FXML
    private TableColumn<User, String> colName;

    @FXML
    private TableColumn<User, String> colPhone;

    @FXML
    private TableColumn<User, User> colActions;

    @FXML
    private Button mediaManage;

    @FXML
    private Button orderManage;

    @FXML
    private Button userManage;

    @FXML
    private TableView<User> userTable;

    @FXML
    void handleCreateUser(ActionEvent event) {
        getController().requestCreateUser(this);
    }

    @FXML
    void handleShowMediaScreen() {
        getController().requestMediaScreen(this);
    }

    @FXML
    void handleShowOrderScreen() {
        getController().requestOrderScreen(this);
    }


    @FXML
    void handleLogoutAction(ActionEvent event) {
        homeScreen.show();
    }

    public AdminUserScreen(String screenPath) throws IOException{
        super(screenPath);
    }

    @Override
    public void show(){
        setUserInfo();
        super.show();
    }

    @Override
    public AdminUserController getController(){
        return (AdminUserController) super.getController();
    }

    private void handleEditUser(User user){
        getController().requestEditUser(this, user);
    }

    private void setUserInfo() {
        setController(new AdminUserController());
        colId.setCellValueFactory(new PropertyValueFactory<User, Integer>("id"));
        colName.setCellValueFactory(new PropertyValueFactory<User, String>(
                "name"));
        colEmail.setCellValueFactory((new PropertyValueFactory<User,
                String>("email")));
        colPhone.setCellValueFactory(new PropertyValueFactory<User, String>(
                "phone"));

        colActions.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        colActions.setCellFactory(param -> new TableCell<User, User>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");

            @Override
            protected void updateItem(User user, boolean empty) {
                if (empty) {
                    setGraphic(null);
                    return;
                }

                HBox buttonsHBox = new HBox(editButton, deleteButton);
                buttonsHBox.setSpacing(12);
                deleteButton.setOnAction(e -> {
                    try {
                        getController().deleteUser(user);
                        update();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                });
                editButton.setOnAction(event -> {
                    try {
                       handleEditUser(user);
                    }catch (Exception err){
                        throw new RuntimeException(err);
                    }
                });
                setGraphic(buttonsHBox);
            }
        });
        update();
    }

    private void update() {
        try {
            userTable.setItems(getController().getAllUser());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
