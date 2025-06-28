package controller;

import dao.DAOFactory;
import dao.IUserDAO;
import entity.user.User;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import utils.Config;
import views.screen.BaseScreen;
import views.screen.admin.AdminMediaScreen;
import views.screen.admin.create.UserCreateScreen;
import views.screen.admin.edit.EditUserScreen;
import views.screen.admin.AdminOrderScreen;

public class AdminUserController extends BaseController {
    IUserDAO userDAO = DAOFactory.getInstance().getUserDAO();

    public ObservableList<User> getAllUser() {
        return FXCollections.observableArrayList(userDAO.getAll());
    }

    public void deleteUser(User user) {
        userDAO.deleteUserById(user.getId());
    }

    public void createUser(User user) {
        userDAO.createUser(user);
    }

    public void requestCreateUser(BaseScreen prevScreen) {
        try {
            UserCreateScreen userCreateScreen =
                     new UserCreateScreen(Config.USER_CREATE_SCREEN_PATH);
            userCreateScreen.setController(this);
            userCreateScreen.setStage(prevScreen.getStage());
            userCreateScreen.setPrevScreen(prevScreen);
            userCreateScreen.setHomeScreen(prevScreen);
            userCreateScreen.show();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public void requestEditUser(BaseScreen prevScreen, User user) {
        try{
            EditUserScreen editUserScreen =
                    new EditUserScreen(Config.USER_CREATE_SCREEN_PATH);
            editUserScreen
                    .setUser(user)
                    .initScreen()
                    .setController(this);
            editUserScreen.setStage(prevScreen.getStage());
            editUserScreen.setPrevScreen(prevScreen);
            editUserScreen.setHomeScreen(prevScreen);
            editUserScreen.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void requestMediaScreen(BaseScreen prevScreen) {
        try{
            AdminMediaScreen mediaScreen = new AdminMediaScreen(Config.ADMIN_MEDIA_SCREEN_PATH);
            mediaScreen.setController(new AdminMediaController());
            mediaScreen.setStage(prevScreen.getStage());
            mediaScreen.setPrevScreen(prevScreen);
            mediaScreen.setHomeScreen(prevScreen.getHomeScreen());
            mediaScreen.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void requestOrderScreen(BaseScreen prevScreen) {
        try {
        	 AdminOrderScreen orderScreen = new AdminOrderScreen(Config.ADMIN_ORDER_SCREEN_PATH);
        	 orderScreen.setController(new AdminOrderController());
        	 orderScreen.setStage(prevScreen.getStage());
        	 orderScreen.setPrevScreen(prevScreen);
        	 orderScreen.setHomeScreen(prevScreen.getHomeScreen());
        	 orderScreen.show();
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void updateUser(User user) {
        userDAO.updateUserById(user);
    }
}
