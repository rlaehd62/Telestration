package DTO.Response.GameRoom;

import DTO.Response.GamePacketResponse;
import Util.SketchBook;

public class SketchBookResponse implements GamePacketResponse
{
    private SketchBook sketchBook;

    public SketchBookResponse(SketchBook book)
    {
        this.sketchBook = book;
    }

    public SketchBook getSketchBook()
    {
        return sketchBook;
    }
}
