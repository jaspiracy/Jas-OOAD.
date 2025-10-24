package view;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginView {
    private Stage stage;
    private TextField customerIdField;
    private PasswordField passwordField;
    private Button loginButton;
    private Button registerButton;
    private Label messageLabel;

    public LoginView(Stage stage) {
        this.stage = stage;
        initializeUI();
    }

    private void initializeUI() {
        stage.setTitle("Botswana Banking System - Login");

        // Create main container
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.CENTER);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        // Title
        Text title = new Text("Botswana Banking System");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 20));
        grid.add(title, 0, 0, 2, 1);

        // Customer ID
        Label customerIdLabel = new Label("Customer ID:");
        grid.add(customerIdLabel, 0, 1);

        customerIdField = new TextField();
        customerIdField.setPromptText("Enter your customer ID");
        grid.add(customerIdField, 1, 1);

        // Password
        Label passwordLabel = new Label("Password:");
        grid.add(passwordLabel, 0, 2);

        passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        grid.add(passwordField, 1, 2);

        // Buttons
        loginButton = new Button("Login");
        loginButton.setDefaultButton(true);

        registerButton = new Button("Register New Customer");

        HBox buttonBox = new HBox(10);
        buttonBox.setAlignment(Pos.BOTTOM_RIGHT);
        buttonBox.getChildren().addAll(registerButton, loginButton);
        grid.add(buttonBox, 1, 4);

        // Message label
        messageLabel = new Label();
        grid.add(messageLabel, 1, 5);

        Scene scene = new Scene(grid, 500, 350);
        stage.setScene(scene);
    }

    // Getters for controller access
    public String getCustomerId() {
        return customerIdField.getText().trim();
    }

    public PasswordField getPasswordField() {
        return passwordField;
    }

    public String getPassword() {
        return passwordField.getText();
    }

    public Button getLoginButton() {
        return loginButton;
    }

    public Button getRegisterButton() {
        return registerButton;
    }

    public void setMessage(String message) {
        messageLabel.setText(message);
    }

    public void clearFields() {
        customerIdField.clear();
        passwordField.clear();
        messageLabel.setText("");
    }

    public Stage getStage() {
        return stage;
    }
}