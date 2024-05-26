package application;

import java.io.IOException;
import java.net.URL;
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
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class Scene1Controller {
	
    private AccountDatabase accountDatabase;
	
	public List<String> usernameList = new ArrayList<>();
	public List<String> passwordList = new ArrayList<>();
    public List<String> nameList = new ArrayList<>();
    String password;
	String username;
	boolean hasCorrectPassword = false;
	boolean hasCorrectUsername = false;
	boolean usernameFound = false;
	boolean passwordFound = false;
	boolean hasCorrectName = false;
    
	@FXML
	TextField usernameField;
	@FXML
	PasswordField pass_hidden;
	@FXML
	TextField pass_text;
	@FXML
	CheckBox pass_toggle;
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	public void setDatabase(AccountDatabase accountDatabase) {
		System.out.println("Database at login Page: " + accountDatabase.nameList);
		this.accountDatabase = accountDatabase;
	}
	public void login(ActionEvent event) throws IOException {
		
		checkUsernameField();
		if(hasCorrectUsername) {
			checkPasswordField();
			hasCorrectUsername = false;
		}
		if(hasCorrectPassword) {
			hasCorrectPassword = false;
			changeScene(event);
		}
		
	}
	public void signUp(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("SignUp.fxml"));
		Parent root = loader.load();
		
		SignUpController signUpPage = loader.getController();
        signUpPage.setDatabase(accountDatabase);

		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
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
		// Load the FXML file for the registration form
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene2.fxml"));
		root = loader.load();
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
		Scene2Controller homePage = loader.getController();
		homePage.setAccountDatabase(accountDatabase);
	}
	public void checkUsernameField() throws IOException{
		username = usernameField.getText();
		usernameList = accountDatabase.getUsernames();

		//IF THE USERNAME IS EMPTY
		if(username.isEmpty()) showAlert("Please enter your username"); 
		
		else if(username.length() < 8) showAlert("Username must be atleast 8 characters");
		
		while(!usernameFound) {
			
			for(int i = 0; i < usernameList.size();) {
				if(username.equals(usernameList.get(i))) {
					hasCorrectUsername = true;
					usernameFound = true;
					break;
				}
				else {
					hasCorrectUsername = false;
				}
			break;
		}
		
		//IF THE USERNAME IS NOT FOUND 
			if(!usernameFound && username.length() >= 8) {
				showAlert("Username does not exist");
					hasCorrectUsername = false;
					return;
				}
			break;
		}
		usernameFound = false; //reset to enter the while loop again
		return;
	}

	public void checkPasswordField() throws IOException{
		password = passwordValue();
		passwordList = accountDatabase.getPasswords();
		
		//IF THE PASSWORD IS EMPTY
		if(password.isEmpty()) showAlert("Please enter your password");
		
		else {
			while(!passwordFound) {
				for(int i = 0; i < passwordList.size(); i++) {
					if(accountDatabase.loginUser(username, password)) {
						accountDatabase.indexer(username, password);
						hasCorrectPassword = true;
						passwordFound = true;
						break;
					}
					else {
						hasCorrectPassword = false;
					}
				}

				if(!passwordFound) {
					showAlert("Incorrect Password");
					hasCorrectPassword = false;
					return;
				}
			}
			passwordFound = false; //reset to enter the while loop again
		}
	}
	private void showAlert(String contentText) {
		System.out.println("GUmagana");
        Alert alert = new Alert(AlertType.ERROR);
        alert.setHeaderText("Error");
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
