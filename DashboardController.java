package controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import model.Account;
import model.Bank;
import view.DashboardView;
import view.TransactionDialog;

import java.util.Optional;

public class DashboardController {
    private DashboardView dashboardView;
    private Bank bank;

    public DashboardController(DashboardView dashboardView, Bank bank) {
        this.dashboardView = dashboardView;
        this.bank = bank;
        attachEventHandlers();
        setupAccountSelection();
    }

    private void attachEventHandlers() {
        dashboardView.getDepositButton().setOnAction(e -> handleDeposit());
        dashboardView.getWithdrawButton().setOnAction(e -> handleWithdraw());
        dashboardView.getViewTransactionsButton().setOnAction(e -> handleViewTransactions());
        dashboardView.getCalculateInterestButton().setOnAction(e -> handleCalculateInterest());
        dashboardView.getLogoutButton().setOnAction(e -> handleLogout());
    }

    private void setupAccountSelection() {
        dashboardView.getAccountsListView().getSelectionModel().selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    dashboardView.displayAccountDetails(newValue);
                });
    }

    private void handleDeposit() {
        Account selectedAccount = getSelectedAccount();
        if (selectedAccount == null) {
            showAlert("Please select an account to deposit into");
            return;
        }

        TransactionDialog depositDialog = new TransactionDialog(
                (javafx.stage.Stage) dashboardView.getScene().getWindow(), "Deposit"
        );

        depositDialog.getConfirmButton().setOnAction(e -> {
            Double amount = depositDialog.getAmount();
            if (amount != null && amount > 0) {
                try {
                    bank.processDeposit(selectedAccount.getAccountNumber(), amount);
                    refreshDashboard();
                    depositDialog.close();
                    showSuccess("Deposit successful!");
                } catch (Exception ex) {
                    showError("Deposit failed: " + ex.getMessage());
                }
            } else {
                showAlert("Please enter a valid positive amount");
            }
        });

        depositDialog.getCancelButton().setOnAction(e -> depositDialog.close());
        depositDialog.show();
    }

    private void handleWithdraw() {
        Account selectedAccount = getSelectedAccount();
        if (selectedAccount == null) {
            showAlert("Please select an account to withdraw from");
            return;
        }

        TransactionDialog withdrawDialog = new TransactionDialog(
                (javafx.stage.Stage) dashboardView.getScene().getWindow(), "Withdraw"
        );

        withdrawDialog.getConfirmButton().setOnAction(e -> {
            Double amount = withdrawDialog.getAmount();
            if (amount != null && amount > 0) {
                try {
                    bank.processWithdrawal(selectedAccount.getAccountNumber(), amount);
                    refreshDashboard();
                    withdrawDialog.close();
                    showSuccess("Withdrawal successful!");
                } catch (Exception ex) {
                    showError("Withdrawal failed: " + ex.getMessage());
                }
            } else {
                showAlert("Please enter a valid positive amount");
            }
        });

        withdrawDialog.getCancelButton().setOnAction(e -> withdrawDialog.close());
        withdrawDialog.show();
    }

    private void handleViewTransactions() {
        Account selectedAccount = getSelectedAccount();
        if (selectedAccount == null) {
            showAlert("Please select an account to view transactions");
            return;
        }

        Alert transactionsAlert = new Alert(Alert.AlertType.INFORMATION);
        transactionsAlert.setTitle("Transaction History");
        transactionsAlert.setHeaderText("Transactions for " + selectedAccount.getAccountNumber());

        StringBuilder transactions = new StringBuilder();
        selectedAccount.getTransactionHistory().forEach(t -> transactions.append(t).append("\n"));

        transactionsAlert.setContentText(transactions.toString());
        transactionsAlert.showAndWait();
    }

    private void handleCalculateInterest() {
        try {
            bank.calculateMonthlyInterest();
            refreshDashboard();
            showSuccess("Interest calculated successfully!");
        } catch (Exception e) {
            showError("Interest calculation failed: " + e.getMessage());
        }
    }

    private void handleLogout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Logout");
        alert.setHeaderText("Confirm Logout");
        alert.setContentText("Are you sure you want to logout?");

        Optional<ButtonType> result = alert.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            System.exit(0);
        }
    }

    private Account getSelectedAccount() {
        return dashboardView.getAccountsListView().getSelectionModel().getSelectedItem();
    }

    private void refreshDashboard() {
        dashboardView.updateAccountsList();
        dashboardView.updateTotalBalance();

        Account selectedAccount = getSelectedAccount();
        if (selectedAccount != null) {
            Account updatedAccount = dashboardView.getCurrentCustomer()
                    .findAccount(selectedAccount.getAccountNumber());
            dashboardView.displayAccountDetails(updatedAccount);
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Warning");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccess(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Success");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}