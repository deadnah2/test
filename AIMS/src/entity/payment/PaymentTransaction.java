package entity.payment;

import java.time.LocalDate;

public class PaymentTransaction {
	private String id;
    private String content;
    private int amount;
    private int orderID;
    private LocalDate paidTime;

	public PaymentTransaction(String id, String content, int amount, LocalDate paidTime) {
		super();
		this.id = id;
		this.content = content;
		this.amount = amount;
		this.paidTime = paidTime;
	}
	
	public int getOrderID() {
		return orderID;
	}

	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}

	public String getId() {
		return id;
	}

	public String getContent() {
		return content;
	}

	public int getAmount() {
		return amount;
	}

	public LocalDate getPaidTime() {
		return paidTime;
	}

	

}
