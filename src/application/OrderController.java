package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import javafx.util.Duration;

public class OrderController implements Initializable{
	
    @FXML
    private Button logoutBtn;
    @FXML
    private Label menu;
    @FXML
    private Label menuClose;
    
    @FXML
    private AnchorPane slider;
    @FXML
    private Label headerLabel;
    @FXML
    private Label nameLabel;
    
    //ORDER SYSTEM
    @FXML
    private Button addToCartBtn;
    @FXML
    private TextField qtyTextField;
    @FXML
    private Button backBtn;
    @FXML 
    private Button viewCartBtn; 
    @FXML
    private ImageView img1;
    @FXML
    private ImageView img2;
    @FXML
    private ImageView img3;
    @FXML
    private ImageView img4;
    @FXML
    private ImageView img5;
    @FXML
    private ImageView img6;
    @FXML
    private ImageView img7;
    @FXML
    private ImageView img8;
    
    @FXML
    private ImageView plusBtn;
    @FXML
    private ImageView minusBtn;
    @FXML
    private Button btn1;
    @FXML
    private Button btn2;
    @FXML
    private Button btn3;
    @FXML
    private Button btn4;
    @FXML
    private Button btn5;
    @FXML
    private Button btn6;
    @FXML
    private Button btn7;
    @FXML
    private Button btn8;
    @FXML
    private Label priceLabel1;
    @FXML
    private Label priceLabel2;
    @FXML
    private Label priceLabel3;
    @FXML
    private Label priceLabel4;
    @FXML
    private Label priceLabel5;
    @FXML
    private Label priceLabel6;
    @FXML
    private Label priceLabel7;
    @FXML
    private Label priceLabel8;
    
    //JAVA FX
    private Stage stage;
	private Scene scene;
	private Parent root;
	
	Image image = new Image("/pictures/fast-food.png");
	List<OrderData> orderList = new ArrayList<>();
	String dbName;
	String orderPage = "OrderPage.fxml";
    String homePage = "HomePage.fxml";
    String accPage = "AccountDetails.fxml";
    String cartPage = "MyCart.fxml";
    String tablePage = "TablePage.fxml";
    String rewardsPage = "RewardsPage.fxml";
    String previousClickedBtn = ""; // Initialize with an empty string
    
	boolean hasAccount = false;
	boolean isHomeBtn = false;
    boolean isSideBtn = false;
    boolean isOrderBtn = false;
    boolean isCartBtn = false;
    
    boolean isBestSellers = false;
    boolean isComboMeals = false;
    boolean isNewProducts = false;
    boolean isChicken = false;
    boolean isFries = false;
    boolean isBurger = false;
    boolean isPizza = false;
    boolean isDessert = false;
    
    //FOOD VARIABLES
    String foodName;
	int price;
	String qty;
	boolean clicked = false;
	boolean added = false;
	boolean selected = false;
	int quantity = 0;
   
