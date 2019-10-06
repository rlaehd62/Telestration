package tele.client.Login.Screen;

import com.badlogic.gdx.ScreenAdapter;
import tele.client.Login.Interface.LoginPresenter;
import tele.client.Login.LoginManager;

public class LoginScreen extends ScreenAdapter
{
    private LoginPresenter presenter;

    public LoginScreen()
    {
        presenter = new LoginManager();
        presenter.setupView();
    }

    public void render(float delta)
    {
        presenter.updateActors();
    }
}
