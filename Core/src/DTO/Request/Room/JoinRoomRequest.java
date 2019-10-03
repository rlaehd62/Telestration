package DTO.Request.Room;

import DTO.Request.GamePacket;

public class JoinRoomRequest extends GamePacket
{
    private int RoomID;

    public JoinRoomRequest(String ID, int RoomID)
    {
        setID(ID);
        this.RoomID = RoomID;
    }

    public void setRoomID(int roomID)
    {
        RoomID = roomID;
    }

    public int getRoomID()
    {
        return RoomID;
    }
}
