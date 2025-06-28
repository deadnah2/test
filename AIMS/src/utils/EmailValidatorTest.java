package utils;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class EmailValidatorTest {

    @Test
    public void testValidEmails() {
        
        assertTrue(EmailValidator.isValidEmail("test@example.com"));
        assertTrue(EmailValidator.isValidEmail("user.name+tag+sorting@example.com"));
        assertTrue(EmailValidator.isValidEmail("user_name@example.co.uk"));
        assertTrue(EmailValidator.isValidEmail("user-name@example.org"));
        assertTrue(EmailValidator.isValidEmail("user.name@example.com"));
    }

    @Test
    public void testInvalidEmails() {
        
        assertFalse(EmailValidator.isValidEmail(null));
        assertFalse(EmailValidator.isValidEmail(""));
        assertFalse(EmailValidator.isValidEmail("plainaddress"));
        assertFalse(EmailValidator.isValidEmail("@missingusername.com"));
 
    }
}
