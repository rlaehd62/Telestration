package kid.Controller;

import DTO.Request.GameRoom.ChatRequest;
import DTO.Request.GameRoom.ExitRoomRequest;
import DTO.Request.GameRoom.SendSketchBookRequest;
import DTO.Request.Room.RoomListRequest;
import DTO.Request.Users.UserInfoRequest;
import DTO.Response.GameRoom.ChatResponse;
import Util.SketchBook;
import com.jfoenix.controls.*;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.SnapshotParameters;
import javafx.scene.image.WritableImage;
import javafx.scene.paint.Color;
import kid.GameData.Account;
import kid.GameData.RoomInfo;
import kid.GameData.User;
import kid.Network.Client;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.input.*;
import kid.TelestrationFX.ScreenManager;

import java.awt.image.BufferedImage;
import java.util.List;
import java.util.Objects;


public class GameRoomController
{
    @FXML
    private JFXListView<Label> list;

    @FXML
    private JFXTextArea chatArea;

    @FXML
    private JFXTextField chatField;

    @FXML
    private Canvas canvas;

    @FXML
    private Label title;

    @FXML
    private Label owner;

    @FXML
    private JFXColorPicker colorPicker;

    @FXML
    private JFXTextField word;

    @FXML
    private JFXButton exit;

    private static GameRoomController controller;
    private SketchBook sketchBook;
    private GraphicsContext gc;
    private Client client;

    public GameRoomController()
    {
        this.controller = this;
        this.sketchBook = new SketchBook("");
        client = Client.getInstance();
    }

    public static GameRoomController getInstance()
    {
        return controller;
    }

    public void UpdateUserList()
    {
        Platform.runLater(() ->
        {
            RoomInfo info = RoomInfo.getInstance();
            if(info == null) return;

            list.getItems().clear();
            for(String name : info.getUserList())
                list.getItems().add(new Label(name));
        });
    }

    public void UpdateRoomInfo()
    {
        Platform.runLater(() ->
        {
            RoomInfo info = RoomInfo.getInstance();
            if(info == null) return;

            title.setText(info.getTitle());
            owner.setText(info.getOwner());
        });
    }

    public void receiveChat(ChatResponse response)
    {
        Platform.runLater(() ->
        {
            String text = response.toString() + "\n";
            chatArea.appendText(text);
        });
    }

    @FXML
    void chat(KeyEvent event)
    {
        Platform.runLater(() ->
        {
            KeyCode key = event.getCode();

            if(key.equals(KeyCode.ENTER))
            {
                String chat = chatField.getText();
                boolean isVALID = (!chat.isEmpty());
                if(!isVALID) return;

                String ID = Account.getInstance().getID();
                String OWNER = RoomInfo.getInstance().getOwner();

                ChatRequest request = new ChatRequest(ID, OWNER, chat);
                System.out.println("[보낼 메세지] " + ID + ": " + request.getText());
                client.send(request);
                chatField.clear();
            }
        });
    }

    @FXML
    void exitRoom(ActionEvent event)
    {
        System.out.println("감지됨");
        Platform.runLater(() ->
        {
            RoomInfo info = RoomInfo.getInstance();
            ScreenManager sm = ScreenManager.getInstance();
            WaitRoomController wc = WaitRoomController.getInstance();
            String ID = Account.getInstance().getID();

            client.send(new ExitRoomRequest(ID, info.getOwner()));
            wc.clearList();
            sm.activate("WaitRoom");
            client.send(new UserInfoRequest(Account.getInstance().getID()));
            client.send(new RoomListRequest(Account.getInstance().getID(), 10));
        });

    }

    @FXML
    void pressMouse(MouseEvent event)
    {
        Platform.runLater(() ->
        {
            if(gc == null) gc = canvas.getGraphicsContext2D();
            gc.setStroke(colorPicker.getValue());
            gc.beginPath();
            gc.moveTo(event.getX(), event.getY());
            gc.stroke();
        });
    }

    @FXML
    void eraseCanvas(ActionEvent event)
    {
        Platform.runLater(() ->
        {
            String ID = Account.getInstance().getID();
            String OWNER = RoomInfo.getInstance().getOwner();

            WritableImage snapshot = canvas.snapshot(null, null);
            BufferedImage image = SwingFXUtils.fromFXImage(snapshot, null);
            sketchBook.toByte(image);
            sketchBook.setSecretWord("사용자정의 (Custom)");

            SendSketchBookRequest request = new SendSketchBookRequest(sketchBook, ID, OWNER);
            client.send(request);
        });

        Platform.runLater(() ->
        {
            if(gc == null) gc = canvas.getGraphicsContext2D();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        });
    }

    @FXML
    void dragMouse(MouseEvent event)
    {
        Platform.runLater(() ->
        {
            gc.setStroke(colorPicker.getValue());
            gc.lineTo(event.getX(), event.getY());
            gc.stroke();
        });
    }

    public void reDraw(SketchBook sketchBook)
    {
        Platform.runLater(() ->
        {
            if(gc == null) gc = canvas.getGraphicsContext2D();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            WritableImage snapshot = SwingFXUtils.toFXImage(sketchBook.toImage(), null);
            gc.drawImage(snapshot, 0, 0, canvas.getWidth(), canvas.getHeight());
        });
    }

    @FXML
    void releaseMouse(MouseEvent event)
    {

    }
}
