package DTO.Request.Room;

import DTO.Request.GamePacket;

public class ExitRoomRequest extends GamePacket
{
    private ExitRoomRequest(String ID)
    {
        setID(ID);
    }
}
