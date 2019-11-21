package DTO.Request.GameRoom;

import DTO.Request.GamePacket;

public class GameStartRequest extends GamePacket
{
    private String owner;
    public GameStartRequest(String owner)
    {
        this.owner = owner;
    }

    public String getOwner()
    {
        return owner;
    }
}
