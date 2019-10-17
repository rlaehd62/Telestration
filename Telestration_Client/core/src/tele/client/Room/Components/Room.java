package tele.client.Room.Components;

import DTO.Response.RoomResponse;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.HorizontalGroup;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class Room extends Button
{
    private RoomResponse response;

    public Room(RoomResponse response)
    {
        this.response = response;
        setName(String.format("[%d] %s (%s)", response.getRoomID(), response.getTitle(), response.getOwner()));
    }

    public int getID()
    {
        return response.getRoomID();
    }
}
