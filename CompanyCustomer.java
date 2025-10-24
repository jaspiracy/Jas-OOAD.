package model;

public class CompanyCustomer extends Customer {
    private String companyName;
    private String registrationNumber;
    private String contactPerson;

    public CompanyCustomer(String customerId, String companyName, String address,
                           String phoneNumber, String email, String registrationNumber,
                           String contactPerson) {
        super(customerId, address, phoneNumber, email, CustomerType.COMPANY);
        this.companyName = companyName;
        this.registrationNumber = registrationNumber;
        this.contactPerson = contactPerson;
    }

    @Override
    public String getFullName() {
        return companyName;
    }

    @Override
    public String getCustomerInfo() {
        return String.format("Company Customer: %s (Reg: %s)\nContact Person: %s\nAddress: %s\nPhone: %s\nEmail: %s",
                companyName, registrationNumber, contactPerson, address, phoneNumber, email);
    }

    public String getCompanyName() { return companyName; }
    public String getRegistrationNumber() { return registrationNumber; }
    public String getContactPerson() { return contactPerson; }

    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public void setRegistrationNumber(String registrationNumber) { this.registrationNumber = registrationNumber; }
    public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }
}