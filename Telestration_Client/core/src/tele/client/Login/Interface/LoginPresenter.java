package tele.client.Login.Interface;

import DTO.Request.GamePacket;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.Viewport;

import java.util.List;

public interface LoginPresenter
{
    void addActor(Actor actor);
    void addListener(String name, InputListener listener);
    void addListener(String name, ClickListener listener);
    void updateActors();
    void setupView();
    void send(GamePacket packet);

    interface LoginModel
    {
        void setViewPort(Viewport viewport);
        void setPresenter(LoginPresenter presenter);

        void addActor(Actor actor);
        void addListener(String name, InputListener listener);
        void addListener(String name, ClickListener listener);
        Stage getStage();
        List<Actor> getActors();
        Actor searchActor(String name);
    }

    interface LoginView
    {
        void initLayout();
        void loadLayout();
        void loadListener();
        void setSkin(String url);
        void setPresenter(LoginPresenter presenter);
    }
}
