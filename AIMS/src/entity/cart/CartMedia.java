package entity.cart;

import entity.media.Media;

public class CartMedia {
	private Media media;
    private int quantity;
    private int price;
    
    public CartMedia(Media media, int quantity) {
    	this.media = media;
    	this.quantity = quantity;
    }
    
    public CartMedia(Media media, int quantity, int price) {
    	this.media = media;
    	this.quantity = quantity;
    	this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public Media getMedia() {
        return media;
    }

	public int getPrice() {
		return (price > 0) ? price : media.getPrice();
	}

	public void setPrice(int price) {
		this.price = price;
	}
	
	public int calculateTotal() {
		return getPrice() * quantity;
	}
}
