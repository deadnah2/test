package views.screen.payment;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import views.screen.BaseScreen;

public class PaymentResultScreen extends BaseScreen {
	@FXML
	private Label pageTitle;

	@FXML
	private Label resultLabel;

	@FXML
	private Button okButton;
	
	@FXML
	private Label messageLabel;

	public PaymentResultScreen( String screenPath, String result, String message) throws IOException {
		super(screenPath);
		resultLabel.setText(result);
		messageLabel.setText(message);
		System.out.println("payment screen hd");		
	}
	
	@FXML
	void confirmPayment(MouseEvent event) throws IOException {
		homeScreen.show();
	}

}