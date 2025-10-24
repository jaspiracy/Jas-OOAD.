package view;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import model.Account;
import model.Customer;

public class DashboardView {
    private Scene scene;
    private Customer currentCustomer;

    private Text welcomeText;
    private ListView<Account> accountsListView;
    private TextArea accountDetailsArea;
    private Button depositButton;
    private Button withdrawButton;
    private Button viewTransactionsButton;
    private Button calculateInterestButton;
    private Button logoutButton;
    private Label totalBalanceLabel;

    public DashboardView(Customer customer) {
        this.currentCustomer = customer;
        initializeUI();
    }

    private void initializeUI() {
        BorderPane borderPane = new BorderPane();
        borderPane.setPadding(new Insets(20));

        VBox topBox = new VBox(10);
        welcomeText = new Text("Welcome, " + currentCustomer.getFullName());
        welcomeText.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        Text customerInfo = new Text("Customer ID: " + currentCustomer.getCustomerId());
        topBox.getChildren().addAll(welcomeText, customerInfo);
        borderPane.setTop(topBox);

        VBox centerBox = new VBox(15);

        Label accountsLabel = new Label("Your Accounts:");
        accountsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        accountsListView = new ListView<>();
        updateAccountsList();
        accountsListView.setPrefHeight(150);

        Label detailsLabel = new Label("Account Details:");
        detailsLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));

        accountDetailsArea = new TextArea();
        accountDetailsArea.setEditable(false);
        accountDetailsArea.setPrefHeight(120);
        accountDetailsArea.setWrapText(true);

        centerBox.getChildren().addAll(accountsLabel, accountsListView, detailsLabel, accountDetailsArea);
        borderPane.setCenter(centerBox);

        VBox bottomBox = new VBox(15);

        HBox actionBox = new HBox(10);
        depositButton = new Button("Deposit");
        withdrawButton = new Button("Withdraw");
        viewTransactionsButton = new Button("View Transactions");
        calculateInterestButton = new Button("Calculate Interest");
        logoutButton = new Button("Logout");

        actionBox.getChildren().addAll(depositButton, withdrawButton, viewTransactionsButton,
                calculateInterestButton, logoutButton);

        totalBalanceLabel = new Label();
        updateTotalBalance();

        bottomBox.getChildren().addAll(actionBox, totalBalanceLabel);
        borderPane.setBottom(bottomBox);

        scene = new Scene(borderPane, 800, 600);
    }

    public void updateAccountsList() {
        ObservableList<Account> accounts = FXCollections.observableArrayList(currentCustomer.getAccounts());
        accountsListView.setItems(accounts);
    }

    public void updateTotalBalance() {
        double totalBalance = currentCustomer.getTotalBalance();
        totalBalanceLabel.setText("Total Balance: BWP " + String.format("%.2f", totalBalance));
        totalBalanceLabel.setFont(Font.font("Arial", FontWeight.BOLD, 14));
    }

    public void displayAccountDetails(Account account) {
        if (account != null) {
            String details = String.format(
                    "Account Number: %s\n" +
                            "Account Type: %s\n" +
                            "Balance: BWP %.2f\n" +
                            "Branch: %s\n" +
                            "Status: %s",
                    account.getAccountNumber(),
                    account.getAccountType(),
                    account.getBalance(),
                    account.getBranch(),
                    account.getStatus()
            );
            accountDetailsArea.setText(details);
        } else {
            accountDetailsArea.setText("No account selected.");
        }
    }

    public Scene getScene() { return scene; }
    public ListView<Account> getAccountsListView() { return accountsListView; }
    public Button getDepositButton() { return depositButton; }
    public Button getWithdrawButton() { return withdrawButton; }
    public Button getViewTransactionsButton() { return viewTransactionsButton; }
    public Button getCalculateInterestButton() { return calculateInterestButton; }
    public Button getLogoutButton() { return logoutButton; }
    public Customer getCurrentCustomer() { return currentCustomer; }
    public TextArea getAccountDetailsArea() { return accountDetailsArea; }
}