package DTO.Notification.GameRoom;

import DTO.Request.GamePacket;
import DTO.Response.GamePacketResponse;
import Util.SketchBook;

public class GameInfoNotification implements GamePacketResponse
{
    private SketchBook sketchBook;
    private String word;
    private boolean isPainter;
    private boolean isOdd;

    public GameInfoNotification(String word, boolean isPainter)
    {
        this(null, word, isPainter);
    }

    public GameInfoNotification(SketchBook sketchBook, String word, boolean isPainter)
    {
        setSketchBook(sketchBook);
        setWord(word);
        setPainter(isPainter);
        isOdd = false;
    }

    public void setSketchBook(SketchBook sketchBook)
    {
        this.sketchBook = sketchBook;
    }

    public SketchBook getSketchBook()
    {
        return sketchBook;
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

    public void setOdd(boolean odd)
    {
        isOdd = odd;
    }

    public boolean isOdd()
    {
        return isOdd;
    }
}
