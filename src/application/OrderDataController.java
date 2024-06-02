package application;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

public class OrderDataController implements Initializable {
	
    @FXML
    private Label foodLabel;

    @FXML
    private Label priceLabel;

    @FXML
    private Label qtyLabel;
    
    @FXML
    private Button deleteBtn;
   
    int price;
    public void setData(OrderData orderData) {
    	foodLabel.setText(orderData.getFoodName());
    	qtyLabel.setText(orderData.getQty());
    	price = orderData.getPrice(orderData.getFoodName()) * Integer.parseInt(orderData.getQty());
    	priceLabel.setText(String.valueOf(price));
    	
    }
    
    public Button getButton() {
    	return deleteBtn;
    }
    
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
	}

}
