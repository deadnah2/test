package controller;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;
import javafx.event.ActionEvent;
import dao.DAOFactory;
import dao.IMediaDAO;
import entity.cart.Cart;
import entity.cart.CartMedia;
import entity.media.Media;
import entity.media.category.SpecificMedia;
import utils.Config;
import views.screen.BaseScreen;
import views.screen.admin.AdminLoginScreen;
import views.screen.cart.CartScreen;
import views.screen.home.HomeScreen;

public class HomeController extends BaseController{
	private IMediaDAO mediaDAO = DAOFactory.getInstance().getMediaDAO();
	
	public List<Media> getAllMedia() throws SQLException{
		List<Media> mediaList = mediaDAO.getAllMedia();
		for (Media media : mediaList) {
			SpecificMedia specificMedia = media.getSpecificMedia();
			media.setSpecificMedia(specificMedia.getSpecificMediaDAO().getByMediaId(media.getId()));		
		}
		return mediaList;
    }

	public void addMediaToCart(Cart cart, Media media) {
		CartMedia cartMedia = cart.checkMediaInCart(media);
		if (cartMedia != null) {
            cartMedia.setQuantity(cartMedia.getQuantity() + 1);
		} else {
			 cartMedia = new CartMedia(media, 1, media.getPrice());
			 cart.addCartMedia(cartMedia);
		}
		media.setQuantity(media.getQuantity()-1);
	}
	
	public void requestToCart(Cart cart, BaseScreen prevScreen) {
		try {
			CartScreen cartScreen = new CartScreen(Config.CART_SCREEN_PATH, cart);
			cartScreen.setStage(prevScreen.getStage());
			cartScreen.setHomeScreen(prevScreen.getHomeScreen());
			cartScreen.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void requestToAdmin(BaseScreen prevScreen) {
		AdminLoginScreen adminLoginScreen;
		try {
			adminLoginScreen = new AdminLoginScreen(Config.ADMIN_LOGIN_SCREEN_PATH);
			adminLoginScreen.setStage(prevScreen.getStage());
			adminLoginScreen.setHomeScreen(prevScreen);
			adminLoginScreen.setController(new AdminLoginController());
			adminLoginScreen.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}
}
