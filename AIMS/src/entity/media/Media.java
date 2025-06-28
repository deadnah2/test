package entity.media;

import java.util.List;
import java.util.ArrayList;
import entity.media.category.Book;
import entity.media.category.CD;
import entity.media.category.DVD;
import entity.media.category.SpecificMedia;
import exception.EmptyFieldsException;

public class Media {
	private int id;
    private String title;
    private String category;
    private int price;
    private int value;
    private int quantity;
    private String imageURL;
    private boolean isSupportRushShipping;
    private int weight;
    private SpecificMedia specificMedia;
    
	public Media(String title, String category, int price, int value, int quantity, String imageURL,
			boolean isSupportRushShipping, int weight) {
		super();
		this.title = title;
		this.category = category;
		this.price = price;
		this.value = value;
		this.quantity = quantity;
		this.imageURL = imageURL;
		this.isSupportRushShipping = isSupportRushShipping;
		this.weight = weight;
		setSpecificMedia();
	}

	public Media(int id, String title, String category, int price, int value, int quantity, String imageURL,
			boolean isSupportRushShipping, int weight) {
		super();
		this.id = id;
		this.title = title;
		this.category = category;
		this.price = price;
		this.value = value;
		this.quantity = quantity;
		this.imageURL = imageURL;
		this.isSupportRushShipping = isSupportRushShipping;
		this.weight = weight;
		setSpecificMedia();
	}
	
	public static List<String> getCategoryList(){
		List<String> categoryList = new ArrayList<>();
		categoryList.add("book");
		categoryList.add("cd");
		categoryList.add("dvd");
		return categoryList;
	}
	
	public void setSpecificMedia() {
		switch (this.category){
			case "book": 
				this.specificMedia = new Book();
				break;
			case "cd":
				this.specificMedia = new CD();
				break;
			case "dvd":
				this.specificMedia = new DVD();
				break;
		}
	}
	
	public void setSpecificMedia(SpecificMedia specificMedia) {
		this.specificMedia = specificMedia;
	}

	public SpecificMedia getSpecificMedia() {
		return specificMedia;
	}

	public int getId() {
		return id;
	}
	
	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getValue() {
		return value;
	}

	public void setValue(int value) {
		this.value = value;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getImageURL() {
		return imageURL;
	}

	public void setImageURL(String imageURL) {
		this.imageURL = imageURL;
	}

	public boolean getIsSupportRushShipping() {
		return isSupportRushShipping;
	}

	public void setIsSupportRushShipping(boolean isSupportRushShipping) {
		this.isSupportRushShipping = isSupportRushShipping;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}
	
	public void validateInfo() throws EmptyFieldsException {
		if (!validateEmptyFields()) throw new EmptyFieldsException("All fields must be filled!");
	}
	
	private boolean validateEmptyFields() {
		if (title.isEmpty() || category.isEmpty() || imageURL.isEmpty()) return false;
		return true;
	}
	
	@Override
	public String toString() {
		return "Media [id=" + id + ", title=" + title + ", category=" + category + ", price=" + price + ", value="
				+ value + ", quantity=" + quantity + ", imageURL=" + imageURL + ", isSupportRushShipping="
				+ isSupportRushShipping + ", weight=" + weight + "]";
	}

}
