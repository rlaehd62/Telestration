package kid.Controller;

import DTO.Notification.GameRoom.RewardNotification;
import com.jfoenix.controls.JFXButton;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import kid.TelestrationFX.ScreenManager;

import java.util.Map;

public class ResultController {

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

    public ResultController()
    {
        con = this;
        sm = ScreenManager.getInstance();
    }

    public static ResultController getController()
    {
        return con;
    }

    public void UpdateResult(RewardNotification notification)
    {
        System.out.println("되긴됨?");
        Platform.runLater(() ->
        {
            list.getChildren().clear();
            try
            {
                Map<String, Integer> result = notification.getResult();
                for(String key : notification.getResult().keySet())
                {
                    FXMLLoader loader = new FXMLLoader(getClass().getResource("Layout/RESULT-ITEM.fxml"));
                    ResultItemController co = new ResultItemController();
                    loader.setController(co);

                    Node node = loader.load();
                    list.getChildren().add(node);
                    co.setLabel(key, result.get(key));
                }
            }

            catch (Exception e)
            { e.printStackTrace(); }
        });
    }

    @FXML
    void goback(ActionEvent event)
    {
        sm.activate("Test");
    }

    @FXML
    void nextOwner(ActionEvent event) {

    }

    @FXML
    void nextRound(ActionEvent event) {

    }

    @FXML
    void previousOwner(ActionEvent event) {

    }

    @FXML
    void previousRound(ActionEvent event) {

    }

}
