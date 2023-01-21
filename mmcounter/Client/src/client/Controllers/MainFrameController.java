package client.Controllers;

import java.io.IOException;
import java.net.ConnectException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.ResourceBundle;

import api.data.Account;
import api.data.Transaction;
import api.services.UserService;
import client.ServiceManager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

public class MainFrameController {
    @FXML
    private Label AccountName;

    @FXML
    private TableColumn<Transaction, Integer> ExAmount;

    @FXML
    private TableColumn<Transaction, String> ExDescription;

    @FXML
    private TableColumn<Transaction, String> ExCategory;

    @FXML
    private TableColumn<Transaction, Date> ExDate;

    @FXML
    private Button ExitAccountButton;
    @FXML
    private Button UpdateDataButton;
    @FXML
    private Button ExDeleteEntryButton;
    @FXML
    private Button ExAddEntryButton;
    @FXML
    private Button ReAddEntryButton;
    @FXML
    private Button ReDeleteEntryButton;

    @FXML
    private TableView<Transaction> ExpenseTable;

    @FXML
    private TableColumn<Transaction, Integer> ReAmount;

    @FXML
    private TableColumn<Transaction, String> ReDescription;

    @FXML
    private TableColumn<Transaction, String> ReCategory;

    @FXML
    private TableColumn<Transaction, Date> ReDate;

    @FXML
    private TableView<Transaction> RevenueTable;

    ObservableList<Transaction> transactionsReData = FXCollections.observableArrayList();
    ObservableList<Transaction> transactionsExData = FXCollections.observableArrayList();
    private void initData(){

        UserService userService = null;
        try {
            userService = ServiceManager.getInstance().getUserService();
        } catch (ConnectException e) {
            throw new RuntimeException(e);
        }

        ArrayList<Transaction> transactions = userService.writeToTable();
        for(Transaction t : transactions){
            if(t.isExpense() == true){
                transactionsExData.add(t);
            }else{
                transactionsReData.add(t);
            }
        }
        ReDate.setCellValueFactory(new PropertyValueFactory<Transaction, Date>("date"));
        ReCategory.setCellValueFactory(new PropertyValueFactory<Transaction, String>("category"));
        ReDescription.setCellValueFactory(new PropertyValueFactory<Transaction, String>("description"));
        ReAmount.setCellValueFactory(new PropertyValueFactory<Transaction, Integer>("amount"));

        ExDate.setCellValueFactory(new PropertyValueFactory<Transaction, Date>("date"));
        ExCategory.setCellValueFactory(new PropertyValueFactory<Transaction, String>("category"));
        ExDescription.setCellValueFactory(new PropertyValueFactory<Transaction, String>("description"));
        ExAmount.setCellValueFactory(new PropertyValueFactory<Transaction, Integer>("amount"));

        RevenueTable.setItems(transactionsReData);
        ExpenseTable.setItems(transactionsExData);
    }


    @FXML
    public void initialize() {
        UserService userService = null;
        try {
            userService = ServiceManager.getInstance().getUserService();
        } catch (ConnectException e) {
            throw new RuntimeException(e);
        }
        ExitAccountButton.setOnAction(event -> {
            ExitAccountButton.getScene().getWindow().hide();

            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("client/LoginWindow.fxml"));
            try {
                loader.load();
            } catch (IOException e){
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.show();
        });
        AccountName.setText(userService.getCurrentAccount().getName());
        initData();

    }

    @FXML
    private void UpdateDataButtonOnAction(){
        RevenueTable.getItems().clear();
        ExpenseTable.getItems().clear();
        UserService uService = null;
        try {
            uService = ServiceManager.getInstance().getUserService();
        } catch (ConnectException e) {
            throw new RuntimeException(e);
        }

        ArrayList<Transaction> transactions = uService.writeToTable();
        for(Transaction t : transactions){
            if(t.isExpense() == true){
                transactionsExData.add(t);
            }else{
                transactionsReData.add(t);
            }
        }
        ReDate.setCellValueFactory(new PropertyValueFactory<Transaction, Date>("date"));
        ReCategory.setCellValueFactory(new PropertyValueFactory<Transaction, String>("category"));
        ReDescription.setCellValueFactory(new PropertyValueFactory<Transaction, String>("description"));
        ReAmount.setCellValueFactory(new PropertyValueFactory<Transaction, Integer>("amount"));

        ExDate.setCellValueFactory(new PropertyValueFactory<Transaction, Date>("date"));
        ExCategory.setCellValueFactory(new PropertyValueFactory<Transaction, String>("category"));
        ExDescription.setCellValueFactory(new PropertyValueFactory<Transaction, String>("description"));
        ExAmount.setCellValueFactory(new PropertyValueFactory<Transaction, Integer>("amount"));

        RevenueTable.setItems(transactionsReData);
        ExpenseTable.setItems(transactionsExData);
    }


    @FXML
    private void ReAddEntryButtonOnAction(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("client/AddEntryWindow.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AddEntryController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setIsExpense(false);

            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ExAddEntryButtonOnAction(){
        try{
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("client/AddEntryWindow.fxml"));
            AnchorPane page = (AnchorPane) loader.load();
            Stage dialogStage = new Stage();
            Scene scene = new Scene(page);
            dialogStage.setScene(scene);

            AddEntryController controller = loader.getController();
            controller.setDialogStage(dialogStage);
            controller.setIsExpense(true);

            dialogStage.showAndWait();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @FXML
    private void ExDeleteEntryButtonOnAction(){

        Transaction transaction = ExpenseTable.getSelectionModel().getSelectedItem();
        UserService userService = null;
        try {
            userService = ServiceManager.getInstance().getUserService();
        } catch (ConnectException e) {
            throw new RuntimeException(e);
        }
        userService.deleteFromDatabase(transaction);

        int selectedIndex = ExpenseTable.getSelectionModel().getSelectedIndex();
        ExpenseTable.getItems().remove(selectedIndex);
    }

    @FXML
    private void ReDeleteEntryButtonOnAction(){

        Transaction transaction = RevenueTable.getSelectionModel().getSelectedItem();
        UserService userService = null;
        try {
            userService = ServiceManager.getInstance().getUserService();
        } catch (ConnectException e) {
            throw new RuntimeException(e);
        }
        userService.deleteFromDatabase(transaction);

        int selectedIndex = RevenueTable.getSelectionModel().getSelectedIndex();
        RevenueTable.getItems().remove(selectedIndex);

    }
}
