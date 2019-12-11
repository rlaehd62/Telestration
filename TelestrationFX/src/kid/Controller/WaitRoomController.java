package kid.Controller;

import DTO.Request.Room.CreateRoomRequest;
import DTO.Request.Room.JoinRoomRequest;
import DTO.Request.Room.RoomListRequest;
import DTO.Response.Room.RoomListResponse;
import DTO.Response.Room.RoomResponse;
import Game.GameRoom;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import kid.GameData.Account;
import kid.GameData.User;
import kid.Network.Client;
import com.jfoenix.controls.*;
import com.jfoenix.controls.datamodels.treetable.RecursiveTreeObject;
import javafx.application.Platform;
import javafx.beans.property.*;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TreeItem;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import kid.TelestrationFX.MainFX;

import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;

public class WaitRoomController
{

    @FXML
    private Label username;

    @FXML
    private Label level;

    @FXML
    private Label exp;

    @FXML
    private Label time;

    @FXML
    private JFXTextField title;

    @FXML
    private JFXTextField limit;

    @FXML
    private JFXTextField timeout;

    @FXML
    private JFXButton creation;

    @FXML
    private VBox list;

    private Client client;
    private static WaitRoomController controller;

    public WaitRoomController()
    {
        client = Client.getInstance();
        controller = this;
    }

    public static WaitRoomController getInstance()
    {
        return controller;
    }

    @FXML
    void clickTable(MouseEvent event)
    {

    }


    @FXML
    void updateInfo(MouseEvent event)
    {
        Platform.runLater(() ->
        {
            AudioClip bgm = new AudioClip(MainFX.SWITCH);
            bgm.play();

            username.setText(Account.getInstance().getID());
            level.setText("Lv." + User.getInstance().level());
            exp.setText(String.format("EXP (%d / %d)", User.getInstance().exp(), User.getInstance().maxExp()));

            clearList();
            RoomListRequest request = new RoomListRequest(Account.getInstance().getID(), 10);
            client.send(request);
        });
    }

    @FXML
    void createRoom(ActionEvent event)
    {
        Platform.runLater(() ->
        {
            try
            {
                AudioClip bgm = new AudioClip(MainFX.CLICK);
                bgm.play();

                String ID = Account.getInstance().getID();
                String TITLE = title.getText();
                title.clear();

                int level = Integer.parseInt(limit.getText());
                limit.clear();

                int time = Integer.parseInt(timeout.getText());
                time = Math.max(time, 30);
                timeout.clear();

                CreateRoomRequest request = new CreateRoomRequest(ID, TITLE);
                request.setLimit(level);
                request.setTimeout(time);

                client.send(request);

            } catch (Exception e) { return; }
        });
    }

    public void clearList()
    {
        Platform.runLater(() ->
        {
            list.getChildren().clear();
        });
    }


    public void updateUserInfo()
    {
        Platform.runLater(() ->
        {
            username.setText(Account.getInstance().getID());
            level.setText("Lv." + User.getInstance().level());
            exp.setText(String.format("EXP (%d / %d)", User.getInstance().exp(), User.getInstance().maxExp()));
        });
    }

    public void updateRoomList(RoomListResponse response)
    {
        System.out.println("되긴됨?");
        Platform.runLater(() ->
        {
            try
            {
                for(RoomResponse room : response.getRooms())
                {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Layout/Item.fxml"));
                    GameRoom gameRoom = room.getRoom();
                    if(Objects.isNull(gameRoom)) continue;

                    RoomController roomController = new RoomController();
                    loader.setController(roomController);

                    Node node = loader.load();
                    list.getChildren().add(node);
                    roomController.setRoom(gameRoom);
                }

            } catch (Exception e)
            { e.printStackTrace(); }
        });
    }
}
