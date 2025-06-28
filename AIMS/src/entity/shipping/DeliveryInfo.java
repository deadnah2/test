package entity.shipping;
import org.junit.Test;

import java.time.LocalDate;

import exception.AddressNotSupportRushOrderException;
import exception.EmptyFieldsException;
import exception.InvalidDateException;
import exception.InvalidEmailException;
import exception.InvalidNameException;
import exception.InvalidNumberException;
import junit.framework.TestCase;

public class DeliveryInfo extends TestCase {
	private String email;
    private String name;
    private String phone;
    private String province;
    private String address;
    private String instruction;
    private boolean isRush;
    private LocalDate rushTime;
    private String rushInstruction;
    
    public void validateInfo() throws AddressNotSupportRushOrderException,EmptyFieldsException, InvalidNameException, InvalidEmailException, InvalidNumberException, InvalidDateException{
    	if (!validateEmptyFields()) throw new EmptyFieldsException("All fields must be filled");
    	if (!validateName(name)) throw new InvalidNameException("Your name is invalid");
    	if (!validateEmail(email)) throw new InvalidEmailException("Your email is invalid");
    	if (!validatePhone(phone)) throw new InvalidNumberException("Your phone number is invalid");
    	if (isRush && !validateTime(rushTime)) throw new InvalidDateException("The delivery date must be after today");
    }
    
    public DeliveryInfo(String email, String name, String phone, String province, String address, 
			String instruction, boolean isRush, LocalDate rushTime, String rushInstruction) {
		this.email = email;
		this.name = name;
		this.phone = phone;
		this.province = province;
		this.address = address;
		this.instruction = instruction;
		this.isRush = isRush;
		this.rushTime = rushTime;
		this.rushInstruction = rushInstruction;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getName() {
		return name;
	}


	public String getPhone() {
		return phone;
	}

	

	public String getProvince() {
		return province;
	}

	

	public String getAddress() {
		return address;
	}

	

	public String getInstruction() {
		return instruction;
	}

	
	public boolean isRush() {
		return isRush;
	}

	public LocalDate getRushTime() {
		return rushTime;
	}
	
	public String getRushInstruction() {
		return rushInstruction;
	}

	@Override
	public String toString() {
		return "DeliveryInfo [email=" + email + ", name=" + name + ", phone=" + phone + ", province=" + province
				+ ", address=" + address + ", instruction=" + instruction + ", isRush=" + isRush + ", rushTime="
				+ rushTime + ", rushInstruction=" + rushInstruction + "]";
	}

	boolean validateName(String name) {
    	if (name.length()<50) {
    		return true;
    	}
    	else {
    		return false;
    	}
    }
    
    boolean validatePhone(String phone) {
    	if (phone.length() == 10) {
    		for (char c: phone.toCharArray()) {
    			if (c<'0' || c>'9'){
    				return false;
    			}
    		}
    		return true;
    	}
    	return false;
    }
    
      boolean validateEmail(String email) {
    	return email.contains("@mail.com") || email.contains("@gmail.com");
    }
    
   
    boolean validateTime(LocalDate time) {
    	return time.isAfter(LocalDate.now());
    }
    
    boolean validateEmptyFields() {
    	if (name.isEmpty() || email.isEmpty() || phone.isEmpty() || province==null || province.isEmpty() || address.isEmpty() || instruction.isEmpty()) return false;
    	if (isRush && (rushTime==null || rushInstruction.isEmpty())) return false;
    	return true;
    }
    
    
    public static boolean validateRushAddress(String province) {
    	if (province == null || province.isEmpty()) return false;
    	if (province.toLowerCase().contains("hà nội") || province.toLowerCase().contains("hồ chí minh")) return true;
        return false;
    }

    
}
