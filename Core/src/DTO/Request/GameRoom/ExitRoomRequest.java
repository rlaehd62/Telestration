package DTO.Request.GameRoom;

import DTO.Request.GamePacket;

public class ExitRoomRequest extends GamePacket
{
    private String owner;
    public ExitRoomRequest(String ID, String owner)
    {
        setID(ID);
        setOwner(owner);
    }

    public void setOwner(String owner)
    {
        this.owner = owner;
    }

    public String getOwner()
    {
        return owner;
    }
}
