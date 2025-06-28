package entity.shipping;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import java.time.LocalDate;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;

public class DeliveryInfoTest {

    private DeliveryInfo deliveryInfo;

    @BeforeEach
    public void setUp() {
        deliveryInfo = new DeliveryInfo("test@gmail.com", "hieu", "0123456789", "Hà Nội", 
                                         "123", "Leave at door", true, LocalDate.now(), 
                                         "instructions");
    }

    @Test
    public void testValidateEmptyFields() {
        
        assertTrue(deliveryInfo.validateEmptyFields());

        
        deliveryInfo = new DeliveryInfo("", "hieu", "0123456789", "Hà Nội", "123 ", 
                                        "Leave at door", true, LocalDate.now(), "instructions");
        assertFalse(deliveryInfo.validateEmptyFields());
        deliveryInfo = new DeliveryInfo("hi@mail.com", "", "0123456789", "Hà Nội", "123 ", 
                "Leave at door", true, LocalDate.now(), "instructions");
        assertFalse(deliveryInfo.validateEmptyFields());
    }

    @Test
    public void testValidateName() {
        assertTrue(deliveryInfo.validateName("hieu"));
        assertFalse(deliveryInfo.validateName("ten nay dai vai cádjhfjshfjsdhjfhsjdfhjsdhfjd       dfhsdjfsfhjsdhfjkshfhsjkdhfks"));
    }

    @Test
    public void testValidatePhone() {
        assertFalse(deliveryInfo.validatePhone("01234a6789"));
        assertFalse(deliveryInfo.validatePhone("12345"));
        assertFalse(deliveryInfo.validatePhone("phone12345"));
        assertTrue(deliveryInfo.validatePhone("1234567890"));
    }

    @Test
    public void testValidateEmail() {
        assertFalse(deliveryInfo.validateEmail("test@mazczxczxczil.com"));
        assertTrue(deliveryInfo.validateEmail("test@gmail.com"));
        assertFalse(deliveryInfo.validateEmail("test@udsfsdfsdfsn.com"));
    }

    @Test
    public void testValidateTime() {
        assertTrue(deliveryInfo.validateTime(LocalDate.now().plusDays(1)));
        assertFalse(deliveryInfo.validateTime(LocalDate.now().minusDays(1)));
    }

    

    @Test
    public void testValidateRushAddress() {
        assertTrue(DeliveryInfo.validateRushAddress("Hà Nội"));
        assertTrue(DeliveryInfo.validateRushAddress("Hồ Chí Minh"));
        assertFalse(DeliveryInfo.validateRushAddress("Đà Nẵng"));
    }
}
