package subsystem.vnpay;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import controller.BaseController;
import controller.PayOrderController;
import entity.payment.PaymentTransaction;
import exception.FailedTransactionException;
import subsystem.IPayment;
import subsystem.vnpay.*;
import utils.Config;

public class VNPayController extends BaseController implements IPayment{
	private PayOrderController client;
	private Stage stage;
	
	public VNPayController(PayOrderController client, Stage stage) {
		this.client = client;
		this.stage = stage;
	}

	@Override
	public void payOrder(int amount, String content) throws UnsupportedEncodingException {
		Request request = new Request(amount, content);
	
		String payUrl = request.createPayUrl();
		try {
			VNPayScreen vnpayScreen = new VNPayScreen(Config.PAYMENT_SCREEN_PATH, payUrl);
			vnpayScreen.setController(this);
			vnpayScreen.setStage(stage);
			vnpayScreen.show();
			//System.out.print("xu ly tại day");
			 
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void processResponse(String query) {
		Response response = new Response();
		try {
			PaymentTransaction trans = response.processResponse(query);
			client.handleSuccess(trans);
		} catch (Exception e) {
			client.handleFailure(e.getMessage());
		}
	}

}
