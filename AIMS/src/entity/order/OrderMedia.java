package entity.order;

public class OrderMedia {
	private int id;
	private boolean isRush;
	public OrderMedia(int id, String title, String imageURL, int quantity, int price, boolean isRush) {
		super();
		this.id = id;
		this.title = title;
		this.imageURL = imageURL;
		this.quantity = quantity;
		this.price = price;
		this.isRush= isRush;
	}
	public boolean getIsRush() {
		return isRush;
	}
	public int getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	
	public String getImageURL() {
		return imageURL;
	}
	public int getQuantity() {
		return quantity;
	}
	public int getPrice() {
		return price;
	}
	private String title;

	private String imageURL;
	private int quantity;
    private int price;
	public OrderMedia() {
		// TODO Auto-generated constructor stub
	}

}
