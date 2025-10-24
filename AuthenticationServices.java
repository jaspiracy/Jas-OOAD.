package service;

import model.Bank;
import model.Customer;

public class AuthenticationService {
    private Bank bank;

    public AuthenticationService(Bank bank) {
        this.bank = bank;
    }

    public Customer authenticate(String customerId, String password) {
        Customer customer = bank.findCustomer(customerId);

        if (customer != null && isValidPassword(customerId, password)) {
            return customer;
        }
        return null;
    }

    private boolean isValidPassword(String customerId, String password) {
        return password != null && password.length() >= 4;
    }
}