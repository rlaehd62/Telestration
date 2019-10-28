package DTO.Response.Room;
import DTO.Request.Room.GameRoom;
import DTO.Response.GamePacketResponse;

public class RoomResponse implements GamePacketResponse
{
    private GameRoom room;

    public RoomResponse(GameRoom room)
    {
        this.room = room;
    }

    public GameRoom getRoom()
    {
        return room;
    }
}
