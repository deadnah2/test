package entity.order;

import java.util.List;

import entity.cart.CartMedia;
import entity.shipping.DeliveryInfo;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
public class Order {
	private List<CartMedia> mediaList;
	private int id;
	private String email;
    private String name;
    private String phone;
    private String province;
    private String address;
    private String instruction;
    private boolean isRush;
    private LocalDate rushTime;
    private String rushInstruction;
    private int shippingFee;
	private int costNoVAT;
	private int costVAT;
	private int totalCost;
	private String status;
	private boolean isPaid;
	
	private DeliveryInfo info;
	public Order(List<CartMedia> mediaList, int costNoVAT, int costVAT) {
		this.mediaList = mediaList;
		this.costNoVAT = costNoVAT;
		this.costVAT = costVAT;
	}
	
	public Order(int id, String email, String name, String phone, String province, String address, String instruction,
			boolean isRush, LocalDate rushTime, String rushInstruction, int shippingFee, int totalCost, String status, boolean isPaid) {
		this.id = id;
		this.email = email;
		this.name = name;
		this.phone = phone;
		this.province = province;
		this.address = address;
		this.instruction = instruction;
		this.isRush = isRush;
		this.rushTime = rushTime;
		this.rushInstruction = rushInstruction;
		this.shippingFee = shippingFee;
		this.totalCost = totalCost;
		this.status = status;
		this.isPaid = isPaid;
	}



	public List<CartMedia> getMediaList() {
		return mediaList;
	}
	
	public void setCartMediaList(List<CartMedia> mediaList) {
		this.mediaList = mediaList;
	}
	
	public int getShippingFee() {
		return shippingFee;
	}
	
	public int getCostNoVAT() {
		return costNoVAT;
	}
	public int getCostVAT() {
		return costVAT;
	}
	public int getTotalCost() {
		return totalCost;
	}
	
	
	public int getId() {
		return id;
	}

	public String getEmail() {
		return email;
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

	public boolean getIsRush() {
		return isRush;
	}

	public LocalDate getRushTime() {
		return rushTime;
	}

	public String getStatus() {
		return status;
	}

	public String getRushInstruction() {
		return rushInstruction;
	}

	public DeliveryInfo getInfo() {
		return info;
	}
	
	public void setInfo(DeliveryInfo info) {
		this.info = info;
		if (info.isRush()) shippingFee = calculateRushShippingFee(info.getProvince());
		else shippingFee = calculateNormalShippingFee(info.getProvince());
		totalCost = calculateTotal(shippingFee);
		name = info.getName();
		phone = info.getPhone();
		email = info.getEmail();
		province = info.getProvince();
		address = info.getAddress();
		instruction = info.getInstruction();
		isRush = info.isRush();
		rushTime = info.getRushTime();
		rushInstruction = info.getRushInstruction();
	}	

	public boolean getIsPaid() {
		return isPaid;
	}

	public int calculateNormalShippingFee(String province) {
		if (province == null) return 0;
		else if (province.toLowerCase().contains("hà nội") || province.toLowerCase().contains("hồ chí minh")) {
			return 30;
		}
		else return 20;
	}
	
	public int calculateRushShippingFee(String province) {
		if (province == null) return 0;
		return 50;
	}
	
	public int calculateTotal(int shippingFee) {
		return costVAT + shippingFee;
	}
	
	public static int getNumberOfRushShippingProduct(List<CartMedia> cartMediaList){
        int count = 0;
        for(CartMedia cm : cartMediaList){
            if(cm.getMedia().getIsSupportRushShipping()) count++;
        }
        return count;
    }

	@Override
	public String toString() {
		return "Order [mediaList=" + mediaList + ", id=" + id + ", email=" + email + ", name=" + name + ", phone="
				+ phone + ", province=" + province + ", address=" + address + ", instruction=" + instruction
				+ ", isRush=" + isRush + ", rushTime=" + rushTime + ", rushInstruction=" + rushInstruction
				+ ", shippingFee=" + shippingFee + ", totalCost=" + totalCost + ", status=" + status + ", isPaid="
				+ isPaid + "]";
	}
	
}
