package tele.client.Room;

import DTO.Response.RoomListResponse;
import DTO.Response.RoomResponse;
import DTO.Response.UserResponse;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import tele.client.Main;
import tele.client.Room.Components.Room;
import tele.client.Room.Interface.RoomMVP;

public class RoomView implements RoomMVP.View
{
    private RoomMVP.Presenter presenter;
    private Stage stage;
    private Skin skin;

    private Window root;
    private Table left;
    private Table right;
    private Table list;
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
        list.clear();
        for(RoomResponse room : response.getRooms())
        {
            Room r = new Room(room, skin);

            list.add(r).expandX().fillX().left().pad(5f, 5f, 5f, 5f);
            list.row();
        }

    }

    public void hideInfo()
    {
        name.setText("NOT-NAMED");
        level.setText("Lv. 0");
        exp.setText("EXP (0 / 100)");
    }

    public void showInfo(UserResponse response)
    {
        name.setText(response.getID());
        level.setText("Lv. " +response.getLevel());
        exp.setText("EXP (" + response.getExp() + " / " + response.getMaxExp() + ")");
    }

    public void initLayout()
    {
        root = new Window("Waiting Room", skin, "main");

        name = new Label("NOT-NAMED", skin);
        level = new Label("Lv. 0", skin);
        exp = new Label("EXP (0 / 100)", skin);
        list = new Table(skin);

        roomList = new ScrollPane(list, skin);
        roomList.setFadeScrollBars(false);
        roomList.setFlickScroll(false);
    }

    public void loadLayout()
    {
        root.setFillParent(true);
        root.setMovable(false);
        stage.setScrollFocus(roomList);


        left = new Table(skin);
        left.defaults().width(1030f);

        right = new Table(skin);
        SplitPane splitPane = new SplitPane(left, right, false, skin);
        root.add(splitPane).grow();

        left.add(new Label("[ Room List ]\nRoom No\t\tTitle\t\t\tOwner", skin, "title")).row();
        left.add(roomList).grow().fill().padTop(10f);

        Table top = new Table(skin);
        Table bottom = new Table(skin);

        SplitPane sp2 = new SplitPane(top, bottom, true, skin);
        right.add(sp2).grow();

        top.add(new Label("My Profile", skin, "title")).row();
        top.add(name).padBottom(10.0f).row();
        top.add(level).padBottom(10.0f).row();
        top.add(exp);

        TextButton button = new TextButton("Submit", skin);
        Label label = new Label("Title", skin);
        TextField field = new TextArea("", skin);
        Label label2 = new Label("Limit", skin);
        TextField field2 = new TextArea("", skin);


        bottom.defaults().padLeft(1f);
        bottom.defaults().padBottom(2f);
        bottom.add(new Label("Create Room", skin, "title")).colspan(2).expandX().fillX().left().padBottom(10f).row();
        bottom.add(label).left();
        bottom.add(field).left().row();
        bottom.add(label2).left();
        bottom.add(field2).left().row();
        bottom.add(button).colspan(2).fillX();
        stage.addActor(root);
    }

    public void loadListener()
    {
        root.addListener(new InputListener()
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
