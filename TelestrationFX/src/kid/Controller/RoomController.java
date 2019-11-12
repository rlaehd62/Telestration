package kid.Controller;

import DTO.Request.Room.GameRoom;
import DTO.Request.Room.JoinRoomRequest;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import kid.GameData.Account;
import kid.Network.Client;

import java.net.URL;
import java.util.ResourceBundle;

public class RoomController implements Initializable
{
    @FXML
    private Label owner;

    @FXML
    private Label title;

    @FXML
    private Label limit;

    @FXML
    private JFXButton connect;
    private Client client = Client.getInstance();

    public void initialize(URL url, ResourceBundle resourceBundle)
    {
        connect.setOnAction(actionEvent ->
        {
            String ID = Account.getInstance().getID();
            JoinRoomRequest request = new JoinRoomRequest(ID);
            request.setOwner(owner.getText());
            client.send(request);
        });
    }

    public void setRoom(GameRoom room)
    {
        Platform.runLater(() ->
        {
            owner.setText(room.getOwner());
            title.setText(room.getTitle());
            limit.setText("Lv." + room.getLevelLimit());
        });
    }
}
