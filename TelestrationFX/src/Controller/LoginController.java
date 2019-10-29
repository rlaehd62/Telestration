package Controller;

import DTO.Request.Account.LoginRequest;
import Network.Client;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import com.sun.xml.internal.bind.v2.model.core.ID;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;

public class LoginController {

    @FXML
    private JFXTextField username;

    @FXML
    private JFXPasswordField password;

    @FXML
    private JFXButton login;

    @FXML
    private JFXButton register;

    private static LoginController controller;
    private Client client = Client.getInstance();

    public LoginController()
    {
        this.controller = this;
    }

    public static LoginController getInstance()
    {
        return controller;
    }

    @FXML
    void makeLogin(ActionEvent event)
    {
        Platform.runLater(() ->
        {
            String ID = username.getText();
            String PW = password.getText();
            if(ID.contains(" ") || PW.contains(" ") || ID.equals("") || PW.equals("")) return;
            LoginRequest request = new LoginRequest(ID, PW);
            request.setSubscribable(false);
            client.send(request);
        });
    }

    @FXML
    void makeRegister(ActionEvent event)
    {
        Platform.runLater(() ->
        {
            String ID = username.getText();
            String PW = password.getText();
            if(!isValid()) return;

            LoginRequest request = new LoginRequest(ID, PW);
            request.setSubscribable(true);
            client.send(request);
        });
    }

    private boolean isValid()
    {
        String ID = username.getText();
        String PW = password.getText();
        return !(ID.contains(" ") || PW.contains(" ") || ID.equals("") || PW.equals(""));
    }
}
