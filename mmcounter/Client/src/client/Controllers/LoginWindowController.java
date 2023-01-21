package client.Controllers;

import java.io.IOException;
import java.sql.SQLException;

import api.data.Account;
import api.services.UserService;
import client.ServiceManager;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class LoginWindowController {
    @FXML
    private Button CancelButton;

    @FXML
    private Button EnterButton;

    @FXML
    private Label FailedLoginLabel;

    @FXML
    private TextField LoginTextField;

    @FXML
    private Button NewUserButton;

    @FXML
    private PasswordField PasswordTextField;

    @FXML
    void initialize() {
        EnterButton.setOnAction(event -> {
            if(LoginTextField.getText().isBlank() == false && PasswordTextField.getText().isBlank() == false){
                try {
                    validateLogin(LoginTextField.getText(), PasswordTextField.getText());
                } catch (SQLException | IOException e) {
                    e.printStackTrace();
                }
            }
            else {
                FailedLoginLabel.setText("Остались незаполненные поля");
            }
        });

        NewUserButton.setOnAction(event -> {
            NewUserButton.getScene().getWindow().hide();
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(getClass().getClassLoader().getResource("client/NewUserWindow.fxml"));
            try {
                loader.load();
            } catch (IOException e){
                e.printStackTrace();
            }
            Parent root = loader.getRoot();
            Stage stage = new Stage();
            stage.setScene(new Scene(root));
            stage.showAndWait();
        });

        CancelButton.setOnAction(event -> {
            Stage stage = (Stage) CancelButton.getScene().getWindow();
            stage.close();
        });
    }

    public void validateLogin(String accountName, String accountPassword) throws SQLException, IOException {

        Account account = new Account("", accountName, accountPassword);
        UserService userService = ServiceManager.getInstance().getUserService();
        int counter = 0;

        counter = userService.getValidAccount(account);
        userService.setCurrentAccount(account);

        if (counter == 0) {
            FailedLoginLabel.setText("Неверное имя пользователя или пароль");
        } else {
            EnterButton.setOnAction(event -> {
                EnterButton.getScene().getWindow().hide();
                try {
                    FXMLLoader loader = new FXMLLoader();
                    loader.setLocation(getClass().getClassLoader().getResource("client/MainFrame.fxml"));

                    TabPane root = (TabPane) loader.load();
                    Stage stage = new Stage();
                    stage.setScene(new Scene(root));
                    stage.show();

                } catch (IOException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}