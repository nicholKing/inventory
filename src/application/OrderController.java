package application;

import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javafx.animation.TranslateTransition;
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
import javafx.scene.input.MouseEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class OrderController implements Initializable{
	@FXML
    private Button bestSellersBtn;
    @FXML
    private Button burgersBtn;
    @FXML
    private Button chickenBtn;
    @FXML
    private Button comboMealsBtn;
    @FXML
    private Button dessertsBtn;
    @FXML
    private Button friesBtn;
    @FXML
    private Button logoutBtn;
    @FXML
    private Label menu;
    @FXML
    private Label menuClose;
    @FXML
    private Button newProductsBtn;
    @FXML
    private Button pizzaBtn;
    @FXML
    private AnchorPane slider;
    @FXML
    private Label headerLabel;
    @FXML 
    private Label nameLabel;
    private Stage stage;
	private Scene scene;
	private Parent root;
	String dbName;
	String orderPage = "OrderPage.fxml";
    String homePage = "HomePage.fxml";
    String accPage = "AccountDetails.fxml";
    String cartPage = "MyCart.fxml";
    String tablePage = "TablePage.fxml";
    String rewardsPage = "RewardsPage.fxml";
    
	boolean hasAccount = false;
	boolean isHomeBtn = false;
    boolean isSideBtn = false;
    boolean isOrderBtn = false;
	//CENTER BUTTONS
    public void searchItem(MouseEvent event) throws IOException {
    	System.out.println("Searching...");
    }
    
	
	//TOP BUTTONS
	public void homeBtn(ActionEvent event) throws IOException, SQLException {
		isHomeBtn = true;
		changeScene(event, homePage);
	}
	public void orderBtn(ActionEvent event) throws IOException, SQLException {
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
		else {System.out.println("Sorry this is only for person who has account");}
	}
	public void showCart(ActionEvent event) throws IOException, SQLException {
		changeScene(event, "MyCart.fxml");

	}
	public void showTable(ActionEvent event) throws IOException, SQLException {
		if(hasAccount) {changeScene(event, "Scene2.fxml");}
		else {System.out.println("Show Table");}
	}

	public void showRewards() {
		if(hasAccount) {
			System.out.println("Points and Rewards!");
		}
		else {
			System.out.println("Sorry, you need to register to an account :(");
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
			orderPage.setName(dbName);
			orderPage.displayName();
			orderPage.setHasAccount(hasAccount);
		}
		else{
			SideBarItemsController sideBarPage = loader.getController();
			sideBarPage.setName(dbName);
			sideBarPage.displayName();
			sideBarPage.setHasAccount(hasAccount);
		}
		
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		// TODO Auto-generated method stub
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
		
    	
    	chickenBtn.setOnMouseClicked(event ->{
    		System.out.println("Hi");
    	});
    	
    	comboMealsBtn.setOnMouseClicked(event ->{
    		System.out.println("Hi");
    	});
    	
    	dessertsBtn.setOnMouseClicked(event ->{
    		System.out.println("Hi");
    	});
    	
    	friesBtn.setOnMouseClicked(event ->{
    		System.out.println("Hi");
    	});
    	
    	newProductsBtn.setOnMouseClicked(event ->{
    		System.out.println("Hi");
    	});
    	
    	pizzaBtn.setOnMouseClicked(event ->{
    		System.out.println("Hi");
    	});
	}
	public void setHasAccount(boolean hasAccount) {
		this.hasAccount = hasAccount;
	}
	public void setName(String dbName) {
		this.dbName = dbName;
	}
}
