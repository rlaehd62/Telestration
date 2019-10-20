package tele.client.Login.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
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
        Gdx.gl.glClearColor(0, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        presenter.updateActors();
    }

    public void dispose()
    {
        presenter.dispose();
    }
}
