package TelestrationFX;

import Network.Client;
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

    /*
     * [ To Do ]
     * - 방에 접속하고 정보 브로드캐스트 (구현)
     * - 채팅 기능 구현하기 (구현)
     * - 클라이언트 종료 시, 해당 유저 룸에서 제거하고 정보 브로드캐스트
     */

    @Override
    public void start(Stage primaryStage) throws Exception
    {
        primaryStage.setResizable(false);
        primaryStage.setTitle("텔레스트레이션 (Telestration)");

        init();
        Client.getInstance().startServer();

<<<<<<< HEAD
        Scene main = new Scene(FXMLLoader.load(getClass().getResource("../Layout/Login.fxml")));
=======
        Scene main = new Scene(FXMLLoader.load(getClass().getResource("Layout/Login.fxml")));
>>>>>>> dev
        primaryStage.setScene(main);

        ScreenManager sm = ScreenManager.getInstance();
        sm.setMain(main);

<<<<<<< HEAD
        sm.addScreen("Login", FXMLLoader.load(getClass().getResource("../Layout/Login.fxml")));
        sm.addScreen("WaitRoom", FXMLLoader.load(getClass().getResource("../Layout/WaitRoom.fxml")));
=======
        sm.addScreen("Login", FXMLLoader.load(getClass().getResource("Layout/Login.fxml")));
        sm.addScreen("WaitRoom", FXMLLoader.load(getClass().getResource("Layout/WaitRoom.fxml")));
<<<<<<< HEAD
>>>>>>> dev
=======
        sm.addScreen("GameRoom", FXMLLoader.load(getClass().getResource("Layout/GameRoom.fxml")));
>>>>>>> dev
        sm.activate("Login");

        primaryStage.centerOnScreen();
        primaryStage.show();
    }

    @Override
    public void stop() throws Exception
    {
        Client.getInstance().stopServer();
        Platform.exit();
    }

    public void init()
    {
        try
        {
            File file = new File(System.getProperty("user.home") + "/Telestration/config.properties");
            Properties properties = new Properties();
            properties.setProperty("IP", "localhost");

            if(!file.exists()) properties.store(new FileOutputStream(file), "");
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
