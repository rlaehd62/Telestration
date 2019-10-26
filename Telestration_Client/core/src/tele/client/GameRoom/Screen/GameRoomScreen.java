package tele.client.GameRoom.Screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import tele.client.GameRoom.GameRoomPresenter;
import tele.client.GameRoom.MVP.GameRoomMVP;

public class GameRoomScreen extends ScreenAdapter
{
    private GameRoomMVP.Presenter presenter;
    public GameRoomScreen()
    {
        presenter = new GameRoomPresenter();
        presenter.setupView();
    }

    public void render(float delta)
    {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        presenter.updateActors();
    }
}
