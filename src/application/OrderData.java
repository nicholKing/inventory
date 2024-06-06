package application;

import java.util.HashMap;
import java.util.Map;

public class OrderData {
    private Map<String, Integer> priceMap;
    String foodName;
    String size;
    String qty;
    int price;
    boolean isDrink; // Added isDrink attribute

    OrderData() {
        priceMap = new HashMap<>();
        initializePrices();
    }

    private void initializePrices() {
        priceMap.put("Cordon Bleu", 269);
        priceMap.put("Pork Sisig", 269);
        priceMap.put("Lechon Kawali", 269);
        priceMap.put("Pork Belly", 269);
        priceMap.put("Grilled Chicken", 269);
        priceMap.put("Beef Stir-fry", 269);
        priceMap.put("Bluberry Cake", 159);
        priceMap.put("Lava Cake", 159);
        priceMap.put("Carrot Cake", 159);
        priceMap.put("Red Velvet", 159); 
   
    }

    public int getPrice(String itemName) {
        return priceMap.getOrDefault(itemName, 0); // Return 0 if item not found
    }

    public int getFinalPrice() {
        return price;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public void setQty(String qty) {
        this.qty = qty;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setSize(String size) {
        this.size = size;
    }

    public void setDrink(boolean isDrink) {
        this.isDrink = isDrink;
    }

    public String getFoodName() {
        return foodName;
    }

    public String getQty() {
        return qty;
    }

    public String getSize() {
        return size;
    }

    public boolean isPref1() {
        return isDrink;
    }
}
	
		

