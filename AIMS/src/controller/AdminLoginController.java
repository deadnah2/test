package controller;

import java.io.IOException;

import exception.FailedLoginException;
import utils.Config;
import views.screen.BaseScreen;
import views.screen.admin.AdminMediaScreen;

public class AdminLoginController extends BaseController{
	public void authenticateLogin(String email, String password, BaseScreen prevScreen) throws FailedLoginException {
		if (!email.equals("admin")) throw new FailedLoginException("Email does not exist"); 
		if (!password.equals("12345")) throw new FailedLoginException("Password is not correct"); 
		try {
			AdminMediaScreen adminMediaScreen = new AdminMediaScreen(Config.ADMIN_MEDIA_SCREEN_PATH);
			adminMediaScreen.setStage(prevScreen.getStage());
			adminMediaScreen.setHomeScreen(prevScreen.getHomeScreen());
			adminMediaScreen.setController(new AdminMediaController());
			adminMediaScreen.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
