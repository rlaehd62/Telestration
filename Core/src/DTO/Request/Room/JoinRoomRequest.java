package DTO.Request.Room;

import DTO.Request.GamePacket;

public class JoinRoomRequest extends GamePacket
{
    private String owner;

    public JoinRoomRequest(String ID)
    {
        setID(ID);
    }
}
