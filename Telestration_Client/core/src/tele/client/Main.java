package tele.client;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import tele.client.Login.Screen.LoginScreen;

public class Main extends Game
{
	public void create ()
	{
		setScreen(new LoginScreen());
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
