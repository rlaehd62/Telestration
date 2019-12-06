package kid.GameData;

import DTO.Response.Room.RoomResponse;
import Game.GameRoom;

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

    public synchronized void setResponse(RoomResponse response)
    {
        this.response = response;
        this.room = response.getRoom();
        System.out.println("Owner: " + room.getOwner());
        System.out.println("Title: " + room.getTitle());
        System.out.println("Users: " + room.getUsers());
    }

    public synchronized void setRoom(GameRoom room)
    {
        this.room = room;
    }

    public synchronized String getTitle()
    {
        return room.getTitle();
    }

    public synchronized String getOwner()
    {
        return room.getOwner();
    }

    public boolean isRunning()
    {
        return room.isRunning();
    }

    public synchronized String[] getUserList()
    {
        return room.getUsers().toArray(new String[1]);
    }
}
