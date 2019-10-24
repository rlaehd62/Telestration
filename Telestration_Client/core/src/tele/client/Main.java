package tele.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import tele.client.Login.Screen.LoginScreen;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

public class Main extends Game
{
	public static String SKIN = "UI/Telestration.json";
	public static String IP;
	public static String PORT;

	public void create ()
	{
		init();
		setScreen(new LoginScreen());
	}

	public void init()
	{
		try
		{
			File file = new File(System.getProperty("user.home") + "/Telestration/config.properties");
			Properties properties = new Properties();
			properties.setProperty("IP", "localhost");
			properties.setProperty("PORT", "9999");

			if(!file.exists()) properties.store(new FileOutputStream(file), "");
			else properties.load(new FileInputStream(file));

			IP = properties.getProperty("IP");
			PORT = properties.getProperty("PORT");

			System.out.println(IP);
			System.out.println(PORT);

		} catch (IOException e)
		{
			e.printStackTrace();
		}
	}

	public void render ()
	{
		screen.render(Gdx.graphics.getDeltaTime());
	}

	public void dispose ()
	{
		screen.dispose();
	}
}
