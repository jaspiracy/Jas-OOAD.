package model;

import java.util.ArrayList;
import java.util.List;

public abstract class Customer {
    protected String customerId;
    protected String address;
    protected String phoneNumber;
    protected String email;
    protected List<Account> accounts;
    protected CustomerType customerType;

    public Customer(String customerId, String address, String phoneNumber, String email, CustomerType customerType) {
        this.customerId = customerId;
        this.address = address;
        this.phoneNumber = phoneNumber;
        this.email = email;
        this.accounts = new ArrayList<>();
        this.customerType = customerType;
    }

    public abstract String getFullName();
    public abstract String getCustomerInfo();

    public void addAccount(Account account) {
        if (account != null) {
            this.accounts.add(account);
            System.out.println("Account " + account.getAccountNumber() + " added to customer " + customerId);
        }
    }

    public boolean removeAccount(String accountNumber) {
        return accounts.removeIf(account -> account.getAccountNumber().equals(accountNumber));
    }

    public Account findAccount(String accountNumber) {
        return accounts.stream()
                .filter(acc -> acc.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElse(null);
    }

    public double getTotalBalance() {
        return accounts.stream()
                .mapToDouble(Account::getBalance)
                .sum();
    }

    public void displayAllAccounts() {
        System.out.println("\n--- Accounts for " + getFullName() + " ---");
        if (accounts.isEmpty()) {
            System.out.println("No accounts found.");
        } else {
            accounts.forEach(account -> {
                System.out.println("  " + account.toString());
            });
        }
        System.out.println("Total Balance: BWP " + String.format("%.2f", getTotalBalance()));
    }

    public String getCustomerId() { return customerId; }
    public String getAddress() { return address; }
    public String getPhoneNumber() { return phoneNumber; }
    public String getEmail() { return email; }
    public List<Account> getAccounts() { return new ArrayList<>(accounts); }
    public CustomerType getCustomerType() { return customerType; }

    public void setAddress(String address) { this.address = address; }
    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }
    public void setEmail(String email) { this.email = email; }

    @Override
    public String toString() {
        return String.format("Customer[ID: %s, Type: %s, Name: %s]",
                customerId, customerType, getFullName());
    }
}