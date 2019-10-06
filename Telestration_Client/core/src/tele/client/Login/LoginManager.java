package tele.client.Login;

import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import tele.client.Login.Interface.LoginPresenter;

public class LoginManager implements LoginPresenter
{
    private LoginModel model;
    private LoginView view;

    public LoginManager()
    {
        model = new LoginProcessor();
        model.setPresenter(this);

        view = new LoginLayout();
        view.setPresenter(this);
    }

    public void addActor(Actor actor)
    {
        model.addActor(actor);
    }

    public void addListener(String name, InputListener listener)
    {
        model.addListener(name, listener);
    }

    public void addListener(String name, ClickListener listener)
    {
        model.addListener(name, listener);
    }

    public void updateActors()
    {
        Stage stage = model.getStage();
        stage.act();
        stage.draw();
    }

    public void setupView()
    {
        view.initLayout();
    }
}
