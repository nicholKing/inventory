package application;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.layout.GridPane;
import javafx.util.Pair;

import java.util.Optional;
import java.util.Timer;
import java.util.TimerTask;

public class EmployeeInputDialog {

	public Employee showAndWait() {
        Dialog<Employee> dialog = new Dialog<>();
        dialog.setTitle("Employee Input");
        dialog.setHeaderText("Please enter employee details:");

        ButtonType addButton = new ButtonType("Add", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(addButton, ButtonType.CANCEL);

        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(10);

        TextField idField = new TextField();
        TextField nameField = new TextField();
        TextField emailField = new TextField();
        TextField ageField = new TextField();
        TextField salaryField = new TextField();
        TextField roleField = new TextField();
        TextField genderField = new TextField();

        gridPane.addRow(0, new Label("ID:"), idField);
        gridPane.addRow(1, new Label("Name:"), nameField);
        gridPane.addRow(2, new Label("Email:"), emailField);
        gridPane.addRow(3, new Label("Gender:"), genderField);
        gridPane.addRow(4, new Label("Age:"), ageField);
        gridPane.addRow(5, new Label("Salary:"), salaryField);
        gridPane.addRow(6, new Label("Role:"), roleField);
       
        
        idField.setPrefWidth(150);
        nameField.setPrefWidth(150);
        emailField.setPrefWidth(150);
        ageField.setPrefWidth(150);
        salaryField.setPrefWidth(150);
        roleField.setPrefWidth(150);
        genderField.setPrefWidth(150);
        
        // Disable the "Add" button initially
        Node addButtonNode = dialog.getDialogPane().lookupButton(addButton);
        addButtonNode.setDisable(true);

        // Add listeners to text fields to enable/disable the "Add" button based on field completion and validity
        idField.textProperty().addListener((observable, oldValue, newValue) -> updateButtonState(addButtonNode, idField, nameField, emailField, ageField, salaryField, roleField, genderField));
        nameField.textProperty().addListener((observable, oldValue, newValue) -> updateButtonState(addButtonNode, idField, nameField, emailField, ageField, salaryField, roleField, genderField));
        emailField.textProperty().addListener((observable, oldValue, newValue) -> updateButtonState(addButtonNode, idField, nameField, emailField, ageField, salaryField, roleField, genderField));
        ageField.textProperty().addListener((observable, oldValue, newValue) -> updateButtonState(addButtonNode, idField, nameField, emailField, ageField, salaryField, roleField, genderField));
        salaryField.textProperty().addListener((observable, oldValue, newValue) -> updateButtonState(addButtonNode, idField, nameField, emailField, ageField, salaryField, roleField, genderField));
        roleField.textProperty().addListener((observable, oldValue, newValue) -> updateButtonState(addButtonNode, idField, nameField, emailField, ageField, salaryField, roleField, genderField));
        genderField.textProperty().addListener((observable, oldValue, newValue) -> updateButtonState(addButtonNode, idField, nameField, emailField, ageField, salaryField, roleField, genderField));

        dialog.getDialogPane().setContent(gridPane);

        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == addButton) {
                int id = Integer.parseInt(idField.getText());
                String name = nameField.getText();
                String email = emailField.getText();
                String gender = genderField.getText();
                int age = Integer.parseInt(ageField.getText());
                double salary = Double.parseDouble(salaryField.getText());
                String role = roleField.getText();
                return new Employee(id, name, email, age, salary, role, gender);
            }
            return null;
        });

        return dialog.showAndWait().orElse(null);
    }

    private void updateButtonState(Node addButtonNode, TextField idField, TextField nameField, TextField emailField, TextField ageField, TextField salaryField, TextField roleField, TextField genderField) {
        // Enable the "Add" button if all fields are not empty and ID, Age, and Salary are numeric
        boolean isIdNumeric = isNumeric(idField.getText());
        boolean isAgeNumeric = isNumeric(ageField.getText());
        boolean isSalaryNumeric = isNumeric(salaryField.getText());

        addButtonNode.setDisable(!fieldsNotEmpty(idField, nameField, emailField, ageField, salaryField, roleField, genderField) || !isIdNumeric || !isAgeNumeric || !isSalaryNumeric);
    }

    private boolean fieldsNotEmpty(TextField... fields) {
        for (TextField field : fields) {
            if (field.getText().isEmpty()) {
                return false;
            }
        }
        return true;
    }

    private boolean isNumeric(String str) {
        return str.matches("\\d+");
    }
    
    private void showAlert(String contentText, AlertType alertType) {

        Alert alert = new Alert(alertType);
        alert.setTitle("Notice");
        alert.setHeaderText(null);
        alert.setContentText(contentText);
        Scene scenes = alert.getDialogPane().getScene();
        scenes.getStylesheets().add(getClass().getResource("alert.css").toExternalForm());
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
