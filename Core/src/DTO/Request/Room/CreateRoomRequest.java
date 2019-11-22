package DTO.Request.Room;

import DTO.Request.GamePacket;
import DTO.Response.Room.RoomResponse;
import Game.GameRoom;
import Util.State;

public class CreateRoomRequest extends GamePacket
{
    private String title;
    private int limit;
    private int timeout;
    private int state;
    private boolean isModifiable;

    public CreateRoomRequest(RoomResponse response)
    {
        GameRoom room = response.getRoom();
        setID(room.getOwner());
        this.title = room.getTitle();
        this.limit = room.getLevelLimit();
        this.timeout = room.getTimeOut();
        this.state = (room.isRunning()) ? State.STARTED : State.READY;
    }

    public CreateRoomRequest(String ID, String title)
    {
        setID(ID);
        this.title = title;
        this.limit = 0;
        this.timeout = 300;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    public int getState()
    {
        return state;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }

    public void setLimit(int limit)
    {
        this.limit = limit;
    }

    public int getLimit()
    {
        return limit;
    }

    public void setTimeout(int timeout)
    {
        this.timeout = timeout;
    }

    public int getTimeout()
    {
        return timeout;
    }

    public void setModifiable(boolean modifiable)
    {
        isModifiable = modifiable;
    }

    public boolean isModifiable()
    {
        return isModifiable;
    }
}
