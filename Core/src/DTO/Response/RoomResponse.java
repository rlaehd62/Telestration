package DTO.Response;
import DTO.Request.Room.GameRoom;

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
