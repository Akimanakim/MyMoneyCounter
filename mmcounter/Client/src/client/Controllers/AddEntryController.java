package client.Controllers;

import java.net.ConnectException;
import java.time.LocalDate;
import java.util.UUID;

import api.data.Transaction;
import api.services.UserService;
import client.ServiceManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class AddEntryController {
    @FXML
    private Button AddButton;

    @FXML
    private TextField AmountTextField;

    @FXML
    private TextField CategoryTextField;

    @FXML
    private DatePicker DatePicker;

    @FXML
    private TextArea DescriptionTextArea;

    private Stage dialogStage;

    private String date;
    private String category;
    private String description;
    private String sAmount;
    private int amount;
    private boolean isExpense;
    public void setIsExpense(boolean b){
        this.isExpense = b;
    }

    @FXML
    void initialize() {
    }

    public void setDialogStage(Stage dialogStage) {
        this.dialogStage = dialogStage;
    }

    @FXML
    private void AddClicked(){
        String id = UUID.randomUUID().toString();
        LocalDate localDate = DatePicker.getValue();
        date = String.valueOf(localDate);
        category = CategoryTextField.getText();
        description = DescriptionTextArea.getText();
        sAmount = AmountTextField.getText();
        try {
            amount = Integer.parseInt(sAmount.trim());
        } catch (NumberFormatException nfe)
        {
            System.out.println("NumberFormatException: " + nfe.getMessage());
        }
        Transaction transaction = new Transaction(id, amount, date, category, isExpense, description);
        UserService userService = null;
        try {
            userService = ServiceManager.getInstance().getUserService();
        } catch (ConnectException e) {
            throw new RuntimeException(e);
        }
        userService.setNewTransaction(transaction);

        dialogStage.close();
    }

}
