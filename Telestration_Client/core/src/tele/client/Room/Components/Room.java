package tele.client.Room.Components;

import DTO.Response.RoomResponse;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.utils.Align;

public class Room extends TextButton implements Comparable<Room>
{
    private RoomResponse response;

    public Room(RoomResponse response, Skin skin)
    {
        super
                (
                        String.format("[%d]          |          %30s          |          Lv.%2d ~          |          %10s",
                        response.getRoomID(),
                        response.getTitle(),
                        response.getLimit(),
                        response.getOwner()),
                        skin
                );

        this.response = response;
        addListener(new ClickedEvent());
        getLabel().setAlignment(Align.topLeft);
        System.out.println();
    }

    public int getID()
    {
        return response.getRoomID();
    }

    public int compareTo(Room o)
    {
        int roomID0 = response.getRoomID();
        int roomID1 = o.response.getRoomID();

        if(roomID0 > roomID1) return 1;
        else return -1;
    }

    private class ClickedEvent extends ClickListener
    {
        public void clicked(InputEvent event, float x, float y)
        {
            System.out.printf("Connect to Room No.%2d..\n", getID());
        }
    }
}
