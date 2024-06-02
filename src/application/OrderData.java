package application;

import java.util.HashMap;
import java.util.Map;

public class OrderData {
	private Map<String, Integer> priceMap;
	String foodName;
	String qty;
	int price;
	OrderData(){
		priceMap = new HashMap<>();
        initializePrices();
	}
	private void initializePrices() {
		priceMap.put("Salmon Symphony", 269);
		priceMap.put("Eternal Flame Pasta", 259);
		priceMap.put("Chicken Parmesan", 259);
		priceMap.put("Cordon Bleu", 259);
		priceMap.put("Sizzling Sisig", 239);
		priceMap.put("Carbonara", 159);
		priceMap.put("Palabok", 159);
		priceMap.put("Classic Stack & Fries", 199);
		priceMap.put("BBQ Fest", 299);
		priceMap.put("Slice & Salad Supreme", 259);
		priceMap.put("BBQ Bonanza Breast", 219);
		priceMap.put("Cheesy Chicken", 219);
		priceMap.put("Crispy Parmesan", 219);
		priceMap.put("Cheese", 99);
		priceMap.put("Sour & Cream", 99);
		priceMap.put("BBQ", 99);
		priceMap.put("Bite-sized Bliss", 99);
		priceMap.put("Classic Caliber", 139);
		priceMap.put("The Patty Palace", 199);
		priceMap.put("Solo", 159);
		priceMap.put("Barkada", 359);
		priceMap.put("Gigante", 759);
		priceMap.put("Strawberry Shortcake", 199);
		priceMap.put("Sunny Squeeze Shack", 59);
		priceMap.put("Blissful Blend Cafe", 59);
		priceMap.put("Ice Tea Oasis", 59);
	}
	public int getPrice(String itemName) {
        return priceMap.getOrDefault(itemName, 0); // Return 0 if item not found
    }
	
	public void setFoodName(String foodName) {
		this.foodName = foodName;
	}
	public void setQty(String qty) {
		this.qty= qty;
	}
	public void setPrice(int price) {
		this.price= price;
	}
	
	public String getFoodName() {
		return foodName;
	}
	public String getQty() {
		return qty;
	}
	
		
}
