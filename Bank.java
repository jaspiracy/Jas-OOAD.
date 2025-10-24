package model;

import java.util.ArrayList;
import java.util.List;

public class Bank {
    private String bankName;
    private String bankCode;
    private List<Customer> customers;
    private List<Account> allAccounts;
    private double totalBankAssets;

    public Bank(String bankName, String bankCode) {
        this.bankName = bankName;
        this.bankCode = bankCode;
        this.customers = new ArrayList<>();
        this.allAccounts = new ArrayList<>();
        this.totalBankAssets = 0.0;
    }

    public IndividualCustomer registerIndividualCustomer(String customerId, String firstName,
                                                         String surname, String address,
                                                         String phoneNumber, String email,
                                                         String idNumber) {
        IndividualCustomer customer = new IndividualCustomer(customerId, firstName, surname,
                address, phoneNumber, email, idNumber);
        customers.add(customer);
        System.out.println("Individual customer registered: " + customer.getFullName());
        return customer;
    }

    public CompanyCustomer registerCompanyCustomer(String customerId, String companyName,
                                                   String address, String phoneNumber,
                                                   String email, String registrationNumber,
                                                   String contactPerson) {
        CompanyCustomer customer = new CompanyCustomer(customerId, companyName, address,
                phoneNumber, email, registrationNumber,
                contactPerson);
        customers.add(customer);
        System.out.println("Company customer registered: " + companyName);
        return customer;
    }

    public Customer findCustomer(String customerId) {
        return customers.stream()
                .filter(c -> c.getCustomerId().equals(customerId))
                .findFirst()
                .orElse(null);
    }

    public SavingsAccount openSavingsAccount(String accountNumber, double initialDeposit,
                                             String branch, Customer customer) {
        SavingsAccount account = new SavingsAccount(accountNumber, initialDeposit, branch, customer);
        customer.addAccount(account);
        allAccounts.add(account);
        totalBankAssets += initialDeposit;
        System.out.println("Savings account opened: " + accountNumber);
        return account;
    }

    public InvestmentAccount openInvestmentAccount(String accountNumber, double initialDeposit,
                                                   String branch, Customer customer) {
        if (initialDeposit < 500.00) {
            throw new IllegalArgumentException("Investment account requires minimum BWP 500.00 deposit");
        }
        InvestmentAccount account = new InvestmentAccount(accountNumber, initialDeposit, branch, customer);
        customer.addAccount(account);
        allAccounts.add(account);
        totalBankAssets += initialDeposit;
        System.out.println("Investment account opened: " + accountNumber);
        return account;
    }

    public ChequeAccount openChequeAccount(String accountNumber, double initialDeposit,
                                           String branch, Customer customer, String employer,
                                           String companyAddress) {
        ChequeAccount account = new ChequeAccount(accountNumber, initialDeposit, branch,
                customer, employer, companyAddress);
        customer.addAccount(account);
        allAccounts.add(account);
        totalBankAssets += initialDeposit;
        System.out.println("Cheque account opened: " + accountNumber);
        return account;
    }

    public void processDeposit(String accountNumber, double amount) {
        Account account = findAccount(accountNumber);
        if (account != null) {
            account.deposit(amount);
            totalBankAssets += amount;
        } else {
            throw new IllegalArgumentException("Account not found: " + accountNumber);
        }
    }

    public void processWithdrawal(String accountNumber, double amount) {
        Account account = findAccount(accountNumber);
        if (account != null) {
            account.withdraw(amount);
            totalBankAssets -= amount;
        } else {
            throw new IllegalArgumentException("Account not found: " + accountNumber);
        }
    }

    public void calculateMonthlyInterest() {
        System.out.println("\n=== Calculating Monthly Interest ===");
        int interestAccounts = 0;
        double totalInterest = 0;

        for (Account account : allAccounts) {
            if (account.calculateInterest()) {
                interestAccounts++;
                totalInterest += account.getLastInterestAmount();
            }
        }

        System.out.println("Interest applied to " + interestAccounts + " accounts");
        System.out.println("Total interest paid: BWP " + String.format("%.2f", totalInterest));
    }

    private Account findAccount(String accountNumber) {
        return allAccounts.stream()
                .filter(acc -> acc.getAccountNumber().equals(accountNumber))
                .findFirst()
                .orElse(null);
    }

    public void displayBankSummary() {
        System.out.println("\n=== " + bankName + " Bank Summary ===");
        System.out.println("Bank Code: " + bankCode);
        System.out.println("Total Customers: " + customers.size());
        System.out.println("Total Accounts: " + allAccounts.size());
        System.out.println("Total Bank Assets: BWP " + String.format("%.2f", totalBankAssets));

        long individualCount = customers.stream()
                .filter(c -> c instanceof IndividualCustomer)
                .count();
        long companyCount = customers.stream()
                .filter(c -> c instanceof CompanyCustomer)
                .count();

        System.out.println("Individual Customers: " + individualCount);
        System.out.println("Company Customers: " + companyCount);

        long savingsCount = allAccounts.stream()
                .filter(acc -> acc instanceof SavingsAccount)
                .count();
        long investmentCount = allAccounts.stream()
                .filter(acc -> acc instanceof InvestmentAccount)
                .count();
        long chequeCount = allAccounts.stream()
                .filter(acc -> acc instanceof ChequeAccount)
                .count();

        System.out.println("Savings Accounts: " + savingsCount);
        System.out.println("Investment Accounts: " + investmentCount);
        System.out.println("Cheque Accounts: " + chequeCount);
    }

    public void displayAllCustomers() {
        System.out.println("\n=== All Customers ===");
        customers.forEach(customer -> {
            System.out.println(customer.getCustomerInfo());
            customer.displayAllAccounts();
            System.out.println("---");
        });
    }

    public String getBankName() { return bankName; }
    public String getBankCode() { return bankCode; }
    public List<Customer> getCustomers() { return new ArrayList<>(customers); }
    public List<Account> getAllAccounts() { return new ArrayList<>(allAccounts); }
    public double getTotalBankAssets() { return totalBankAssets; }
}
