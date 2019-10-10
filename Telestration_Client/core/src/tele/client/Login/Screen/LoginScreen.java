package tele.client.Login.Screen;

import com.badlogic.gdx.ScreenAdapter;
import tele.client.Login.Interface.LoginMVP;
import tele.client.Login.LoginPresenter;
import tele.client.Network.Client;
import tele.client.Network.GameClient;

public class LoginScreen extends ScreenAdapter
{
    private LoginMVP.Presenter presenter;

    public LoginScreen()
    {
        presenter = new LoginPresenter();
        presenter.setupNetwork();
        presenter.setupView();
    }

    public void render(float delta)
    {
        presenter.updateActors();
    }
}
