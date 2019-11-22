package DTO.Response.Room;
import Game.GameRoom;
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
