package DTO.Response.Room;

import Game.GameRoom;
import DTO.Response.GamePacketResponse;

public class JoinRoomResponse implements GamePacketResponse
{
    private GameRoom room;
    private boolean accepted;

    public JoinRoomResponse(GameRoom room, boolean accepted)
    {
        this.room = room;
        this.accepted = accepted;
    }

    public GameRoom getRoom()
    {
        return room;
    }

    public boolean isAccepted()
    {
        return accepted;
    }
}
