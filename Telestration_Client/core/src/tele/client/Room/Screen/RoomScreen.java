package tele.client.Room.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import tele.client.Room.Interface.RoomMVP;
import tele.client.Room.RoomPresenter;

public class RoomScreen extends ScreenAdapter
{
    private RoomMVP.Presenter presenter;

    public RoomScreen()
    {
        presenter = RoomPresenter.getInstance();
        presenter.setupView();
    }

    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0.2f, 0.2f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        presenter.updateActors();
    }
}
