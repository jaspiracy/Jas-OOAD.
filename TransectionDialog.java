package view;

import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class TransactionDialog {
    private Stage dialog;
    private TextField amountField;
    private Button confirmButton;
    private Button cancelButton;

    public TransactionDialog(Stage owner, String transactionType) {
        initializeDialog(owner, transactionType);
    }

    private void initializeDialog(Stage owner, String transactionType) {
        dialog = new Stage();
        dialog.initOwner(owner);
        dialog.initModality(Modality.APPLICATION_MODAL);
        dialog.setTitle(transactionType + " Funds");

        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(20));

        Label amountLabel = new Label("Amount (BWP):");
        grid.add(amountLabel, 0, 0);

        amountField = new TextField();
        amountField.setPromptText("Enter amount");
        grid.add(amountField, 1, 0);

        HBox buttonBox = new HBox(10);
        confirmButton = new Button("Confirm");
        cancelButton = new Button("Cancel");
        buttonBox.getChildren().addAll(confirmButton, cancelButton);
        grid.add(buttonBox, 1, 1);

        dialog.setScene(new javafx.scene.Scene(grid, 300, 120));
    }

    public Double getAmount() {
        try {
            return Double.parseDouble(amountField.getText());
        } catch (NumberFormatException e) {
            return null;
        }
    }

    public void show() {
        dialog.showAndWait();
    }

    public void close() {
        dialog.close();
    }

    public Button getConfirmButton() { return confirmButton; }
    public Button getCancelButton() { return cancelButton; }
    public TextField getAmountField() { return amountField; }
}