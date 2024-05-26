package application;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class Scene2Controller {
	@FXML
	Label nameLabel;
	
	private Stage stage;
	private Scene scene;
	private Parent root;
	private AccountDatabase accountDatabase;
	
	public void setAccountDatabase(AccountDatabase accountDatabase) {
	        this.accountDatabase = accountDatabase;
	}
	public void displayName(String username) {
		nameLabel.setText("Hello:");
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
	    loginPage.setDatabase(accountDatabase); // Pass the accountDatabase instance back to Scene1Controller
	  
	}
	
}

