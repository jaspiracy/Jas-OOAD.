package model;

public class SavingsAccount extends Account {
    private static final double INTEREST_RATE = 0.0005;

    public SavingsAccount(String accountNumber, double balance, String branch, Customer customer) {
        super(accountNumber, balance, branch, customer);
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            setBalance(getBalance() + amount);
            addTransaction("DEPOSIT", amount, getBalance());
            System.out.println("Deposited BWP " + amount + " into Savings Account " + accountNumber);
        } else {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
    }

    @Override
    public void withdraw(double amount) {
        throw new UnsupportedOperationException("Withdrawals are not allowed from Savings accounts");
    }

    @Override
    public boolean calculateInterest() {
        double interest = getBalance() * INTEREST_RATE;
        if (interest > 0) {
            setBalance(getBalance() + interest);
            lastInterestAmount = interest;
            addTransaction("INTEREST", interest, getBalance());
            System.out.println("Interest of BWP " + String.format("%.4f", interest) + " applied to " + accountNumber);
            return true;
        }
        return false;
    }

    @Override
    public String getAccountType() {
        return "SAVINGS";
    }

    @Override
    public double getInterestRate() {
        return INTEREST_RATE;
    }
}