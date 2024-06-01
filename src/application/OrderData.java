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
		priceMap.put("Salmon Symphony", 169);
		priceMap.put("Eternal Flame Pasta", 159);
		priceMap.put("Chicken Parmesan", 149);
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
