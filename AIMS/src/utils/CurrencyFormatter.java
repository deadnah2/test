package utils;

import java.text.NumberFormat;
import java.util.Locale;

public class CurrencyFormatter{
	public static String format(int num) {
		Locale vietname = new Locale("vi", "VN");
		NumberFormat defaultFormat = NumberFormat.getCurrencyInstance(vietname);
		return defaultFormat.format(num*1000);
	}

}
