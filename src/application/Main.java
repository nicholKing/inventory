package application;

import java.io.IOException;

import javafx.application.Application;
import javafx.stage.Stage;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.AnchorPane;
import javafx.fxml.FXMLLoader;

public class Main extends Application {
    private Stage primaryStage;
   
    @Override
    public void start(Stage stage) {
        try {
            showLoginForm(stage);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void exit(Stage stage) {
        Alert alert = new Alert(AlertType.CONFIRMATION);
        alert.setTitle("");
        alert.setHeaderText("You're about to exit");
        alert.setContentText("Are you sure you want to exit?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            stage.close();
        }
    }

    public void showLoginForm(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Scene2.fxml"));
        AnchorPane loginLayout = loader.load();

        Scene2Controller homePage = loader.getController();

        primaryStage = new Stage();
        primaryStage.setTitle("User Login");
        primaryStage.setResizable(false);
        primaryStage.setScene(new Scene(loginLayout));

        // Set the close request handler before showing the stage
        primaryStage.setOnCloseRequest(event -> {
            event.consume();
            exit(primaryStage);
        });

        primaryStage.showAndWait();
    }

    public static void main(String[] args) {
        launch(args);
    }

    
}