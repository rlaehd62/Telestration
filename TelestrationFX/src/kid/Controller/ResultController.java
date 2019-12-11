package kid.Controller;

import DTO.Notification.GameRoom.RewardNotification;
import DTO.Request.GameRoom.ReportRequest;
import Game.RoundSet;
import Game.SketchBookSet;
import Util.SketchBook;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPopup;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import javafx.scene.media.AudioClip;
import kid.GameData.RoomInfo;
import kid.Network.Client;
import kid.TelestrationFX.MainFX;
import kid.TelestrationFX.ScreenManager;

import javax.swing.*;
import java.io.IOException;
import java.util.*;

public class ResultController
{

    @FXML
    private Canvas canvas;

    @FXML
    private Label word;

    @FXML
    private Label round;

    @FXML
    private Label owner;

    @FXML
    private JFXButton left;

    @FXML
    private JFXButton up;

    @FXML
    private JFXButton right;

    @FXML
    private JFXButton down;

    @FXML
    private VBox list;

    @FXML
    private JFXButton goback;

    @FXML
    private JFXButton report;

    private static ResultController con;
    private ScreenManager sm;
    private RewardNotification notification;
    private GraphicsContext gc;

    public ResultController()
    {
        con = this;
        sm = ScreenManager.getInstance();
    }

    public static ResultController getController()
    {
        return con;
    }

    public void setNotification(RewardNotification notification)
    {
        this.notification = notification;
    }
    public void UpdateResult()
    {
        try
        {
            clearCanvas();
            owner.setText("");
            word.setText("");
            round.setText("");
            report.setDisable(false);

            // TODO: 결과 화면 테스트를 위한 코드 추후 보강하기 (실용적으로)
            updateCanvas();
            updateScores();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    private void updateCanvas()
    {
        Platform.runLater(() ->
        {
            RoundSet roundSet = notification.getRoundSet();
            SketchBookSet set = roundSet.current();
            SketchBook book = set.current();
            System.out.println(Objects.isNull(book.toImage()));
            reDraw(book);
        });
    }

    private void updateScores()
    {
        list.getChildren().clear();
        Map<String, Integer> users = notification.getUsers();
        users.forEach((ID, VALUE) -> createResultItem("Layout/RESULT-ITEM.fxml", ID, VALUE));

        Map<String, Integer> words = notification.getWords();
        words.forEach((ID, VALUE) -> createResultItem("Layout/RESULT-ITEM.fxml", ID, VALUE));
    }

    private void createResultItem(String path, String name, int exp)
    {
        Platform.runLater(() ->
        {
            FXMLLoader loader = new FXMLLoader(getClass().getResource(path));
            ResultItemController co = new ResultItemController();
            loader.setController(co);

            Node node = null;
            try
            { node = loader.load(); }
            catch (IOException e)
            { e.printStackTrace(); }

            list.getChildren().add(node);
            co.setLabel(name, exp);
        });
    }

    public void reDraw(SketchBook sketchBook)
    {
        clearCanvas();
        Platform.runLater(() ->
        {
            if(gc == null) gc = canvas.getGraphicsContext2D();
            RoundSet roundSet = notification.getRoundSet();
            SketchBookSet set = roundSet.current();

            owner.setText(sketchBook.getOwner());
            word.setText(sketchBook.getSecretWord());
            round.setText(roundSet.round() + " ROUND");

            WritableImage snapshot = SwingFXUtils.toFXImage(sketchBook.toImage(), null);
            gc.drawImage(snapshot, 0, 0, canvas.getWidth(), canvas.getHeight());
        });
    }

    public void clearCanvas()
    {
        Platform.runLater(() ->
        {
            if(gc == null) gc = canvas.getGraphicsContext2D();
            gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        });
    }

    @FXML
    void goback(ActionEvent event)
    {
        AudioClip bgm = new AudioClip(MainFX.CLICK);
        bgm.play();
        sm.activate("Test");
    }

    @FXML
    void nextOwner(ActionEvent event)
    {
        AudioClip bgm = new AudioClip(MainFX.CLICK);
        bgm.play();

        RoundSet roundSet = notification.getRoundSet();
        SketchBookSet set = roundSet.current();
        set.next();
        reDraw(set.current());
    }

    @FXML
    void nextRound(ActionEvent event)
    {
        AudioClip bgm = new AudioClip(MainFX.CLICK);
        bgm.play();

        RoundSet roundSet = notification.getRoundSet();
        roundSet.next();
        updateCanvas();
    }

    @FXML
    void previousOwner(ActionEvent event)
    {
        AudioClip bgm = new AudioClip(MainFX.CLICK);
        bgm.play();

        RoundSet roundSet = notification.getRoundSet();
        SketchBookSet set = roundSet.current();
        set.previous();
        reDraw(set.current());
    }

    @FXML
    void previousRound(ActionEvent event)
    {
        AudioClip bgm = new AudioClip(MainFX.CLICK);
        bgm.play();

        RoundSet roundSet = notification.getRoundSet();
        roundSet.previous();
        updateCanvas();
    }

    @FXML
    void reportUser(ActionEvent event)
    {
        Platform.runLater(() ->
        {
            VBox box = new VBox();
            JFXPopup popup = new JFXPopup();

            Arrays.stream(RoomInfo.getInstance().getUserList())
                    .forEach(ID ->
                    {
                        JFXButton button = new JFXButton(ID);
                        button.setPadding(new Insets(10));
                        button.setOnAction(actionEvent ->
                        {
                            ReportRequest reportRequest = new ReportRequest(ID);
                            Client.getInstance().send(reportRequest);
                            report.setDisable(true);
                        });
                        box.getChildren().add(button);
                    });

            popup.setPopupContent(box);
            popup.show(report, JFXPopup.PopupVPosition.TOP, JFXPopup.PopupHPosition.LEFT);
        });

    }
}
