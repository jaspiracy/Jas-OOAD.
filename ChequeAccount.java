package model;

public class ChequeAccount extends Account {
    private String employer;
    private String companyAddress;
    private static final double OVERDRAFT_LIMIT = 1000.00;

    public ChequeAccount(String accountNumber, double balance, String branch, Customer customer,
                         String employer, String companyAddress) {
        super(accountNumber, balance, branch, customer);
        this.employer = employer;
        this.companyAddress = companyAddress;

        if (employer == null || employer.trim().isEmpty()) {
            throw new IllegalArgumentException("Employer information is required for Cheque accounts");
        }
    }

    @Override
    public void deposit(double amount) {
        if (amount > 0) {
            setBalance(getBalance() + amount);
            addTransaction("DEPOSIT", amount, getBalance());
            System.out.println("Deposited BWP " + amount + " into Cheque Account " + accountNumber);
        } else {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
    }

    @Override
    public void withdraw(double amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (getBalance() - amount < -OVERDRAFT_LIMIT) {
            throw new IllegalArgumentException("Overdraft limit exceeded. Maximum overdraft: BWP " + OVERDRAFT_LIMIT);
        }
        setBalance(getBalance() - amount);
        addTransaction("WITHDRAWAL", amount, getBalance());
        System.out.println("Withdrew BWP " + amount + " from Cheque Account " + accountNumber);

        if (getBalance() < 0) {
            System.out.println("Warning: Account is in overdraft. Current balance: BWP " + getBalance());
        }
    }

    @Override
    public boolean calculateInterest() {
        System.out.println("No interest paid on Cheque Account " + accountNumber);
        return false;
    }

    @Override
    public String getAccountType() {
        return "CHEQUE";
    }

    @Override
    public double getInterestRate() {
        return 0.0;
    }

    public String getEmployer() { return employer; }
    public String getCompanyAddress() { return companyAddress; }
}