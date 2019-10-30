package DTO.Request.Room;

import DTO.Request.GamePacket;

public class JoinRoomRequest extends GamePacket
{
    private String owner;

    public JoinRoomRequest(String ID)
    {
        setID(ID);
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
