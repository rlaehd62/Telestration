package DTO.Request.GameRoom;

import DTO.Request.GamePacket;

public class CurrentTimeNotification extends GamePacket
{
    private int minute;
    private int second;

    public CurrentTimeNotification(int minute, int second)
    {
        this.minute = minute;
        this.second = second;
    }

    public String toString()
    {
        return String.format("%d : %d", minute, second);
    }
}
