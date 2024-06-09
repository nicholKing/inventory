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
    private ImageView menu;
    @FXML
    private ImageView menuClose;
    @FXML
    private ImageView account;
    @FXML
    private ImageView accountClose;
    @FXML
    private AnchorPane slider2;
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
    private ImageView img9;
    @FXML
    private ImageView img10;
    @FXML
    private ImageView plusBtn;
    @FXML
    private ImageView minusBtn;
    @FXML
    private Button cat1;
    @FXML
    private Button cat2;
    @FXML
    private Button cat3;
    @FXML
    private Button cat4;
    @FXML
    private Button cat5;
    @FXML
    private Button cat6;
    @FXML
    private Button cat7;
    @FXML
    private Button cat8;
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
    private Button btn9;
    @FXML
    private Button btn10;
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
    @FXML
    private Label priceLabel9;
    @FXML
    private Label priceLabel10;
    @FXML
    private Label foodNameLabel;
    @FXML
    private Label priceLabel;
    @FXML
    private Button prefBtn1;
    @FXML
    private Button prefBtn2;
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
    boolean isTableBtn = false;
    boolean isAccBtn = false;
    boolean isRewardBtn = false;
    
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
	String qty; // for data transferring
	boolean clicked = false;
	boolean added = false;
	boolean selected = false;
	boolean pref1 = false;
	boolean pref2 = false;
	int quantity = 0; //for counting
	int price;
	char category;
   
	//TOP BUTTONS
	public void homeBtn(ActionEvent event) throws IOException, SQLException {
		isHomeBtn = true;
		changeScene(event, homePage);
	}
	public void orderBtn(ActionEvent event) throws IOException, SQLException {
		this.setOrders(orderList);
		isOrderBtn = true;
		changeScene(event, orderPage);
	}
	public void signIn(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
	    root = loader.load();
		if(hasAccount) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Logout");
			alert.setHeaderText("You're about to logout");
			alert.setContentText("Are you sure you want to logout?");
			if(alert.showAndWait().get() == ButtonType.OK) {
				stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			    scene = new Scene(root);
			    stage.setScene(scene);
			    stage.show();
			    hasAccount = false;
			}
		}
		else {
	    Scene1Controller loginPage = loader.getController();
	    loginPage.Connect();
	    stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		}
	}
	public void signUp(ActionEvent event) throws IOException, ClassNotFoundException, SQLException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
	    root = loader.load();
		if(hasAccount) {
			Alert alert = new Alert(AlertType.CONFIRMATION);
			alert.setTitle("Logout");
			alert.setHeaderText("You're about to logout");
			alert.setContentText("Are you sure you want to logout?");
			if(alert.showAndWait().get() == ButtonType.OK) {
				stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			    scene = new Scene(root);
			    stage.setScene(scene);
			    stage.show();
			    hasAccount = false;
			}
			
		}
		else {
		SignUpController signUpPage = loader.getController();
		signUpPage.Connect();
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		}
	}
	
	//SIDE BUTTONS
	public void showAccount(ActionEvent event) throws IOException, SQLException {
		if(hasAccount) {
			isAccBtn = true;
			changeScene(event, "AccountDetails.fxml");
		}
		else {showAlert("Login or register to edit your information", AlertType.INFORMATION);}
	}
	public void showCart(ActionEvent event) throws IOException, SQLException {
		isCartBtn = true;
		changeScene(event, "MyCart.fxml");
	}
	public void showTable(ActionEvent event) throws IOException, SQLException {
		isTableBtn = true;
		changeScene(event, "TablePage.fxml");
		}

	public void showRewards(ActionEvent event) throws IOException, SQLException {
		if(hasAccount) {
			isRewardBtn = true;
			changeScene(event, rewardsPage);
		}
		else {
			showAlert("Create an account to unlock exciting rewards!", AlertType.INFORMATION);
		}
	}
	public void logout(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
		root = loader.load();
		
		if(!hasAccount) {
			stage = (Stage)((Node)event.getSource()).getScene().getWindow();
			scene = new Scene(root);
			stage.setScene(scene);
			stage.show();
			Scene1Controller loginPage = loader.getController();
		}else {
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
	}
	
	//FOOD METHODS
	public void showRiceMeals() {
		btn1.setText("Cordon Bleu");
		btn2.setText("Pork Sisig");
		btn3.setText("Lechon Kawali");
		btn4.setText("Pork Belly");
		btn5.setText("Grilled Chicken");
		btn6.setText("Beef Stir-fry");
		btn7.setText("Buffalo Wings");
		
		priceLabel1.setText("₱ 269"); 
		priceLabel2.setText("₱ 259");
		priceLabel3.setText("₱ 259");
		priceLabel4.setText("₱ 269"); 
		priceLabel5.setText("₱ 259");
		priceLabel6.setText("₱ 259");
		priceLabel7.setText("₱ 259");
		
		img1.setImage(image);
		img2.setImage(image);
		img3.setImage(image);
		img4.setImage(image);
		img5.setImage(image);
		img6.setImage(image);
		img7.setImage(image);
		
		setVisibility(false);
		
		btn1.setVisible(true);btn2.setVisible(true);btn3.setVisible(true);btn4.setVisible(true);btn5.setVisible(true);btn6.setVisible(true);btn7.setVisible(true);
		img1.setVisible(true);img2.setVisible(true);img3.setVisible(true);img4.setVisible(true);img5.setVisible(true);img6.setVisible(true);img7.setVisible(true);
		priceLabel1.setVisible(true);priceLabel2.setVisible(true);priceLabel3.setVisible(true);priceLabel4.setVisible(true);
		priceLabel5.setVisible(true);priceLabel6.setVisible(true);priceLabel7.setVisible(true);
	}
	public void showPasta() {
		btn1.setText("Carbonara");
		btn2.setText("Spaghetti");
		btn3.setText("Palabok");
	
		priceLabel1.setText("₱ 269"); 
		priceLabel2.setText("₱ 259");
		priceLabel3.setText("₱ 259");
	
		img1.setImage(image);
		img2.setImage(image);
		img3.setImage(image);

		setVisibility(false);
		
		btn1.setVisible(true);btn2.setVisible(true);btn3.setVisible(true);
		img1.setVisible(true);img2.setVisible(true);img3.setVisible(true);
		priceLabel1.setVisible(true);priceLabel2.setVisible(true);priceLabel3.setVisible(true);
		
	}
	public void showCakes() {
		btn1.setText("Bluberry Cake");
		btn2.setText("Lava Cake");
		btn3.setText("Carrot Cake");
		btn4.setText("Red Velvet");
		
		priceLabel1.setText("₱ 159"); 
		priceLabel2.setText("₱ 159");
		priceLabel3.setText("₱ 159");
		priceLabel4.setText("₱ 159");
		
		img1.setImage(image);
		img2.setImage(image);
		img3.setImage(image);
		img4.setImage(image);
		
		setVisibility(false);
		
		btn1.setVisible(true);btn2.setVisible(true);btn3.setVisible(true);btn4.setVisible(true);
		img1.setVisible(true);img2.setVisible(true);img3.setVisible(true);img4.setVisible(true);
		priceLabel1.setVisible(true);priceLabel2.setVisible(true);priceLabel3.setVisible(true);priceLabel4.setVisible(true);
		
	}
	public void showBurger() {
		btn1.setText("Hamburger");
		btn2.setText("Cheeseburger");
		btn3.setText("Elk burger");
		btn4.setText("Veggie burger");
	
		priceLabel1.setText("₱ 269"); 
		priceLabel2.setText("₱ 259");
		priceLabel3.setText("₱ 259");
		priceLabel4.setText("₱ 269"); 
	
		img1.setImage(image);
		img2.setImage(image);
		img3.setImage(image);
		img4.setImage(image);
	
		setVisibility(false);

		btn1.setVisible(true);btn2.setVisible(true);btn3.setVisible(true);btn4.setVisible(true);
		img1.setVisible(true);img2.setVisible(true);img3.setVisible(true);img4.setVisible(true);
		priceLabel1.setVisible(true);priceLabel2.setVisible(true);priceLabel3.setVisible(true);priceLabel4.setVisible(true);
	
	}
	public void showCoffee() {
		btn1.setText("Coffee1");
		btn2.setText("Coffee2");
		btn3.setText("Coffee3");
		btn4.setText("Coffee4");
		btn5.setText("Coffee5");
		btn6.setText("Coffee6");
		btn7.setText("Coffee7");
		btn8.setText("Coffee8");
		btn9.setText("Coffee9");
		btn10.setText("Coffee10");
		
		priceLabel1.setText("₱ 269"); 
		priceLabel2.setText("₱ 259");
		priceLabel3.setText("₱ 259");
		priceLabel4.setText("₱ 269"); 
		priceLabel5.setText("₱ 259");
		priceLabel6.setText("₱ 259");
		priceLabel7.setText("₱ 259");
		priceLabel8.setText("₱ 269"); 
		priceLabel9.setText("₱ 259");
		priceLabel10.setText("₱ 259");
		
		img1.setImage(image);
		img2.setImage(image);
		img3.setImage(image);
		img4.setImage(image);
		img5.setImage(image);
		img6.setImage(image);
		img7.setImage(image);
		img8.setImage(image);
		img9.setImage(image);
		img10.setImage(image);
		
		setVisibility(true);
	}
	public void showFrappe() {
		btn1.setText("Frappe1");
		btn2.setText("Frappe2");
		btn3.setText("Frappe3");
		btn4.setText("Frappe4");
		btn5.setText("Frappe5");
		btn6.setText("Frappe6");
		btn7.setText("Frappe7");
		btn8.setText("Frappe8");
		btn9.setText("Frappe9");
		btn10.setText("Frappe10");
		
		priceLabel1.setText("₱ 269"); 
		priceLabel2.setText("₱ 259");
		priceLabel3.setText("₱ 259");
		priceLabel4.setText("₱ 269"); 
		priceLabel5.setText("₱ 259");
		priceLabel6.setText("₱ 259");
		priceLabel7.setText("₱ 259");
		priceLabel8.setText("₱ 269"); 
		priceLabel9.setText("₱ 259");
		priceLabel10.setText("₱ 259");
		
		img1.setImage(image);
		img2.setImage(image);
		img3.setImage(image);
		img4.setImage(image);
		img5.setImage(image);
		img6.setImage(image);
		img7.setImage(image);
		img8.setImage(image);
		img9.setImage(image);
		img10.setImage(image);
		
		setVisibility(true);
	}
	public void showTea() {
		btn1.setText("Tea1");
		btn2.setText("Tea2");
		btn3.setText("Tea3");
		btn4.setText("Tea4");
		btn5.setText("Tea5");
		btn6.setText("Tea6");
		btn7.setText("Tea7");

		priceLabel1.setText("₱ 269"); 
		priceLabel2.setText("₱ 259");
		priceLabel3.setText("₱ 259");
		priceLabel4.setText("₱ 269"); 
		priceLabel5.setText("₱ 259");
		priceLabel6.setText("₱ 259");
		priceLabel7.setText("₱ 259");
	
		
		img1.setImage(image);
		img2.setImage(image);
		img3.setImage(image);
		img4.setImage(image);
		img5.setImage(image);
		img6.setImage(image);
		img7.setImage(image);
	
		
		setVisibility(true);
		
		btn8.setVisible(false);btn9.setVisible(false);btn10.setVisible(false);
		img8.setVisible(false);img9.setVisible(false);img10.setVisible(false);
		priceLabel8.setVisible(false);priceLabel9.setVisible(false);priceLabel10.setVisible(false);
	}
	public void showAppetizers() {
		btn1.setText("Snacks");
		btn2.setText("Mojos");
		btn3.setText("Nachos");
		btn4.setText("Fries");
	
		priceLabel1.setText("₱ 109"); 
		priceLabel2.setText("₱ 109");
		priceLabel3.setText("₱ 109");
		priceLabel4.setText("₱ 109"); 
	
		img1.setImage(image);
		img2.setImage(image);
		img3.setImage(image);
		img4.setImage(image);
	
		setVisibility(false);

		btn1.setVisible(true);btn2.setVisible(true);btn3.setVisible(true);btn4.setVisible(true);
		img1.setVisible(true);img2.setVisible(true);img3.setVisible(true);img4.setVisible(true);
		priceLabel1.setVisible(true);priceLabel2.setVisible(true);priceLabel3.setVisible(true);priceLabel4.setVisible(true);
		
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
			homePage.setOrderList(orderList);
			homePage.setHasAccount(hasAccount);
			homePage.setName(dbName);
			homePage.displayName(0);
		}
		else if(isOrderBtn) {
			OrderController orderPage = loader.getController();
			orderPage.setOrders(orderList);
			orderPage.setName(dbName);
			orderPage.setHasAccount(hasAccount);
		}else if(isTableBtn) {
			TableReservationController tablePage = loader.getController();
			tablePage.setHasAccount(hasAccount);
			tablePage.setName(dbName);
		}else if(isAccBtn) {
			AccountDetailsController accPage = loader.getController();
			accPage.setOrders(orderList);
			accPage.setName(dbName);
			accPage.displayName();
			accPage.setHasAccount(hasAccount);
		}else if(isRewardBtn) {
			RewardsController rewardPage = loader.getController();
			rewardPage.setOrderList(orderList);
			rewardPage.setHasAccount(hasAccount);
			rewardPage.setName(dbName);
			rewardPage.displayName();
		}else {
			CartController cartPage = loader.getController();
			cartPage.setOrders(orderList);
			cartPage.setHasAccount(hasAccount);
			cartPage.setName(dbName);
			cartPage.displayName();
		}
		
	}
	private void setSlides() {
		menu.setVisible(true);
		menuClose.setVisible(false);
		
		
		menu.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(-200);
            slide.play();

            slider.setTranslateX(0);

            slide.setOnFinished((ActionEvent e)-> {
                menu.setVisible(false);
                menuClose.setVisible(true);
            });
        });

        menuClose.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider);

            slide.setToX(0);
            slide.play();

            slider.setTranslateX(-200);

            slide.setOnFinished((ActionEvent e)-> {
                menu.setVisible(true);
                menuClose.setVisible(false);
            });
        });

       	slider2.setTranslateX(225);
       	accountClose.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider2);

            slide.setToX(0);
            slide.play();

            slider2.setTranslateX(225);

            slide.setOnFinished((ActionEvent e)-> {
                account.setVisible(false);
                accountClose.setVisible(true);
            });
        });

       account.setOnMouseClicked(event -> {
            TranslateTransition slide = new TranslateTransition();
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider2);

            slide.setToX(225);
            slide.play();

            slider2.setTranslateX(0);

            slide.setOnFinished((ActionEvent e)-> {
            	clicked = false;
            	account.setVisible(true);
                accountClose.setVisible(false);
            });
        });
	}

	private String recordButtonClick(Button clickedBtn) {
		displayChoices();
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
		qty = String.valueOf(quantity);
		selected = true;
		foodNameLabel.setText(newClickedBtn);
		OrderData orderData = new OrderData();
    	priceLabel.setText("Price: ₱" +  String.valueOf(orderData.getPrice(newClickedBtn)));
		return foodName = newClickedBtn;
	}
	
	public void slideWindow() {
		
    	
		 slider2.setTranslateX(225);
            TranslateTransition slide = new TranslateTransition();
           
            slide.setDuration(Duration.seconds(0.4));
            slide.setNode(slider2);
            
            slide.setToX(0);
            slide.play();
            
            slide.setOnFinished((ActionEvent e)-> {
            	
            });
        
	}
	private void displayChoices() {
		switch(category) {
			case 'a': prefBtn1.setText("W/o drinks"); prefBtn2.setText("With drinks");
					break;
			case 'b': prefBtn1.setText("Slice"); prefBtn2.setText("Whole");
					break;
			case 'c': prefBtn1.setText("Short"); prefBtn2.setText("Tall");
					break;
			case 'd': prefBtn1.setText("Solo"); prefBtn2.setText("Family");
					break;
		}
	}
	String pref;
	public String getSizeLabel(Button prefBtn) {
		pref = prefBtn.getText();
		return pref;
	}
	private void setCategory() {
		category = 'a';
		cat1.setOnAction(event -> {
			showRiceMeals();
			category = 'a';
		});
		cat2.setOnAction(event -> {
			showPasta();
			category = 'a';
		});
		cat3.setOnAction(event -> {
			showCakes();
			category = 'b';
		});
		cat4.setOnAction(event -> {
			showBurger();
			category = 'a';
		});
		cat5.setOnAction(event -> {
			showCoffee();
			category = 'c';
		});
		cat6.setOnAction(event -> {
			showFrappe();
			category = 'c';
		});
		cat7.setOnAction(event -> {
			showTea();
			category = 'c';
		});
		cat8.setOnAction(event -> {
			showAppetizers();
			category = 'd';
		});
	}
	
	private void funcBtns() {
		prefBtn1.setOnAction(event ->{
			pref = getSizeLabel(prefBtn1);
			OrderData orderData = new OrderData();
			price = fetchPrice(foodName);
			priceLabel.setText("Price: ₱" +  String.valueOf(orderData.getPrice(foodName)));
	
		});
		prefBtn2.setOnAction(event ->{
			pref = getSizeLabel(prefBtn2);
			OrderData orderData = new OrderData(); 
			switch(category) {
				case 'a': price = fetchPrice(foodName) + 60;
							priceLabel.setText("Price: ₱" + String.valueOf(orderData.getPrice(foodName) + 60));
						break;
				case 'b': price = fetchPrice(foodName) + 700;
				priceLabel.setText("Price: ₱" + String.valueOf(orderData.getPrice(foodName) + 700));
						break;
				case 'c': price = fetchPrice(foodName) + 40;
				priceLabel.setText("Price: ₱" + String.valueOf(orderData.getPrice(foodName) + 40));
						break;
				case 'd': price = fetchPrice(foodName) + 210;
				priceLabel.setText("Price: ₱" + String.valueOf(orderData.getPrice(foodName) + 210));
						break;
			}
			
		});
		addToCartBtn.setOnAction(event ->{
			if(selected) {
				
				if (price == 0) {
					pref = getSizeLabel(prefBtn1);
		            price = fetchPrice(foodName);
		        }
				OrderData orderData = new OrderData();
				orderData.setSize(pref);
				orderData.setFoodName(foodName);
				orderData.setPrice(price);
				orderData.setQty(String.valueOf(quantity));
				orderList.add(orderData);
				selected = false;
				added = true;
				qtyTextField.setText("0");
				quantity = 0;
			}else {showAlert("Please select an item", AlertType.ERROR);}
		});
		
		plusBtn.setOnMouseClicked(event ->{
			quantity++;
			qtyTextField.setText(String.valueOf(quantity));
			System.out.println(quantity);
			selected = true;
		});
		
		minusBtn.setOnMouseClicked(event ->{
			if(quantity <= 0) {qtyTextField.setText("0");} 
			else {
				quantity--;
				qtyTextField.setText(String.valueOf(quantity));
			} 
		});
	
		btn1.setOnAction(event ->{
			if(!clicked) {
				slideWindow();
				
				clicked = true;
			}
			recordButtonClick(btn1);
			
			});
		btn2.setOnAction(event ->{
			if(!clicked) {
				slideWindow();
				clicked = true;
			}
			recordButtonClick(btn2);
			
			});
    	
		btn3.setOnAction(event ->{
			if(!clicked) {
				slideWindow();
				clicked = true;
			}
			recordButtonClick(btn3);
			
			});
    	
		btn4.setOnAction(event ->{
			if(!clicked) {
				slideWindow();
				clicked = true;
			}
			recordButtonClick(btn4);
			
			});
    	
		btn5.setOnAction(event ->{
			
			if(!clicked) {
				slideWindow();
				clicked = true;
			}
			recordButtonClick(btn5);
			
			});
    	
		btn6.setOnAction(event ->{
			
			if(!clicked) {
				slideWindow();
				clicked = true;
			}
			recordButtonClick(btn6);
			
			});
		
		btn7.setOnAction(event ->{
			if(!clicked) {
				slideWindow();
				clicked = true;
			}
			recordButtonClick(btn7);
			
			});
    	
		btn8.setOnAction(event ->{
			if(!clicked) {
				slideWindow();
				clicked = true;
			}
			recordButtonClick(btn8);
			});
		btn9.setOnAction(event ->{
			if(!clicked) {
				slideWindow();
				clicked = true;
			}
			recordButtonClick(btn9);
			});
		btn10.setOnAction(event ->{
			if(!clicked) {
				slideWindow();
				clicked = true;
			}
			recordButtonClick(btn10);
			});
    	
		viewCartBtn.setOnAction(event ->{try {showCart(event);} catch (IOException | SQLException e) {e.printStackTrace();}});
		
	}
	
	private int fetchPrice(String foodName) {
	    OrderData orderData = new OrderData();
	    return orderData.getPrice(foodName);
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		setCategory();
       	setSlides();
		setDisplay(true);
		showRiceMeals();
		funcBtns();
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
	
	public void setVisibility(boolean toggle) {
		btn1.setVisible(toggle);btn2.setVisible(toggle);btn3.setVisible(toggle);btn4.setVisible(toggle);btn5.setVisible(toggle);
		btn6.setVisible(toggle);btn7.setVisible(toggle);btn8.setVisible(toggle);btn9.setVisible(toggle);btn10.setVisible(toggle);
		img1.setVisible(toggle);img2.setVisible(toggle);img3.setVisible(toggle);img4.setVisible(toggle);img5.setVisible(toggle);
		img6.setVisible(toggle);img7.setVisible(toggle);img8.setVisible(toggle);img9.setVisible(toggle);img10.setVisible(toggle);
		priceLabel1.setVisible(toggle);priceLabel2.setVisible(toggle);priceLabel3.setVisible(toggle);priceLabel4.setVisible(toggle);priceLabel5.setVisible(toggle);
		priceLabel6.setVisible(toggle);priceLabel7.setVisible(toggle);priceLabel8.setVisible(toggle);priceLabel9.setVisible(toggle);priceLabel10.setVisible(toggle);
	}
	public void setDisplay(boolean toggle) {
		qtyTextField.setVisible(toggle);
		minusBtn.setVisible(toggle);
		plusBtn.setVisible(toggle);
		addToCartBtn.setVisible(toggle);
		viewCartBtn.setVisible(toggle);
	}
	private void showAlert(String contentText, AlertType alertType) {

		 Alert alert = new Alert(alertType);
	        alert.setTitle("Notice");
	        alert.setHeaderText(null);
	        alert.setContentText(contentText);
	        Scene scenes = alert.getDialogPane().getScene();
	        scenes.getStylesheets().add(getClass().getResource("alert.css").toExternalForm());
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
