package DTO.Notification.GameRoom;

import DTO.Request.GamePacket;

public class CurrentTimeNotification extends GamePacket
{
    private int minute;
    private int second;

    private int MAX_MINUTE;
    private int MAX_SECOND;

    public CurrentTimeNotification(int minute, int second)
    {
        this.minute = minute;
        this.second = second;
    }

    public void setMax(int M, int S)
    {
        this.MAX_MINUTE = M;
        this.MAX_SECOND = S;
    }

    public int getCurrentSeconds()
    {
        return minute * 60 + second;
    }

    public int getMaxSeconds()
    {
        return MAX_MINUTE * 60 + MAX_SECOND;
    }

    public String toString()
    {
        return String.format("%d : %d", minute, second);
    }
}
