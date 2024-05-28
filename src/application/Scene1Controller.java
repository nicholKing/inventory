package application;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Scene1Controller {
	
    String password;
	String username = "";
	boolean hasCorrectPassword = false;
	boolean hasCorrectUsername = false;
	boolean usernameFound = false;
	boolean passwordFound = false;
	boolean hasCorrectName = false;
    boolean hasAccount = false;
	@FXML
	TextField usernameField;
	@FXML
	PasswordField pass_hidden;
	@FXML
	TextField pass_text;
	@FXML
	CheckBox pass_toggle;
	@FXML
	Button orderGuest;
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	Connection con;
	public void Connect() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
			con = DriverManager.getConnection("jdbc:mysql://localhost/inventory", "root", "");
		} catch (SQLException | ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void login(ActionEvent event) throws IOException {
		Connect();
		checkUsernameField();
		if(hasCorrectUsername) {
			checkPasswordField();
			hasCorrectUsername = false;
		}
		if(hasCorrectPassword) {
			hasCorrectPassword = false;
			hasAccount = true;
			changeScene(event);
		}
	}
	public void signUp(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
		Parent root = loader.load();
		
		SignUpController signUpPage = loader.getController();
        signUpPage.Connect();

		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
	}
	
	
	public void checkUsernameField() throws IOException{
		PreparedStatement pst;
		ResultSet rs;
		username = usernameField.getText();

		//IF THE USERNAME IS EMPTY
		if(username.isEmpty()) showAlert("Please enter your username", AlertType.ERROR); 
		
		else if(username.length() < 8) showAlert("Username must be atleast 8 characters", AlertType.ERROR);
		
		else {
			try {
				pst = con.prepareStatement("SELECT * FROM user_tbl");
				rs = pst.executeQuery();
				while(rs.next()) {
					String uname = rs.getString("username");
					if((username.equals(uname))) {
					
						hasCorrectUsername = true;
						usernameFound = true;
						return;
					}
				}
				if(usernameFound == false) {
					showAlert("Username does not exist", AlertType.ERROR);
					hasCorrectUsername = false;
				}
				usernameFound = false;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		
		}
	}

	public void checkPasswordField() throws IOException{
		PreparedStatement pst;
		ResultSet rs;
		password = passwordValue();
		
		//IF THE PASSWORD IS EMPTY
		if(password.isEmpty()) showAlert("Please enter your password", AlertType.ERROR);
		
		else {
			try {
				pst = con.prepareStatement("SELECT * FROM user_tbl");
				rs = pst.executeQuery();
				while(rs.next()) {
					String pword = rs.getString("password");
					if((password.equals(pword))) {
						hasCorrectPassword = true;
						passwordFound = true;
						return;
					}
				}
				if(passwordFound == false) {
					showAlert("Incorrect Password", AlertType.ERROR);
					hasCorrectPassword = false;
				}
				passwordFound = false;
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
	public void togglevisiblePassword(ActionEvent event) throws IOException{
	    if (pass_toggle.isSelected()) {
	        pass_text.setText(pass_hidden.getText());
	        pass_text.setVisible(true);
	        pass_hidden.setVisible(false);
	        return;
	    }
	    pass_hidden.setText(pass_text.getText());
	    pass_hidden.setVisible(true);
	    pass_text.setVisible(false);
	}
	public void initialize(URL location, ResourceBundle resources) throws IOException {
	    this.togglevisiblePassword(null);
	    
	}
	private String passwordValue() {
	    return pass_toggle.isSelected()?
	       pass_text.getText(): pass_hidden.getText();
	}
	public void changeScene(ActionEvent event) throws IOException {
		
		String page;
		if(username.equals("Admin123")) {
			page = "AdminSide.fxml";
		}
		else if(username.equals("staff")) {
			page = "StaffSide.fxml";
		}
		else {
			page = "Scene2.fxml";
		}
		
		FXMLLoader loader = new FXMLLoader(getClass().getResource(page));
		root = loader.load();
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		Scene2Controller homePage = loader.getController();
		homePage.setHasAccount(hasAccount);
	}
	
	
}
