package kid.Controller;

import DTO.Request.GameRoom.ChatRequest;
import DTO.Request.GameRoom.ExitRoomRequest;
import DTO.Request.GameRoom.GameStartRequest;
import DTO.Request.GameRoom.SendSketchBookRequest;
import DTO.Request.Room.RoomListRequest;
import DTO.Request.Users.UserInfoRequest;
import DTO.Response.GameRoom.ChatResponse;
import Util.SketchBook;
import com.jfoenix.controls.*;
import com.sun.xml.internal.bind.v2.model.core.ID;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.SnapshotParameters;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressIndicator;
import javafx.scene.image.WritableImage;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import kid.GameData.Account;
import kid.GameData.RoomInfo;
import kid.Network.Client;
import kid.TelestrationFX.ScreenManager;

import java.awt.image.BufferedImage;

public class TestController
{
    @FXML
    private Label title;

    @FXML
    private Label owner;

    @FXML
    private JFXButton exit;

    @FXML
    private Canvas canvas;

    @FXML
    private VBox userList;

    @FXML
    private JFXTextArea chatArea;

    @FXML
    private JFXColorPicker picker;

    @FXML
    private JFXSlider slider;

    @FXML
    private JFXButton erase;

    @FXML
    private JFXTextField chatField;

    @FXML
    private JFXButton submit;

    @FXML
    private JFXTextField word;

    @FXML
    private JFXButton start;

    @FXML
    private JFXProgressBar timer;

    private static TestController controller;
    private GraphicsContext gc;
    private Client client;
    private boolean isPainter;
    private SketchBook received;

    public TestController()
    {
        controller = this;
        isPainter = false;
        client = Client.getInstance();
    }

    public static TestController getController()
    {
        return controller;
    }

    public void setPainter(boolean painter)
    {
        isPainter = painter;
    }
    public boolean isPainter()
    {
        return isPainter;
    }

    public void init()
    {
        if(gc == null) gc = canvas.getGraphicsContext2D();
        Platform.runLater(() ->
        {
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
            word.clear();
            chatArea.clear();
            chatField.clear();
            timer.setProgress(ProgressIndicator.INDETERMINATE_PROGRESS);
        });
    }

    public void UpdateUserList()
    {
        Platform.runLater(() ->
        {
            RoomInfo info = RoomInfo.getInstance();
            if(info == null) return;
            userList.getChildren().clear();

            String ID = Account.getInstance().getID();
            String OWNER = info.getOwner();
            start.setVisible(ID.equals(OWNER));
            submit.setText(ID);
            for(String name : info.getUserList()) userList.getChildren().add(new Label(name));
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
    void start(ActionEvent event)
    {
        String ID = Account.getInstance().getID();
        client.send(new GameStartRequest(RoomInfo.getInstance().getOwner()));
        start.setVisible(false);
    }

    @FXML
    void exit(ActionEvent event)
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
    void clearCanvas(ActionEvent event)
    {
        Platform.runLater(() ->
        {
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        });
    }

    public void clearCanvas()
    {
        Platform.runLater(() ->
        {
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        });
    }

    @FXML
    void press(MouseEvent event)
    {
        Platform.runLater(() ->
        {
            if(gc == null) gc = canvas.getGraphicsContext2D();
            gc.setStroke(picker.getValue());
            gc.beginPath();
            gc.setLineWidth(slider.getValue());
            gc.moveTo(event.getX(), event.getY());
            gc.stroke();
        });
    }

    @FXML
    void drag(MouseEvent event)
    {
        Platform.runLater(() ->
        {
            gc.setStroke(picker.getValue());
            gc.lineTo(event.getX(), event.getY());
            gc.stroke();
        });
    }

    public void sendCurrentCanvas()
    {
        String ID = Account.getInstance().getID();
        String OWNER = (received == null) ? ID : received.getOwner();
        String ROOM_OWNER = RoomInfo.getInstance().getOwner();
        SketchBook sketchBook = new SketchBook(OWNER, getWord());

        Platform.runLater(() ->
        {
            WritableImage snapshot = canvas.snapshot(new SnapshotParameters(), null);
            BufferedImage image = SwingFXUtils.fromFXImage(snapshot, null);

            sketchBook.toByte(image);
            sketchBook.setSecretWord(getWord());
            sketchBook.setPainter(isPainter);

            SendSketchBookRequest request = new SendSketchBookRequest(sketchBook, ID, ROOM_OWNER);
            client.send(request);
        });

    }

    public void setWord(String word, boolean isPainter)
    {
        Platform.runLater(() ->
        {
            this.word.setText(word);
        });
    }

    public String getWord()
    {
        return word.getText();
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

    public void setTimer(double per)
    {
        Platform.runLater(() ->
        {
            timer.setProgress(per);
        });
    }

    public void setReceived(SketchBook received)
    {
        this.received = received;
    }
}
