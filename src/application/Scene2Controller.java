package application;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
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
	
	String orderPage = "OrderPage.fxml";
    String homePage = "Scene2.fxml";
	boolean hasAccount = false;
	
	Connection con;
	PreparedStatement pst;
	ResultSet rs;
	
	
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
	public void homeBtn(ActionEvent event) throws IOException {
		changeScene(event, homePage);
	}
	public void orderBtn(ActionEvent event) throws IOException {
		changeScene(event, orderPage);
	}
	public void promoBtn() {
		System.out.println("Promo");
	}
	
	public void setHasAccount(boolean hasAccount) {
		this.hasAccount = hasAccount;
		System.out.println(hasAccount);
	}
	
	public void displayName(String username) {
		nameLabel.setText("Hello:");
	}
	
	public void toggleAccount() {
		
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

