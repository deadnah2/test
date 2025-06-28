package subsystem;

import java.io.UnsupportedEncodingException;

public interface IPayment {
	public void payOrder(int amount, String content) throws UnsupportedEncodingException;
}
