package tele.client.GameRoom;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.SplitPane;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.Window;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import tele.client.GameRoom.MVP.GameRoomMVP;
import tele.client.Main;
import tele.client.Room.Data.RoomInfo;

public class GameRoomView implements GameRoomMVP.View
{
    private GameRoomMVP.Presenter presenter;
    private Stage stage;
    private Skin skin;

    private Window root;
    private Table left;
    private Table right;

    public GameRoomView()
    {
        stage = new Stage(new ScreenViewport());
        skin = new Skin(Gdx.files.internal(Main.SKIN));
        Gdx.input.setInputProcessor(stage);
    }

    public void updateActors()
    {
        stage.act(Gdx.graphics.getDeltaTime());
    }

    public void drawActors()
    {
        stage.draw();
    }

    public void initLayout()
    {
        RoomInfo info = RoomInfo.getInstance();
        root = new Window(info.getTitle(), skin, "main");
        root.setFillParent(true);
        root.setMovable(false);
        root.setDebug(true);

        left = new Table(skin);
        left.defaults().width(1030f);

        right = new Table(skin);
        right.defaults().width(220f);
    }

    public void loadLayout()
    {
        SplitPane splitPane = new SplitPane(left, right, false, skin);
        root.add(splitPane).grow();
        stage.addActor(root);
    }

    public void loadListener()
    {

    }

    public void setPresenter(GameRoomMVP.Presenter presenter)
    {
        this.presenter = presenter;
    }
}
