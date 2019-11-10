package DTO.Request.GameRoom;

import DTO.Request.GamePacket;
import Util.SketchBook;
import com.sun.xml.internal.bind.v2.model.core.ID;

public class SendSketchBookRequest extends GamePacket
{
    private String owner;
    private SketchBook sketchBook;

    public SendSketchBookRequest(SketchBook sketchBook, String ID, String owner)
    {
        setID(ID);
        setOwner(owner);
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
