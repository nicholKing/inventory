package application;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;
import application.OrderData;
import javafx.animation.PauseTransition;
import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.Duration;

public class SideBarItemsController implements Initializable{
	
	
	@FXML
	private Label nameLabel;
    @FXML
    private AnchorPane slider;
    @FXML
    private Label menuClose;
    @FXML
    private Label menu;
    @FXML
    private VBox orderLayout;
    @FXML
    private Label totalPriceLabel;
    @FXML
    private VBox vBox;
    @FXML
    private Button deleteBtn;
    @FXML
    private Button confirmBtn;
   
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	List<OrderData> orderList = new ArrayList<>();
	OrderData orderData = new OrderData();
	
	String name;
	String username = "";
	String orderPage = "OrderPage.fxml";
    String homePage = "HomePage.fxml";
    String accPage = "AccountDetails.fxml";
    String cartPage = "MyCart.fxml";
    String tablePage = "TablePage.fxml";
    String rewardsPage = "RewardsPage.fxml";
    String query = "SELECT name FROM user_tbl WHERE id = ?";
    String dbName;
    
	boolean hasAccount = false;
	boolean isHomeBtn = false;
	boolean isOrderBtn = false;
	boolean isTableBtn = false;
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	//TOP BUTTONS
	public void homeBtn(ActionEvent event) throws IOException, SQLException {
			System.out.println("Home");
			isHomeBtn = true;
			changeScene(event, homePage);
	}
	public void orderBtn(ActionEvent event) throws IOException, SQLException {
			
			System.out.println("Order");
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
			System.out.println("hasAccount?: " + hasAccount);
		if(hasAccount) {changeScene(event, accPage);}
			else {showAlert("Login or register to edit your information.", AlertType.INFORMATION);}
	}
	public void showCart(ActionEvent event) throws IOException, SQLException {
		changeScene(event, cartPage);
	}
	public void showTable(ActionEvent event) throws IOException, SQLException {
			isTableBtn = true;
			changeScene(event, tablePage);
	}
	public void showRewards(ActionEvent event) throws IOException, SQLException {
			if(hasAccount) {changeScene(event, rewardsPage);}
			else {showAlert("Login or register to unlock exciting rewards!", AlertType.INFORMATION);}
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
	
	//CART SYSTEM
	private List<OrderData> sortOrders() {
		List<OrderData> mergedList = new ArrayList<>();

	    if (orderList.isEmpty()) {return mergedList;}
	    
	    // Iterate through the orders
	    for (OrderData currentOrder : orderList) {
	        boolean found = false;

	        // Check if the current order has the same food name as an existing order in mergedList
	        for (OrderData mergedOrder : mergedList) {
	            if (currentOrder.getFoodName().equals(mergedOrder.getFoodName())) {
	                // If the same food item is found, update the quantity
	                int totalQuantity = Integer.parseInt(mergedOrder.getQty()) + Integer.parseInt(currentOrder.getQty());
	                mergedOrder.setQty(String.valueOf(totalQuantity));
	                found = true;
	                break;
	            }
	        }
	        // If the food item is not found in mergedList, add it
	        if (!found) {mergedList.add(currentOrder);}
	    }
	    return mergedList;
	}
	private void displayOrders() {
		orderList = sortOrders();
        // Display each order in the orderList
        for (OrderData order : orderList) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("OrderItem.fxml"));

            try {
            	HBox hBox = loader.load();
            	OrderDataController orderDataController = loader.getController();
                deleteBtn = orderDataController.getButton();
                orderDataController.setData(order);
                orderLayout.getChildren().add(hBox);
                deleteBtn.setOnAction(e ->{
                	orderLayout.getChildren().remove(hBox);
                	orderList.remove(order);
                	totalPriceLabel.setText("Total Price: " + String.valueOf(getTotalPrice()));
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
	public void backBtn(ActionEvent event) throws IOException, SQLException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("OrderPage.fxml"));
		root = loader.load();

		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		OrderController orderPage = loader.getController();
		orderPage.setHasAccount(hasAccount);
		orderPage.setName(dbName);
		orderPage.displayName();
		System.out.println(orderList);
		orderPage.setOrders(orderList);
	}
	
	//HELPER METHODS
	public void Connect() {
			try {
				Class.forName("com.mysql.jdbc.Driver");
				con = DriverManager.getConnection("jdbc:mysql://localhost/inventory", "root", "");
			} catch (SQLException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
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
			}else if(isOrderBtn) {
				OrderController orderPage = loader.getController();
				orderPage.setOrders(orderList);
				orderPage.setHasAccount(hasAccount);
				orderPage.setName(dbName);
				orderPage.displayName();
			}else if(isTableBtn) {
				TableReservationController tablePage = loader.getController();
				tablePage.setOrderList(orderList);
				tablePage.setHasAccount(hasAccount);
				tablePage.setName(dbName);
				tablePage.displayName();
			}
			else {
				SideBarItemsController sideBarItems = loader.getController();
				sideBarItems.setOrders(orderList);
				sideBarItems.setHasAccount(hasAccount);
				sideBarItems.setName(dbName);
				sideBarItems.displayName();
			}
			
			
	}
	private void showAlert(String contentText, AlertType alertType) {

        Alert alert = new Alert(alertType);
        alert.setHeaderText("");
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
        }, 1 * 1000);
    }
	
	//GETTERS AND SETTERS 
	public void setHasAccount(boolean hasAccount) {
			this.hasAccount = hasAccount;
	}
	public void setName(String dbName) {
		this.dbName = dbName;
	}
	public void setOrders(List<OrderData> orderList) {
		this.orderList = orderList;
		totalPriceLabel.setText("Total Price: " + String.valueOf(getTotalPrice()));
		displayOrders();
	}
	public void setOrderList(List<OrderData> orderList) {
		this.orderList = orderList;
	}
	public int getTotalPrice() {
		int totalPrice = 0;
        for (OrderData order : orderList) {
        	 totalPrice += order.getPrice(order.getFoodName()) * Integer.parseInt(order.getQty());
        }
        return totalPrice;
    }
	
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
			
			confirmBtn.setOnMouseClicked(event ->{
				Alert alert = new Alert(AlertType.CONFIRMATION);
				alert.setTitle("Checkout");
				alert.setHeaderText("");
				alert.setContentText("Are you sure you want to proceed?");
				
				if(alert.showAndWait().get() == ButtonType.OK) {
					System.out.println("Proceed");
				}
			});
	
        	Connect();
        
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
	
	
	
}
