package tele.client.Room;

import DTO.Response.RoomListResponse;
import DTO.Response.RoomResponse;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import tele.client.Main;
import tele.client.Room.Components.Room;
import tele.client.Room.Interface.RoomMVP;

import java.util.ArrayList;

public class RoomView implements RoomMVP.View
{
    private RoomMVP.Presenter presenter;
    private Stage stage;
    private Skin skin;

    private Window root;
    private List<Button> rooms;
    private ScrollPane roomList;

    private Label name;
    private Label level;
    private Label exp;

    public RoomView()
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

    public void setRoomList(RoomListResponse response)
    {
        rooms.clearItems();
        for(RoomResponse room : response.getRooms())
            rooms.getItems().add(new Room(room));

    }

    public void initLayout()
    {
        root = new Window("Waiting Room", skin, "main");

        name = new Label("NOT-NAMED", skin);
        level = new Label("Lv. 0", skin);
        exp = new Label("EXP (0 / 0)", skin);

        rooms = new List<>(skin);
        roomList = new ScrollPane(rooms, skin);
        roomList.setFadeScrollBars(false);
        roomList.setFlickScroll(false);
    }

    public void loadLayout()
    {
        root.setFillParent(true);
        root.setMovable(false);

        Table left = new Table(skin);
        Table right = new Table(skin);
        SplitPane splitPane = new SplitPane(left, right, false, skin);
        root.add(splitPane).grow();

        left.add(new Label("Room List", skin, "title")).row();
        left.add(roomList).grow().padTop(10.0f).padBottom(10.0f);

        Table top = new Table(skin);
        Table bottom = new Table(skin);
        SplitPane sp2 = new SplitPane(top, bottom, true, skin);
        right.add(sp2).grow();

        top.add(new Label("My Profile", skin, "title")).top().row();
        top.add(name).padBottom(10.0f).row();
        top.add(level).padBottom(10.0f).row();
        top.add(exp);

        bottom.add(new Label("Create Room", skin, "title")).top();
        stage.addActor(root);
    }

    public void loadListener()
    {
        roomList.addListener(new InputListener()
        {
            public boolean keyDown(InputEvent event, int keycode)
            {
                switch(event.getKeyCode())
                {
                    case Input.Keys.F5:
                        presenter.updateRoomList();
                        break;
                }

                return true;
            }
        });
    }

    public void setPresenter(RoomMVP.Presenter presenter)
    {
        this.presenter = presenter;
    }
}
