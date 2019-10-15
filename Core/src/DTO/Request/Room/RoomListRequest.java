package DTO.Request.Room;

import DTO.Request.GamePacket;

public class RoomListRequest extends GamePacket
{
    private int amount;

    public RoomListRequest(String ID, int amount)
    {
        this.amount = amount;
    }

    public void setAmount(int amount)
    {
        this.amount = amount;
    }

    public int getAmount()
    {
        return amount;
    }
}
