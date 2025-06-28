package utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PhoneValidator {

 
    //  số điện thoại có đúng 10 chữ số và bắt đầu bằng số 0
    private static final String PHONE_REGEX = "^0\\d{9}$";

    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);

    // Hàm kiểm tra tính hợp lệ của số điện thoại
    public static boolean isValidPhoneNumber(String phoneNumber) {
       
        if (phoneNumber == null) {
            return false;
        }
       
        Matcher matcher = PHONE_PATTERN.matcher(phoneNumber);
        return matcher.matches(); 
    }
}
