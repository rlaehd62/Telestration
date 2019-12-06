package kid.Controller;

import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class ResultItemController
{

    @FXML
    private Label name;

    @FXML
    private Label exp;

    public void setLabel(String tag, int value)
    {
        name.setText(tag);
        exp.setText(value + "");
    }

}
