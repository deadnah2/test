package views.screen.home;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import controller.HomeController;
import entity.cart.Cart;
import entity.media.Media;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.MenuButton;
import javafx.scene.control.MenuItem;
import javafx.scene.control.SplitMenuButton;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import utils.Config;
import views.screen.BaseScreen;
import views.screen.cart.CartScreen;
import views.screen.item.SpecificMediaDetailScreen;

public class HomeScreen extends BaseScreen{
	@FXML private Label numMediaInCart;
    @FXML private VBox vboxMedia1;
    @FXML private VBox vboxMedia2;
    @FXML private VBox vboxMedia3;
    @FXML private VBox vboxMedia4;
    @FXML private HBox hboxMedia;
    @FXML private SplitMenuButton btnSearch;
    @FXML private Button btnSort;
    @FXML private TextField searchText;
    @FXML private Button prevPageButton;
    @FXML private Button nextPageButton;
    @FXML private Label pageLabel;
	
    private int currentPage = 0;
    private final int ITEMS_PER_PAGE = 12;

    private List<Media> mediaList;
    private List<Media> filteredMediaList;
    private Cart cart;

    public HomeScreen(String screenPath) throws IOException{
    	super(screenPath);
        this.cart = new Cart();
        setController(new HomeController());
        initializeCategories();
        updateNumMediaInCart();
    }
    
    @Override
    public void show() {
    	//reset cart media
    	if (cart!=null) {
    		cart.emptyCart();
    	}
    	setHomeInfo();
    	updateNumMediaInCart();
    	super.show();
    }
    
    public void showWithoutFetching() {
    	setHomeInfoWithoutFetching();
    	updateNumMediaInCart();
    	super.show();
    }
    
    
    @Override
    public HomeController getController() {
    	return (HomeController)super.getController();
    }
   
    public void updateNumMediaInCart(){
    	numMediaInCart.setText(String.valueOf(cart.getMediaList().size()) + " media");
    }
    
    private void setHomeInfo() {
    	try {	
    		mediaList = getController().getAllMedia();
    		filteredMediaList = new ArrayList<>(mediaList);
			displayPage(currentPage);
		} catch (SQLException e) {
			e.printStackTrace();
		}  	    	    	
    }
    
    private void setHomeInfoWithoutFetching() {
		filteredMediaList = new ArrayList<>(mediaList);
		displayPage(currentPage);   	    	
    }
    
    private void displayPage(int pageIndex) {
        clearMediaVBoxes();

        int start = pageIndex * ITEMS_PER_PAGE;
        int end = Math.min(start + ITEMS_PER_PAGE, filteredMediaList.size());

        for (int i = start; i < end; i++) {
        	
        	Media media = filteredMediaList.get(i);
        	System.out.println(media);
			try {
				HomeItemScreen itemScreen= new HomeItemScreen(Config.HOME_ITEM_SCREEN_PATH, media, this, cart);
				itemScreen.setOnClick(event -> {
					try {
						SpecificMediaDetailScreen detailScreen = media.getSpecificMedia().getDetailScreen();
						detailScreen.setMedia(media);
						detailScreen.setCart(cart);
						detailScreen.setStage(this.stage);
						detailScreen.setHomeScreen(this);
						detailScreen.setController(this.getController());
						detailScreen.show();	                	
					} catch (IOException e) {
						e.printStackTrace();
					}
	                System.out.println("Item clicked: " + media.getTitle());
	            });
				VBox targetVBox = getVBoxForIndex(i - start);
	            targetVBox.getChildren().add(itemScreen.getRoot());
			} catch (SQLException | IOException e) {
				e.printStackTrace();
			}   
        }

        updatePaginationControls();
    }

    private void clearMediaVBoxes() {
        vboxMedia1.getChildren().clear();
        vboxMedia2.getChildren().clear();
        vboxMedia3.getChildren().clear();
        vboxMedia4.getChildren().clear();
    }

    private VBox getVBoxForIndex(int index) {
        if (index % 4 == 0) {
            return vboxMedia1;
        } else if (index % 4 == 1) {
            return vboxMedia2;
        } else if (index % 4 == 2) {
        	return vboxMedia3;
        } else {
            return vboxMedia4;
        }
    }
    	
    @FXML
    private void handlePrevPageAction() {
        if (currentPage > 0) {
            currentPage--;
            displayPage(currentPage);
        }
    }

    @FXML
    private void handleNextPageAction() {
        if ((currentPage + 1) * ITEMS_PER_PAGE < filteredMediaList.size()) {
            currentPage++;
            displayPage(currentPage);
        }
    }

    private void updatePaginationControls() {
        pageLabel.setText("Page " + (currentPage + 1));
        prevPageButton.setDisable(currentPage == 0);
        nextPageButton.setDisable((currentPage + 1) * ITEMS_PER_PAGE >= filteredMediaList.size());
    }
    
    @FXML
    private void handleSearchAction() {
    	String query = searchText.getText().toLowerCase();
        filteredMediaList = mediaList.stream()
                .filter(media -> media.getTitle().toLowerCase().contains(query))
                .collect(Collectors.toList());
        currentPage = 0; // Reset to the first page of results
        displayPage(currentPage);
    }
    
    @FXML
    private void handleResetAction() {
    	filteredMediaList = new ArrayList<>(mediaList);
    	currentPage = 0;
		displayPage(currentPage);
    }
    
    @FXML
    private void handleSortByPriceAction() {
    	filteredMediaList.sort(Comparator.comparingDouble(Media::getPrice));
        currentPage = 0; // Reset to the first page of sorted results
        displayPage(currentPage);
    }
    
    private void initializeCategories() {
        MenuItem bookCategory = new MenuItem("Book");
        MenuItem cdCategory = new MenuItem("CD");
        MenuItem dvdCategory = new MenuItem("DVD");

        bookCategory.setOnAction(event -> filterByCategory("Book"));
        cdCategory.setOnAction(event -> filterByCategory("CD"));
        dvdCategory.setOnAction(event -> filterByCategory("DVD"));

        btnSearch.getItems().addAll(bookCategory, cdCategory, dvdCategory);
    }

    private void filterByCategory(String category) {
        filteredMediaList = mediaList.stream()
                .filter(media -> media.getCategory().equalsIgnoreCase(category))
                .collect(Collectors.toList());
        currentPage = 0; // Reset to the first page of filtered results
        displayPage(currentPage);
    }
    
    @FXML
    private void handleCartRequest() {
    	getController().requestToCart(cart, this);
    }
    
    @FXML
    private void handleAdminRequest() {
    	getController().requestToAdmin(this);
    }
}
