import controller.LoginController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;
import model.Bank;
import model.CompanyCustomer;
import model.IndividualCustomer;
import view.LoginView;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) {
        try {
            Bank bank = initializeBankWithSampleData();

            LoginView loginView = new LoginView(primaryStage);
            LoginController loginController = new LoginController(loginView, bank);

            primaryStage.show();
        } catch (Exception e) {
            System.err.println("Error starting application: " + e.getMessage());
            e.printStackTrace();
            Platform.exit();
        }
    }

    private Bank initializeBankWithSampleData() {
        Bank bank = new Bank("Botswana Commercial Bank", "BCB001");

        try {
            // Individual Customers
            IndividualCustomer cust1 = bank.registerIndividualCustomer(
                    "CUST001", "Kagiso", "Molapo",
                    "Plot 123, Broadhurst, Gaborone", "+26771234567",
                    "kagiso.molapo@email.com", "19901234567"
            );

            IndividualCustomer cust2 = bank.registerIndividualCustomer(
                    "CUST002", "Amantle", "Khumo",
                    "Block 7, Francistown", "+26772345678",
                    "amantle.khumo@email.com", "19911234568"
            );

            // Company Customer
            CompanyCustomer company1 = bank.registerCompanyCustomer(
                    "CUST003", "Botswana Tech Solutions Ltd",
                    "Mall Complex, Gaborone", "+26773456789",
                    "info@bwtech.co.bw", "CORP2024001", "Tshepo Dintwa"
            );

            // Add sample accounts
            bank.openSavingsAccount("SAV001", 1500.00, "Gaborone Main", cust1);
            bank.openInvestmentAccount("INV001", 2500.00, "Gaborone Main", cust1);
            bank.openChequeAccount("CHQ001", 3500.00, "Gaborone Main", cust1,
                    "Ministry of Education", "Government Enclave, Gaborone");

            bank.openSavingsAccount("SAV002", 800.00, "Francistown", cust2);

            bank.openChequeAccount("CHQ002", 15000.00, "Gaborone Main", company1,
                    "Self", "Mall Complex, Gaborone");

            System.out.println("Sample data initialized successfully");

        } catch (Exception e) {
            System.err.println("Error initializing sample data: " + e.getMessage());
        }

        return bank;
    }

    public static void main(String[] args) {
        System.out.println("Starting Banking System Application...");
        try {
            launch(args);
        } catch (Exception e) {
            System.err.println("Failed to launch application: " + e.getMessage());
            e.printStackTrace();
        }
    }
}