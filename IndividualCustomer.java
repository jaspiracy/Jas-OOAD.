package model;

public class IndividualCustomer extends Customer {
    private String firstName;
    private String surname;
    private String idNumber;

    public IndividualCustomer(String customerId, String firstName, String surname,
                              String address, String phoneNumber, String email, String idNumber) {
        super(customerId, address, phoneNumber, email, CustomerType.INDIVIDUAL);
        this.firstName = firstName;
        this.surname = surname;
        this.idNumber = idNumber;
    }

    @Override
    public String getFullName() {
        return firstName + " " + surname;
    }

    @Override
    public String getCustomerInfo() {
        return String.format("Individual Customer: %s %s (ID: %s)\nID Number: %s\nAddress: %s\nPhone: %s\nEmail: %s",
                firstName, surname, customerId, idNumber, address, phoneNumber, email);
    }

    public String getFirstName() { return firstName; }
    public String getSurname() { return surname; }
    public String getIdNumber() { return idNumber; }

    public void setFirstName(String firstName) { this.firstName = firstName; }
    public void setSurname(String surname) { this.surname = surname; }
    public void setIdNumber(String idNumber) { this.idNumber = idNumber; }
}