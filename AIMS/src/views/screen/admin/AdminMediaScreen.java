package views.screen.admin;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import controller.AdminMediaController;
import entity.media.Media;
import javafx.beans.property.ReadOnlyObjectWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.HBox;
import utils.Config;
import views.screen.BaseScreen;

public class AdminMediaScreen extends BaseScreen{
    @FXML
    private TableView<Media> mediaTableView;

    @FXML
    private TableColumn<Media, Integer> idColumn;

    @FXML
    private TableColumn<Media, String> titleColumn;

    @FXML
    private TableColumn<Media, String> categoryColumn;

    @FXML
    private TableColumn<Media, Integer> valueColumn;

    @FXML
    private TableColumn<Media, Integer> priceColumn;

    @FXML
    private TableColumn<Media, Integer> quantityColumn;

    @FXML
    private TableColumn<Media, Boolean> isSupportRushColumn;

    @FXML
    private TableColumn<Media, String> imageColumn;

    @FXML
    private TableColumn<Media, Media> actionsColumn;
    
	public AdminMediaScreen(String screenPath) throws IOException {
		super(screenPath);
	}
	
	@Override
	public void show() {
		setMediaInfo();
		super.show();
	}
	
	@Override
	public AdminMediaController getController() {
		return (AdminMediaController)super.getController();
	}
	
    private void setMediaInfo() {
		setController(new AdminMediaController());
        idColumn.setCellValueFactory(new PropertyValueFactory<Media, Integer>("id"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<Media, String>("title"));
        categoryColumn.setCellValueFactory((new PropertyValueFactory<Media, String>("category")));
        valueColumn.setCellValueFactory(new PropertyValueFactory<Media, Integer>("value"));
        priceColumn.setCellValueFactory(new PropertyValueFactory<Media, Integer>("price"));
        quantityColumn.setCellValueFactory(new PropertyValueFactory<Media, Integer>("quantity"));
        isSupportRushColumn.setCellValueFactory(new PropertyValueFactory<Media, Boolean>("isSupportRushShipping"));
        imageColumn.setCellValueFactory(new PropertyValueFactory<Media, String >("imageURL"));

        actionsColumn.setCellValueFactory(param -> new ReadOnlyObjectWrapper<>(param.getValue()));
        actionsColumn.setCellFactory(param -> new TableCell<Media, Media>() {
            private final Button editButton = new Button("Edit");
            private final Button deleteButton = new Button("Delete");

            @Override
            protected void updateItem(Media media, boolean empty) {
                if (empty) {
                    setGraphic(null);
                    return;
                }

                HBox buttonsHBox = new HBox(editButton, deleteButton);
                deleteButton.setOnAction(e -> {
                    try {
                    	handleDeleteMedia(media);
                        update();
                    } catch (Exception ex) {
                        throw new RuntimeException(ex);
                    }
                });
                editButton.setOnAction(event -> {
                    try {
                    	handleUpdateMedia(media);
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
            mediaTableView.setItems(getController().getAllMedia());
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    
    private void handleUpdateMedia(Media media) {
    	getController().requestUpdateMedia(media, this);;
    }
    
    private void handleDeleteMedia(Media media) throws SQLException {
    	getController().deleteMedia(media);;
    }

    @FXML
    private void handleCreateMedia() {
    	getController().requestCreateMedia(this);
    }
    
    
    @FXML
    private void handleLogoutAction() {
    	homeScreen.show();
    }
    
    @FXML
    void handleShowOrderScreen(ActionEvent event) {
    	getController().requestOrderScreen(this);
    }
    
    	
    @FXML
    void handleShowUserScreen() {
        getController().requestUserScreen(this);
    }
}
