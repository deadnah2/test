package views.screen.popup;

import java.io.File;
import java.io.IOException;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import javafx.util.Duration;
import utils.Config;
import views.screen.BaseScreen;

public class PopupScreen extends BaseScreen{
    @FXML ImageView tickicon;

    @FXML Label message;

    private PopupScreen() throws IOException{
        super(Config.POPUP_PATH);
    }

    private static PopupScreen createPopupScreen(String message, String imagepath) throws IOException{
        PopupScreen popup = new PopupScreen();
        popup.setStage(new Stage());
        popup.message.setText(message);
        File file = new File(imagepath);
		Image img = new Image(file.toURI().toString());
		popup.tickicon.setImage(img);
        return popup;
    }
    
    @Override
	public void show() {
    	super.show();
    	close(1.5);
    }
    
    private void close(double time){
        PauseTransition delay = new PauseTransition(Duration.seconds(time));
        delay.setOnFinished( event -> stage.close() );
        delay.play();
    }

    public static void success(String message) throws IOException{
        createPopupScreen(message, Config.SUCCESS_IMAGE_PATH).show();
    }

    public static void error(String message) throws IOException{
    	createPopupScreen(message, Config.FAILURE_IMAGE_PATH).show();
    }

//    public static PopupScreen loading(String message) throws IOException{
//        return popup(message, Configs.IMAGE_PATH + "/" + "loading.gif", true);
//    }
}

