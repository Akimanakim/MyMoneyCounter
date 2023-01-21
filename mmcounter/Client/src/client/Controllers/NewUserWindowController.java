package client.Controllers;

import java.io.IOException;
import java.net.ConnectException;
import java.net.MalformedURLException;

import api.data.Account;
import api.services.UserService;
import client.ServiceManager;
import com.caucho.hessian.client.HessianProxyFactory;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class NewUserWindowController {
    @FXML
    private Button CancelButton;

    @FXML
    private TextField LoginTextField;

    @FXML
    private Label NewLoginLabel;

    @FXML
    private PasswordField PasswordTextField;

    @FXML
    private Button SignUpButton;

    @FXML
    void initialize() {
        CancelButton.setOnAction(event -> {
            CancelButton.getScene().getWindow().hide();
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

        SignUpButton.setOnAction(event -> {
            try {
                UserService userService = ServiceManager.getInstance().getUserService();
                Account account = new Account();
                account.setName(LoginTextField.getText());
                account.setPassword(PasswordTextField.getText());
                userService.writeToDatabase(account);
            } catch (ConnectException e) {
                throw new RuntimeException(e);
            }
        });
    }
}