	//TOP BUTTONS
	public void homeBtn(ActionEvent event) throws IOException, SQLException {
		isHomeBtn = true;
		changeScene(event, homePage);
	}
	public void orderBtn(ActionEvent event) throws IOException, SQLException {
		setOrders(orderList);
		isOrderBtn = true;
		changeScene(event, orderPage);
	}
	public void promoBtn() {
		System.out.println("Promo");
	}
	public void signUp(ActionEvent event) throws IOException {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
		    root = loader.load();
			System.out.println("Test");
			SignUpController signUpPage = loader.getController();
			
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
	public void signIn(ActionEvent event) throws IOException {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
		    root = loader.load();
			
		    Scene1Controller loginPage = loader.getController();
		    
		    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		
		}
	public void showFAQ() {
		Alert alert = new Alert(AlertType.INFORMATION);
        alert.setHeaderText("");
        alert.setContentText("FAQ");
        alert.show();
	}
	//SIDE BUTTONS
	public void displayName() throws SQLException {
		nameLabel.setText(dbName);
	}
	public void showAccount(ActionEvent event) throws IOException, SQLException {
		if(hasAccount) {
			changeScene(event, "AccountDetails.fxml");
		}
		else {showAlert("Login or register to edit your information", AlertType.INFORMATION);}
	}
	public void showCart(ActionEvent event) throws IOException, SQLException {
		this.setOrders(orderList);
		isCartBtn = true;
		changeScene(event, "MyCart.fxml");
	}
	public void showTable(ActionEvent event) throws IOException, SQLException {
		changeScene(event, "TablePage.fxml");
	}

	public void showRewards(ActionEvent event) throws IOException, SQLException {
		if(hasAccount) {
			changeScene(event, rewardsPage);
		}
		else {
			showAlert("Login or register to unlock exciting rewards!", AlertType.INFORMATION);
		}
	}
	public void logout(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
		root = loader.load();
		    
		Alert alert = new Alert(AlertType.CONFIRMATION);
		alert.setTitle("Logout");
		alert.setHeaderText("You're about to logout");
		alert.setContentText("Are you sure you want to logout?");
			
		if(alert.showAndWait().get() == ButtonType.OK) {
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
		}
		Scene1Controller loginPage = loader.getController();
		}
	
	
	//FOOD METHODS
	public void showBestSellers() {
		btn1.setText("Salmon Symphony");
		btn2.setText("Eternal Flame Pasta");
		btn3.setText("Chicken Parmesan");
		btn1.setPrefHeight(60);
		btn2.setPrefHeight(60);
		btn3.setPrefHeight(60);
		btn1.setFont(new Font(15));
		btn2.setFont(new Font(15));
		btn3.setFont(new Font(15));
		btn2.setLayoutX(310);
		btn4.setVisible(false);
		btn5.setVisible(false);
		btn6.setVisible(false);
		btn7.setVisible(false);
		btn8.setVisible(false);
		img1.setImage(image);img2.setImage(image);img3.setImage(image);img4.setVisible(false);img5.setVisible(false);img6.setVisible(false);img7.setVisible(false);img8.setVisible(false);
		priceLabel1.setText("₱ 169"); 
		priceLabel2.setText("₱ 159");
		priceLabel3.setText("₱ 149");
		priceLabel4.setVisible(false);priceLabel5.setVisible(false);priceLabel6.setVisible(false);priceLabel7.setVisible(false);priceLabel8.setVisible(false);
		
	}
	public void showNewProducts() {
		btn1.setText("Sizzling Sisig");
		btn2.setText("Cordon Bleu");
		btn3.setText("Palabok");
		btn4.setText("Carbonara");
		btn1.setPrefHeight(60);
		btn2.setPrefHeight(60);
		btn3.setPrefHeight(60);
		btn4.setPrefHeight(60);
		btn1.setFont(new Font(15));
		btn2.setFont(new Font(15));
		btn3.setFont(new Font(15));
		btn4.setFont(new Font(15));
		btn2.setLayoutX(310);
		btn5.setVisible(false);
		btn6.setVisible(false);
		btn7.setVisible(false);
		btn8.setVisible(false);
		img1.setImage(image);
		img2.setImage(image);
		img3.setImage(image);
		img4.setImage(image);
		img5.setVisible(false);
		img6.setVisible(false);
		img7.setVisible(false);
		img8.setVisible(false);
	}
	public void showComboMeals() {
		btn1.setText("Classic Stack & Fries");
		btn2.setText("BBQ Fest");
		btn3.setText("Slice & Salad Supreme");
		btn1.setPrefHeight(60);
		btn2.setPrefHeight(60);
		btn3.setPrefHeight(60);
		btn1.setFont(new Font(15));
		btn2.setFont(new Font(15));
		btn3.setFont(new Font(15));
		btn2.setLayoutX(310);
		btn4.setVisible(false);
		btn5.setVisible(false);
		btn6.setVisible(false);
		btn7.setVisible(false);
		btn8.setVisible(false);
		img1.setImage(image);
		img2.setImage(image);
		img3.setImage(image);
		img4.setVisible(false);
		img5.setVisible(false);
		img6.setVisible(false);
		img7.setVisible(false);
		img8.setVisible(false);
	}
	public void showChicken() {
		btn1.setText("BBQ Bonanza Breast");
		btn2.setText("Cheesy Chicken");
		btn3.setText("Crispy Parmesan");
		btn1.setPrefHeight(60);
		btn2.setPrefHeight(60);
		btn3.setPrefHeight(60);
		btn1.setFont(new Font(15));
		btn2.setFont(new Font(15));
		btn3.setFont(new Font(15));
		btn2.setLayoutX(310);
		btn4.setVisible(false);
		btn5.setVisible(false);
		btn6.setVisible(false);
		btn7.setVisible(false);
		btn8.setVisible(false);
		img1.setImage(image);
		img2.setImage(image);
		img3.setImage(image);
		img4.setVisible(false);
		img5.setVisible(false);
		img6.setVisible(false);
		img7.setVisible(false);
		img8.setVisible(false);
	}
	public void showFries() {
		btn1.setText("Cheese");
		btn2.setText("Sour & Cream");
		btn3.setText("BBQ");
		btn1.setPrefHeight(60);
		btn2.setPrefHeight(60);
		btn3.setPrefHeight(60);
		btn1.setFont(new Font(15));
		btn2.setFont(new Font(15));
		btn3.setFont(new Font(15));
		btn2.setLayoutX(310);
		btn4.setVisible(false);
		btn5.setVisible(false);
		btn6.setVisible(false);
		btn7.setVisible(false);
		btn8.setVisible(false);
		img1.setImage(image);
		img2.setImage(image);
		img3.setImage(image);
		img4.setVisible(false);
		img5.setVisible(false);
		img6.setVisible(false);
		img7.setVisible(false);
		img8.setVisible(false);
	}
	public void showBurgers() {
		btn1.setText("Bite-sized Bliss");
		btn2.setText("Classic Caliber");
		btn3.setText("The Patty Palace");
		btn1.setPrefHeight(60);
		btn2.setPrefHeight(60);
		btn3.setPrefHeight(60);
		btn1.setFont(new Font(15));
		btn2.setFont(new Font(15));
		btn3.setFont(new Font(15));
		btn2.setLayoutX(310);
		btn4.setVisible(false);
		btn5.setVisible(false);
		btn6.setVisible(false);
		btn7.setVisible(false);
		btn8.setVisible(false);
		img1.setImage(image);
		img2.setImage(image);
		img3.setImage(image);
		img4.setVisible(false);
		img5.setVisible(false);
		img6.setVisible(false);
		img7.setVisible(false);
		img8.setVisible(false);
	}
	public void showPizza() {
		btn1.setText("Solo");
		btn2.setText("Barkada");
		btn3.setText("Gigante");
		btn1.setPrefHeight(60);
		btn2.setPrefHeight(60);
		btn3.setPrefHeight(60);
		btn1.setFont(new Font(15));
		btn2.setFont(new Font(15));
		btn3.setFont(new Font(15));
		btn2.setLayoutX(310);
		btn4.setVisible(false);
		btn5.setVisible(false);
		btn6.setVisible(false);
		btn7.setVisible(false);
		btn8.setVisible(false);
		img1.setImage(image);
		img2.setImage(image);
		img3.setImage(image);
		img4.setVisible(false);
		img5.setVisible(false);
		img6.setVisible(false);
		img7.setVisible(false);
		img8.setVisible(false);
	}
	public void showDesAndBev() {
		btn1.setText("Strawberry Shortcake");
		btn2.setText("Banana Bread");
		btn3.setText("Blueberry Cheesecake");
		btn4.setText("Brownies");
		btn1.setPrefHeight(60);
		btn2.setPrefHeight(60);
		btn3.setPrefHeight(60);
		btn4.setPrefHeight(60);
		btn1.setFont(new Font(15));
		btn2.setFont(new Font(15));
		btn3.setFont(new Font(15));
		btn4.setFont(new Font(15));
		btn2.setLayoutX(310);
		btn5.setVisible(false);
		btn6.setVisible(false);
		btn7.setVisible(false);
		btn8.setVisible(false);
		img1.setImage(image);
		img2.setImage(image);
		img3.setImage(image);
		img4.setImage(image);
		img5.setVisible(false);
		img6.setVisible(false);
		img7.setVisible(false);
		img8.setVisible(false);
	}
	
	//HELPER METHODS
	public void changeScene(ActionEvent event, String page) throws IOException, SQLException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(page));
		root = loader.load();

		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		if(isHomeBtn) {
			HomeController homePage = loader.getController();
			homePage.setName(dbName);
			homePage.displayName(0);
			homePage.setHasAccount(hasAccount);
		}
		else if(isOrderBtn) {
			OrderController orderPage = loader.getController();
			orderPage.setOrders(orderList);
			orderPage.setName(dbName);
			orderPage.displayName();
			orderPage.setHasAccount(hasAccount);
		}
		else{
			SideBarItemsController sideBarPage = loader.getController();
			if(added) {
				sideBarPage.setOrders(orderList);
				added = false;
			}
			sideBarPage.setName(dbName);
			sideBarPage.displayName();
			sideBarPage.setHasAccount(hasAccount);
			
		}
		
	}
	private String recordButtonClick(Button clickedBtn) {
       
		String newClickedBtn = clickedBtn.getText();
		if(previousClickedBtn.equals("")) {
			quantity++;
			qtyTextField.setText(String.valueOf(quantity));
		}
		else if (previousClickedBtn.equals(newClickedBtn)) {
		  quantity++;
		  qtyTextField.setText(String.valueOf(quantity));
		}
		else {
			quantity = 1;
			qtyTextField.setText(String.valueOf(quantity));
		}
		// Update previousClickedBtn for next comparison
		previousClickedBtn = newClickedBtn;
		System.out.println(newClickedBtn);
		qty = String.valueOf(quantity);
		selected = true;
		return foodName = newClickedBtn;
	}
	
	
 
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setBtnSize();
		showBtns(false);
		
		addToCartBtn.setOnAction(event ->{
			if(selected) {
				OrderData orderData = new OrderData();
				orderData.setFoodName(foodName);
				orderData.setPrice(orderData.getPrice(foodName));
				orderData.setQty(qty);
				orderList.add(orderData);
				selected = false;
				added = true;
				qtyTextField.setText("0");
				quantity = 0;
			}else {
				showAlert("Please select an item", AlertType.ERROR);
			}
			
		});
		
		plusBtn.setOnMouseClicked(event ->{
			quantity++;
			qtyTextField.setText(String.valueOf(quantity));
			System.out.println(quantity);
		});
		minusBtn.setOnMouseClicked(event ->{
			
			if(quantity <= 0) {
				qtyTextField.setText("0");
			} 
			else {
				quantity--;
				qtyTextField.setText(String.valueOf(quantity));
				System.out.println(qtyTextField.getText());
			} 
		});
		
		
		backBtn.setOnAction(event -> {
			try {
				clicked = false; 
				orderBtn(event);
		} catch (IOException | SQLException e) {e.printStackTrace();}});
		btn1.setOnAction(event ->{
			if(!clicked) {
				showBtns(true);
				headerLabel.setText(btn1.getText());
	    		showBestSellers();
	    		clicked = true;
			}
			else {
				recordButtonClick(btn1);
			}
		
    	});
    	
		btn2.setOnAction(event ->{
			if(!clicked) {
				showBtns(true);
				headerLabel.setText(btn2.getText());
	    		showNewProducts();
	    		clicked = true;
			}
			else {
				recordButtonClick(btn2);
			}
    	});
    	
		btn3.setOnAction(event ->{
			if(!clicked) {
				showBtns(true);
				headerLabel.setText(btn3.getText());
	    		showComboMeals();
	    		clicked = true;
			}
			else {
				recordButtonClick(btn3);
			}
    	});
    	
		btn4.setOnAction(event ->{
			if(!clicked) {
				showBtns(true);
				headerLabel.setText(btn4.getText());
	    		showChicken();
	    		clicked = true;
			}
			else {
				recordButtonClick(btn4);
			}
    	});
    	
		btn5.setOnAction(event ->{
			if(!clicked) {
				showBtns(true);
				headerLabel.setText(btn5.getText());
	    		showFries();
	    		clicked = true;
			}
			else {
				recordButtonClick(btn5);
			}
    	});
    	
		btn6.setOnAction(event ->{
			if(!clicked) {
				showBtns(true);
				backBtn.setVisible(true);
				headerLabel.setText(btn6.getText());
	    		showBurgers();
	    		clicked = true;
			}
			else {
				recordButtonClick(btn6);
			}
    	});
		
		btn7.setOnAction(event ->{
			if(!clicked) {
				showBtns(true);
				headerLabel.setText(btn7.getText());
	    		showPizza();
	    		clicked = true;
			}
			else {
				recordButtonClick(btn7);
			}
    	});
    	
		btn8.setOnAction(event ->{
			if(!clicked) {
				showBtns(true);
				headerLabel.setText(btn8.getText());
	    		showDesAndBev();
	    		clicked = true;
			}
			else {
				recordButtonClick(btn8);
			}
    	});
    	viewCartBtn.setOnAction(event ->{try {showCart(event);} catch (IOException | SQLException e) {e.printStackTrace();}});
		
		
		slider.setTranslateX(-200);
		menu.setOnMouseClicked(event -> {
			TranslateTransition slide = new TranslateTransition();
			slide.setDuration(Duration.seconds(0.4));
			slide.setNode(slider);
			
			slide.setToX(0);
			slide.play();
			
			slider.setTranslateX(-200);
			
			slide.setOnFinished((ActionEvent e) ->{
				menu.setVisible(false);
				menuClose.setVisible(true);
			});
		
		});
		
		menuClose.setOnMouseClicked(event -> {
			TranslateTransition slide = new TranslateTransition();
			slide.setDuration(Duration.seconds(0.4));
			slide.setNode(slider);
			
			slide.setToX(-200);
			slide.play();
			
			slider.setTranslateX(0);
			
			slide.setOnFinished((ActionEvent e) ->{
				menu.setVisible(true);
				menuClose.setVisible(false);
			});
			
		});
		
	}
	
	public void showBtns(boolean toggle) {
		qtyTextField.setVisible(toggle);
		minusBtn.setVisible(toggle);
		plusBtn.setVisible(toggle);
		addToCartBtn.setVisible(toggle);
		viewCartBtn.setVisible(toggle);
		backBtn.setVisible(toggle);
	}
	public void setBtnSize() {
		btn1.setPrefWidth(140);
		btn1.setPrefHeight(40);	
		btn2.setPrefWidth(140);
		btn2.setPrefHeight(40);	
		btn3.setPrefWidth(140);
		btn3.setPrefHeight(40);	
		btn4.setPrefWidth(140);
		btn4.setPrefHeight(40);	
		btn5.setPrefWidth(140);
		btn5.setPrefHeight(40);	
		btn6.setPrefWidth(140);
		btn6.setPrefHeight(40);	
		btn7.setPrefWidth(140);
		btn7.setPrefHeight(40);	
		btn8.setPrefWidth(210);
		btn8.setPrefHeight(40);	
	}
	public void setOrders(List<OrderData> orderList) {
		this.orderList = orderList;
	}
	public void setHasAccount(boolean hasAccount) {
		this.hasAccount = hasAccount;
	}
	public void setName(String dbName) {
		this.dbName = dbName;
	}
	
	private void showAlert(String contentText, AlertType alertType) {

        Alert alert = new Alert(alertType);
        alert.setTitle("Notice");
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        alert.show();
  
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
            	Platform.runLater(() -> {
                   alert.close();
                });
                
                timer.cancel(); // Cancel the timer after closing the alert
            }
        }, 2 * 1000);
    }
}
