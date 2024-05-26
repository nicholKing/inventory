package application;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Timer;
import java.util.TimerTask;

import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class SignUpController {

    private AccountDatabase accountDatabase;
	
	@FXML
	TextField nameField;
	@FXML
	TextField usernameField;
	@FXML
	PasswordField pass_hidden;
	@FXML
	TextField pass_text;
	@FXML
	CheckBox pass_toggle;
	
	public List<String> usernameList = new ArrayList<>();
	public List<String> passwordList = new ArrayList<>();
	public List<String> nameList = new ArrayList<>();
	String name;
	String password;
	String username;
	int emptyIndex = -1;
	boolean hasCorrectPassword = false;
	boolean hasCorrectUsername = false;
	boolean hasCorrectName = false;
	boolean newUsername = true;
	boolean newPassword = true;
	boolean newName = true;
	boolean usernameFound = true;
	boolean passwordFound = true;
	boolean nameFound = true;
	boolean loginSuccessful = false;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	
	
	public void setDatabase(AccountDatabase accountDatabase) {
		System.out.println("Database at register Page: " + accountDatabase.nameList);
		this.accountDatabase = accountDatabase;
		
	}
	
	public void signUp(ActionEvent event) throws IOException{
			checkNameField();
			if (hasCorrectName) {
				checkUsernameField();
			}
			if(hasCorrectUsername) {
				checkPasswordField();
				
			}
			if(hasCorrectPassword) {
				checkAccount(event);
			}
	}
	public void checkNameField() {
		name = nameField.getText();
		nameList = accountDatabase.getNames();
		
		findAvailableIndex(nameList);
		
		//IF THE USERNAME IS EMPTY
		if(name.isEmpty()) {
			showAlert("Please enter a name"); return;
		}
		
		while(nameFound) {
			for(int i = 0; i < nameList.size(); i++) {
				if(name.equals(nameList.get(i))) {
					hasCorrectName = false;
					newName = false;
					nameFound = true;
					break;
				}
				else {
					nameFound = false;
					newName = true;
					hasCorrectName  = true;
				}
			}
			break;
		}
		
		//IF THE NAME IS NEW AND THERE'S AVAILABLE SLOT
		if (newName && emptyIndex != -1) {
				nameList.add(emptyIndex, name);
	            hasCorrectName = true;
		}
	}
	public void checkUsernameField() {
		username = usernameField.getText();
		usernameList = accountDatabase.getUsernames();
		findAvailableIndex(usernameList);
		
		//IF THE USERNAME IS EMPTY
		if(username.isEmpty()) {
			showAlert("Please enter a username"); return;
			
		}
		
		//IF THE USERNAME IS SHORT
		if(username.length() < 8) {
			showAlert("Username must be atleast 8 characters"); return;
		}
		
		while(usernameFound) {
			for(int i = 0; i < usernameList.size();) {
				if(username.equals(usernameList.get(i))) {
					hasCorrectUsername = false;
					newUsername = false;
					usernameFound = true;
					break;
				}
				else {
					usernameFound = false;
					newUsername = true;
					hasCorrectUsername  = true;
				}
				break;
			}
			break; 
		}
	
		//IF THE USERNAME IS NEW AND THERE'S AVAILABLE SLOT
		if (newUsername && emptyIndex != -1) {
	            usernameList.add(emptyIndex, username);
				nameList.add(emptyIndex, name);
	            hasCorrectUsername = true;
	    }else {
	    	usernameFound = true;
	    	newUsername = false;
	    	System.out.println("This username already exist");
	    }
	
	}
	public void togglevisiblePassword(ActionEvent event) {
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

	public void initialize(URL location, ResourceBundle resources) {
	    this.togglevisiblePassword(null);
	}
	private String passwordValue() {
	    return pass_toggle.isSelected()?
	       pass_text.getText(): pass_hidden.getText();
	}

	public void checkPasswordField() {
		password = passwordValue();
		passwordList = accountDatabase.getPasswords();
		
		//IF THE PASSWORD IS EMPTY
		if(password.length() == 0) showAlert("Please enter your password");
		
		//IF THE PASSWORD IS SHORT
		else if(password.length() < 8) showAlert("Password must be atleast 8 characters");
		
		findAvailableIndex(passwordList);
		if(password.length() >= 8 && emptyIndex != -1) {
			passwordList.add(emptyIndex, password);
	        hasCorrectPassword = true;
		}
	}
	
	public void checkAccount(ActionEvent event) throws IOException {
		for(int i = 0; i < passwordList.size(); i++) {
			if (usernameList.get(i).equals(username) && passwordList.get(i).equals(password)) {
                loginSuccessful = true;
                break;
			}
		}
		
		if(loginSuccessful) {
			
			register(usernameList, passwordList, nameList);
			changeScene(event);
			
		}
	}
	
	private void findAvailableIndex(List<String> List) {
        for (int i = 0; i < List.size(); i++) {
            if (List.get(i) != null) {
                emptyIndex = i;
                break;
            }
        }
    }
	public void changeScene(ActionEvent event) throws IOException {
		FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
		root = loader.load();
		
		Scene1Controller loginPage = loader.getController();
		loginPage.setDatabase(accountDatabase);
		
		stage = (Stage)((Node)event.getSource()).getScene().getWindow();
		scene = new Scene(root);
		stage.setScene(scene);
		stage.show();
		
	}
	public void register(List<String> usernameList, List<String> passwordList, List<String> nameList) {
	 	accountDatabase.registerUser(usernameList, passwordList);
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
        }, 1 * 1000);
    }
	
}
