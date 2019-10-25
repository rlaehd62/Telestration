package tele.client.Room.Data;

import DTO.Request.Room.GameRoom;
import DTO.Response.RoomResponse;

public class RoomInfo
{
    private static RoomInfo ins = null;
    private RoomResponse response;
    private GameRoom room;

    private RoomInfo() {}
    public static RoomInfo getInstance()
    {
        return (ins != null) ? ins : (ins = new RoomInfo());
    }

    public void setResponse(RoomResponse response)
    {
        this.response = response;
        this.room = response.getRoom();
    }

    public String getTitle()
    {
        return room.getTitle();
    }

    public String getOwner()
    {
        return response.getRoom().getOwner();
    }

    public boolean isRunning()
    {
        return response.getRoom().isRunning();
    }

    public String[] getUserList()
    {
        return room.getUsers().toArray(new String[1]);
    }
}
