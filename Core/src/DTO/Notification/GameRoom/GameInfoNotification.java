package DTO.Notification.GameRoom;

import DTO.Request.GamePacket;
import DTO.Response.GamePacketResponse;
import Util.SketchBook;

public class GameInfoNotification implements GamePacketResponse
{
    private SketchBook sketchBook;
    private String word;
    private boolean isPainter;

    public GameInfoNotification(String word, boolean isPainter)
    {
        setSketchBook(null);
        setPainter(isPainter);
        setWord(word);
    }

    public GameInfoNotification(SketchBook sketchBook, String word, boolean isPainter)
    {
        setPainter(isPainter);
        setSketchBook(sketchBook);
        setWord(word);

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
}
