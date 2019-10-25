package tele.client.Room.Components;

import DTO.Request.Room.GameRoom;
import DTO.Response.RoomResponse;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class Room extends TextButton
{
    private RoomResponse response;

    public Room(RoomResponse response, Skin skin)
    {
        super
                (
                        String.format("[%s]          |          %30s          |          Lv.%2d ~ ",
                        response.getRoom().getOwner(),
                        response.getRoom().getTitle(),
                        response.getRoom().getLevelLimit()),
                        skin
                );

        this.response = response;
        addListener(new ClickedEvent());
        getLabel().setAlignment(Align.topLeft);
        System.out.println();
    }

    public String getID()
    {
        GameRoom room = response.getRoom();
        return room.getOwner();
    }

    private class ClickedEvent extends ClickListener
    {
        public void clicked(InputEvent event, float x, float y)
        {
            System.out.printf("Connect to %s's Room\n", getID());
        }
    }
}
