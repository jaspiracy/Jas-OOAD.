package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Account {
    protected String accountNumber;
    protected double balance;
    protected String branch;
    protected Customer customer;
    protected List<String> transactionHistory;
    protected AccountStatus status;
    protected double lastInterestAmount;

    public Account(String accountNumber, double balance, String branch, Customer customer) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.branch = branch;
        this.customer = customer;
        this.transactionHistory = new ArrayList<>();
        this.status = AccountStatus.ACTIVE;
        this.lastInterestAmount = 0.0;

        addTransaction("ACCOUNT_OPENED", balance, balance);
    }

    public abstract void deposit(double amount);
    public abstract void withdraw(double amount);
    public abstract boolean calculateInterest();
    public abstract String getAccountType();
    public abstract double getInterestRate();

    protected void addTransaction(String type, double amount, double newBalance) {
        String transaction = String.format("%s: BWP %.2f | Balance: BWP %.2f", type, amount, newBalance);
        transactionHistory.add(transaction);
    }

    public void displayTransactionHistory() {
        System.out.println("\n--- Transaction History for " + accountNumber + " ---");
        if (transactionHistory.isEmpty()) {
            System.out.println("No transactions found.");
        } else {
            transactionHistory.forEach(System.out::println);
        }
    }

    public String getAccountNumber() { return accountNumber; }
    public double getBalance() { return balance; }
    protected void setBalance(double balance) { this.balance = balance; }
    public String getBranch() { return branch; }
    public Customer getCustomer() { return customer; }
    public List<String> getTransactionHistory() { return new ArrayList<>(transactionHistory); }
    public AccountStatus getStatus() { return status; }
    public void setStatus(AccountStatus status) { this.status = status; }
    public double getLastInterestAmount() { return lastInterestAmount; }

    @Override
    public String toString() {
        return String.format("%s - %s - Balance: BWP %.2f - %s",
                accountNumber, getAccountType(), balance, status);
    }
}
