package controller;

import javafx.scene.control.Alert;
import model.Bank;
import model.Customer;
import service.AuthenticationService;
import view.DashboardView;
import view.LoginView;
import javafx.stage.Stage;

public class LoginController {
    private LoginView loginView;
    private AuthenticationService authService;
    private Bank bank;

    public LoginController(LoginView loginView, Bank bank) {
        this.loginView = loginView;
        this.bank = bank;
        this.authService = new AuthenticationService(bank);
        attachEventHandlers();
    }

    private void attachEventHandlers() {
        loginView.getLoginButton().setOnAction(e -> handleLogin());
        loginView.getRegisterButton().setOnAction(e -> showRegistrationNotImplemented());
        loginView.getPasswordField().setOnAction(e -> handleLogin());
    }

    private void handleLogin() {
        String customerId = loginView.getCustomerId();
        String password = loginView.getPassword();

        if (customerId.isEmpty() || password.isEmpty()) {
            loginView.setMessage("Please enter both customer ID and password");
            return;
        }

        try {
            Customer customer = authService.authenticate(customerId, password);
            if (customer != null) {
                showDashboard(customer);
                loginView.clearFields();
            } else {
                loginView.setMessage("Invalid customer ID or password");
            }
        } catch (Exception e) {
            showError("Login failed: " + e.getMessage());
        }
    }

    private void showRegistrationNotImplemented() {
        showInfo("Registration feature will be implemented in the next phase.");
    }

    private void showDashboard(Customer customer) {
        DashboardView dashboardView = new DashboardView(customer);
        DashboardController dashboardController = new DashboardController(dashboardView, bank);

        Stage stage = loginView.getStage();
        stage.setScene(dashboardView.getScene());
        stage.setTitle("Botswana Banking System - Dashboard");
        stage.setWidth(850);
        stage.setHeight(650);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showInfo(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}