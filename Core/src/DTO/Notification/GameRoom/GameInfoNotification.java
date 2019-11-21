package DTO.Notification.GameRoom;

import DTO.Request.GamePacket;
import DTO.Response.GamePacketResponse;

public class GameInfoNotification implements GamePacketResponse
{
    private String word;
    private boolean isPainter;

    public GameInfoNotification(String word, boolean isPainter)
    {
        setWord(word);
        setPainter(isPainter);
    }

    public void setWord(String word)
    {
        this.word = word;
    }

    public String getWord()
    {
        return word;
    }

    public void setPainter(boolean painter)
    {
        isPainter = painter;
    }

    public boolean isPainter()
    {
        return isPainter;
    }
}
