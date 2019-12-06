package DTO.Response.Room;

import Game.GameRoom;
import DTO.Response.GamePacketResponse;

public class RoomResponse implements GamePacketResponse
{
    private static final long serialVersionUID = 7843401822477121315L;
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
