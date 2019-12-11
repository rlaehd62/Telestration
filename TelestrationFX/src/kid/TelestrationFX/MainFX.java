package kid.TelestrationFX;

import javafx.scene.media.AudioClip;
import javafx.scene.media.MediaPlayer;
import kid.Network.Client;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Properties;

public class MainFX extends Application
{
    public static String IP;
    public static String PORT;

    public static String BGM;
    public static String CLICK;
    public static String SWITCH;

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setResizable(false);
        primaryStage.setTitle("텔레스트레이션 (Telestration)");

        init();
        Client.getInstance().startServer();

        Scene main = new Scene(FXMLLoader.load(getClass().getResource("Layout/Login.fxml")));
        primaryStage.setScene(main);

        ScreenManager sm = ScreenManager.getInstance();
        sm.setMain(main);

        sm.addScreen("Login", FXMLLoader.load(getClass().getResource("Layout/Login.fxml")));
        sm.addScreen("WaitRoom", FXMLLoader.load(getClass().getResource("Layout/WaitRoom.fxml")));
        sm.addScreen("GameRoom", FXMLLoader.load(getClass().getResource("Layout/GameRoom.fxml")));
        sm.addScreen("Test", FXMLLoader.load(getClass().getResource("Layout/TEST.fxml")));
        sm.addScreen("ResultScreen", FXMLLoader.load(getClass().getResource("Layout/RESULT.fxml")));
        sm.activate("Login");

        primaryStage.centerOnScreen();
        primaryStage.show();

        Class c = this.getClass();
        String BASE = c.getResource("/kid/Audio/").toExternalForm();
        System.out.println(BASE);

        BGM = BASE + "BGM.wav";
        CLICK = BASE + "click.wav";
        SWITCH = BASE + "switch.wav";

        AudioClip bgm = new AudioClip(BGM);
        bgm.setCycleCount(MediaPlayer.INDEFINITE);
        bgm.setVolume(0.5);
        bgm.play();
    }

    public void stop() throws Exception
    {
        Client.getInstance().stopServer();
        Platform.exit();
    }

    public void init()
    {
        try
        {
            File file = new File(System.getProperty("user.home") + "./System/config.properties");
            Properties properties = new Properties();
            properties.setProperty("IP", "localhost");

            if(!file.exists())
            {
                file.getParentFile().mkdirs();
                properties.store(new FileOutputStream(file), "");
            }
            else properties.load(new FileInputStream(file));

            IP = properties.getProperty("IP");
            PORT = "9999";

        } catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    public static void main(String[] args)
    {
        launch(args);
    }
}
