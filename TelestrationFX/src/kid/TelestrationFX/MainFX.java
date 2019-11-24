package kid.TelestrationFX;

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
import java.util.Properties;

public class MainFX extends Application
{
    public static String IP;
    public static String PORT;

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
        sm.activate("Login");

        primaryStage.centerOnScreen();
        primaryStage.show();
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
