package entity.cart;

import java.util.ArrayList;
import java.util.List;

import exception.MediaUnavailableException;
import entity.media.Media;

public class Cart {	
	private List<CartMedia> cartMediaList;
	private int costNoVAT;
	private int costVAT;

	public Cart() {
		cartMediaList = new ArrayList<CartMedia>();
	}
	
	public void addCartMedia(CartMedia cm){
		 cartMediaList.add(cm);
		 updateCost();
	}

    public void removeCartMedia(CartMedia cm){
    	cartMediaList.remove(cm);
    	int prevQty = cm.getMedia().getQuantity();
    	cm.getMedia().setQuantity(prevQty + cm.getQuantity());
    	updateCost();
    }

    public List<CartMedia> getMediaList(){
        return cartMediaList;
    }
    
    public int getCostNoVAT() {
		return costNoVAT;
	}
    
    public int getCostVAT() {
		return costVAT;
	}
    
    public int calculateVAT() {
    	return (int)(0.1 * costNoVAT);
    }
    
    public void updateCost() {
    	costNoVAT = 0;
    	for (CartMedia cm : cartMediaList) {
    		costNoVAT += cm.calculateTotal();
    	}
    	costVAT = costNoVAT + calculateVAT();
    }

    public void emptyCart(){
    	cartMediaList.clear();
    }
    
    public void checkProductAvai() throws MediaUnavailableException{
        for (CartMedia cartMedia : cartMediaList) {
            int availQuantity = cartMedia.getMedia().getQuantity();
            if (availQuantity < 0) throw new MediaUnavailableException("Only " + availQuantity + " of " + cartMedia.getMedia().getTitle() + " available!");
        }
    }

	public CartMedia checkMediaInCart(Media media) {
		for (CartMedia cartMedia : cartMediaList) {
			if (cartMedia.getMedia().getId() == media.getId()) return cartMedia;
		}
		return null;
	}


}
