package kid.TelestrationFX;

import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.util.HashMap;

public class ScreenManager
{
    private static ScreenManager sm = null;
    private HashMap<String, Pane> screenMap = new HashMap<>();
    private Scene main;

    private ScreenManager() { }
    public static ScreenManager getInstance()
    {
        return (sm != null) ? (sm) : (sm = new ScreenManager());
    }

    public void setMain(Scene scene)
    {
        this.main = scene;
    }

    public void addScreen(String name, Pane pane)
    {
        screenMap.put(name, pane);
    }

    protected void removeScreen(String name)
    {
        screenMap.remove(name);
    }

    public void activate(String name)
    {
        Platform.runLater(() ->
        {
            Pane root = screenMap.get(name);

            main.setRoot(root);
            main.getWindow().sizeToScene();
        });
    }
}
