package DTO.Request.GameRoom;

import DTO.Request.GamePacket;
import Util.SketchBook;

public class SendSketchBookRequest extends GamePacket
{
    private static final long serialVersionUID = 3582029039115827201L;
    private String owner;
    private SketchBook sketchBook;

    public SendSketchBookRequest(SketchBook sketchBook, String ID, String owner)
    {
        setID(ID);
        this.owner = owner;
        this.sketchBook = sketchBook;
    }

    public void setOwner(String owner)
    {
        this.owner = owner;
    }

    public String getOwner()
    {
        return owner;
    }

    public SketchBook getSketchBook()
    {
        return sketchBook;
    }
}
