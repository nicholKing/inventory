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
    private AccountDatabase accountDatabase = new AccountDatabase();

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
        alert.setTitle("Exit");
        alert.setHeaderText("You're about to exit");
        alert.setContentText("Are you sure you want to exit?");
        if (alert.showAndWait().get() == ButtonType.OK) {
            stage.close();
        }
    }

    public void showLoginForm(Stage stage) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("Main.fxml"));
        AnchorPane loginLayout = loader.load();

        // Get the controller and set the mainApp and accountDatabase references
        Scene1Controller loginPage = loader.getController();
        loginPage.setDatabase(accountDatabase);

        primaryStage = new Stage();
        primaryStage.setTitle("User Login");
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

    public AccountDatabase getAccountDatabase() {
        return accountDatabase;
    }
}