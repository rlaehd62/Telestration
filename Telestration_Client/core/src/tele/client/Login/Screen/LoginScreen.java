package tele.client.Login.Screen;

import com.badlogic.gdx.ScreenAdapter;
import tele.client.Login.Interface.LoginPresenter;
import tele.client.Login.LoginManager;
import tele.client.Network.Client;
import tele.client.Network.GameClient;

public class LoginScreen extends ScreenAdapter
{
    private LoginPresenter presenter;
    private GameClient client;

    public LoginScreen()
    {
        presenter = new LoginManager();
        presenter.setupView();

        client = Client.getInstance();
        client.startServer();
    }

    public void render(float delta)
    {
        presenter.updateActors();
    }
}
