package Game;

import DTO.Response.GamePacketResponse;
import Util.SketchBook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SketchBookSet implements GamePacketResponse
{
    private int pointer = 0;
    private List<SketchBook> books;

    public SketchBookSet(int round)
    {
        books = new ArrayList<>();
    }

    public void saveData(List<SketchBook> list)
    {
        books.addAll(list);
    }

    public void next()
    {
        if(++pointer <= 0) return;
        else pointer %= books.size();
    }

    public void previous()
    {
        pointer = Math.max(--pointer, 0);
    }

    public SketchBook current()
    {
        return books.get(pointer);
    }
}
