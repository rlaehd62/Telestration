package tele.client.Room;

import DTO.Response.RoomListResponse;
import DTO.Response.RoomResponse;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.FitViewport;
import javafx.scene.input.KeyCode;
import tele.client.Room.Interface.RoomMVP;

import java.util.ArrayList;

public class RoomView implements RoomMVP.View
{
    private RoomMVP.Presenter presenter;
    public static final String SPLITOR = " | ";
    private Stage stage;
    private Skin skin;

    private Table root;
    private List<Label> rooms;
    private ScrollPane roomList;

    private VerticalGroup group;
    private Label name;
    private Label level;
    private Label exp;

    public RoomView()
    {
        stage = new Stage(new FitViewport(Gdx.graphics.getWidth(), Gdx.graphics.getHeight()));
        skin = new Skin(Gdx.files.internal("Skin/Particle Park UI.json"));
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
        rooms.clear();
        java.util.List<Label> items = new ArrayList<>();
        for(RoomResponse room : response.getRooms())
        {
            Label label = new Label("", skin, "subtitle");
            label.setName
                    (
                            "[ " +
                            room.getRoomID() + SPLITOR +
                            room.getTitle() + SPLITOR +
                            room.getOwner() + SPLITOR +
                            room.getUsers().size() + " (Amount) " +
                            " ]"
                    );
            items.add(label);
        }

        rooms.setItems(items.toArray(new Label[1]));
    }

    public void initLayout()
    {
        root = new Table(skin);
        root.setFillParent(true);

        group = new VerticalGroup();

        name = new Label("NOT-NAMED", skin);
        level = new Label("Lv. 0", skin);
        exp = new Label("0 / 0", skin);

        rooms = new List<>(skin);
        rooms.getStyle().font.getData().setScale(1.2f);
        roomList = new ScrollPane(rooms, skin);
        roomList.setFadeScrollBars(false);
    }

    public void loadLayout()
    {

        root.add(roomList).width(800).height(500).align(Align.center).padRight(50);
        root.add(group).width(130);

        group.addActor(name);
        group.addActor(level);
        group.addActor(exp);
        group.pad(0, 0, 30, 0);

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

        group.addListener(new ClickListener()
        {
            public void clicked(InputEvent event, float x, float y)
            {
                name.setText("KimDongDong");
                level.setText("Lv.100");
                exp.setText("100 / 300");
            }
        });
    }

    public void setPresenter(RoomMVP.Presenter presenter)
    {
        this.presenter = presenter;
    }
}
