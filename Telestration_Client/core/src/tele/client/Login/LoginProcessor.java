package tele.client.Login;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.Viewport;
import tele.client.Login.Interface.LoginPresenter;

import java.util.ArrayList;
import java.util.List;

public class LoginProcessor implements LoginPresenter.LoginModel
{
    private Stage stage;
    private LoginPresenter presenter;

    public LoginProcessor()
    {
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        Gdx.input.setInputProcessor(stage);
    }

    public void setViewPort(Viewport viewport)
    {
        stage.setViewport(viewport);
    }

    public void addActor(Actor actor)
    {
        if(actor != null)
            stage.addActor(actor);
    }

    public void addListener(String name, InputListener listener)
    {
        Actor actor = searchActor(name);
        if(actor != null) stage.addActor(actor);
    }

    public void addListener(String name, ClickListener listener)
    {
        Actor actor = searchActor(name);
        if(actor != null) stage.addActor(actor);
    }

    public Stage getStage()
    {
        return stage;
    }

    public List<Actor> getActors()
    {
        List<Actor> list = new ArrayList<>();
        Array<Actor> actors = stage.getActors();
        actors.forEach(list::add);
        return list;
    }

    public void setPresenter(LoginPresenter presenter)
    {
        this.presenter = presenter;
    }

    public Actor searchActor(String name)
    {
        return null;
    }
}
