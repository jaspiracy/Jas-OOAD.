package model;

public class InvestmentAccount extends Account {
    private static final double INTEREST_RATE = 0.05;
    private static final double MINIMUM_BALANCE = 500.00;

    public InvestmentAccount(String accountNumber, double balance, String branch, Customer customer) {
        super(accountNumber, balance, branch, customer);
        if (balance < MINIMUM_BALANCE) {
            throw new IllegalArgumentException("Investment account requires minimum deposit of BWP " + MINIMUM_BALANCE);
        }
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            setBalance(getBalance() + amount);
            addTransaction("DEPOSIT", amount, getBalance());
            System.out.println("Deposited BWP " + amount + " into Investment Account " + accountNumber);
        } else {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (getBalance() - amount < 0) {
            throw new IllegalArgumentException("Insufficient funds. Available: BWP " + getBalance());
        }
        setBalance(getBalance() - amount);
        addTransaction("WITHDRAWAL", amount, getBalance());
        System.out.println("Withdrew BWP " + amount + " from Investment Account " + accountNumber);
    }

    @Override
    public boolean calculateInterest() {
        double interest = getBalance() * INTEREST_RATE;
        if (interest > 0) {
            setBalance(getBalance() + interest);
            lastInterestAmount = interest;
            addTransaction("INTEREST", interest, getBalance());
            System.out.println("Interest of BWP " + String.format("%.2f", interest) + " applied to " + accountNumber);
            return true;
        }
        return false;
    }

    @Override
    public String getAccountType() {
        return "INVESTMENT";
    }

    @Override
    public double getInterestRate() {
        return INTEREST_RATE;
    }
}