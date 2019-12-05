package kid.Controller;

import DTO.Notification.GameRoom.RewardNotification;
import Game.RoundSet;
import Game.SketchBookSet;
import Util.SketchBook;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.embed.swing.SwingFXUtils;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.VBox;
import kid.TelestrationFX.ScreenManager;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

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
        Map<String, Integer> result = new HashMap<>();
        notification.getUsers().forEach(result::put);
        notification.getWords().forEach(result::put);

        result.forEach((name, exp) ->
        {
            Platform.runLater(() ->
            {
                FXMLLoader loader = new FXMLLoader(getClass().getResource("Layout/RESULT-ITEM.fxml"));
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
            round.setText("0 ROUND");

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
        sm.activate("Test");
    }

    @FXML
    void nextOwner(ActionEvent event)
    {
        RoundSet roundSet = notification.getRoundSet();
        SketchBookSet set = roundSet.current();
        set.next();
        reDraw(set.current());
    }

    @FXML
    void nextRound(ActionEvent event)
    {
        RoundSet roundSet = notification.getRoundSet();
        roundSet.next();
        updateCanvas();
    }

    @FXML
    void previousOwner(ActionEvent event)
    {
        RoundSet roundSet = notification.getRoundSet();
        SketchBookSet set = roundSet.current();
        set.previous();
        reDraw(set.current());
    }

    @FXML
    void previousRound(ActionEvent event)
    {
        RoundSet roundSet = notification.getRoundSet();
        roundSet.previous();
        updateCanvas();
    }
}
