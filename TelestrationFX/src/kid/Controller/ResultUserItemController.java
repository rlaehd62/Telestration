package kid.Controller;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.event.EventType;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.media.AudioClip;
import kid.TelestrationFX.MainFX;

public class ResultUserItemController
{

    @FXML
    private Label name;

    @FXML
    private Label exp;

    @FXML
    private ImageView report;

    public void setItem(String tag, int value)
    {
        name.setText(tag);
        exp.setText(value + "");
        report.addEventHandler(MouseEvent.MOUSE_CLICKED, mouseEvent -> Platform.runLater(() ->
        {
            AudioClip bgm = new AudioClip(MainFX.CLICK);
            bgm.play();
            System.out.println(name + "을 신고하려고 시도함 ..");
        }));
    }
}
