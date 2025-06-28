package utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class PhoneValidatorTest {

    @Test
    public void testValidPhoneNumbers() {
    
        assertFalse(PhoneValidator.isValidPhoneNumber("000001234567990"));
        assertTrue(PhoneValidator.isValidPhoneNumber("0123456789"));
     
    }

    @Test
    public void testInvalidPhoneNumbers() {
    	assertFalse(PhoneValidator.isValidPhoneNumber("1234567990"));
        assertFalse(PhoneValidator.isValidPhoneNumber(null));
        assertFalse(PhoneValidator.isValidPhoneNumber(""));
        assertFalse(PhoneValidator.isValidPhoneNumber("12345"));
        assertFalse(PhoneValidator.isValidPhoneNumber("phone1234567890"));
    
    }
}
