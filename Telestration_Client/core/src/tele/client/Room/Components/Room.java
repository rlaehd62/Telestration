package tele.client.Room.Components;

import DTO.Response.RoomResponse;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
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
                        String.format("[%d]\t\t\t\t\t\t\t\t%s\t\t\t\t\t\t\t\t(%s)",
                        response.getRoomID(),
                        response.getTitle(),
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

    private class ClickedEvent extends ClickListener
    {
        public void clicked(InputEvent event, float x, float y)
        {
            System.out.printf("Connect to Room No.%2d..\n", getID());
        }
    }
}
