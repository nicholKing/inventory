package application;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.TranslateTransition;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Duration;

public class Scene2Controller implements Initializable{
	
	@FXML
	private Label nameLabel;
    @FXML
    private AnchorPane slider;
    @FXML
    private Label menuClose;
    @FXML
    private Label menu;

	private Stage stage;
	private Scene scene;
	private Parent root;
	
	String name;
	String username = "";
	String orderPage = "OrderPage.fxml";
    String homePage = "Scene2.fxml";
    String accPage = "/SideBarItemsCustomer/AccountDetails.fxml";
    String query = "SELECT name FROM user_tbl WHERE id = ?";
    
	boolean hasAccount = false;
	int id = 0;
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	//TOP BUTTONS
	public void homeBtn(ActionEvent event) throws IOException {
		System.out.println("Home");
		changeScene(event, homePage);
	}
	public void orderBtn(ActionEvent event) throws IOException {
		System.out.println("Order");
		changeScene(event, orderPage);
	}
	public void promoBtn() {
		System.out.println("Promo");
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
	public void displayName(int id) throws SQLException {
		String dbName = "";
		
		if(hasAccount) {
			pst = con.prepareStatement(query);
			pst.setInt(1, id);
			rs = pst.executeQuery();
			while(rs.next()) {
				dbName = rs.getString("name");
				System.out.println(dbName);
			}
			nameLabel.setText(dbName);
		}else {
			nameLabel.setText("Guest");
		}

	}
	public void showAccount(ActionEvent event) throws IOException {
		if(hasAccount) {changeScene(event, accPage);}
		else {System.out.println("Account Details");}
	}
	public void showCart(ActionEvent event) throws IOException {
		if(hasAccount) {changeScene(event, accPage);}
		else {System.out.println("Show Cart");}
	}
	public void showTable(ActionEvent event) throws IOException {
		if(hasAccount) {changeScene(event, accPage);}
		else {System.out.println("Show Table");}
	}
	public void showRewards(ActionEvent event) throws IOException {
		if(hasAccount) {changeScene(event, accPage);}
		else {System.out.println("Points and rewards!");}
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
	public void Connect() {
		try {
			System.out.println("Database connected");
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/inventory", "root", "");
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void changeScene(ActionEvent event, String page) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource(page));
		root = loader.load();

		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	public void setHasAccount(boolean hasAccount) {
		this.hasAccount = hasAccount;
		System.out.println(hasAccount);
	}
	@Override
	public void initialize(URL arg0, ResourceBundle arg1) {
		
		Connect();
		// TODO Auto-generated method stub
		
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

