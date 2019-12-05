package Game;

import DTO.Response.GamePacketResponse;
import Util.SketchBook;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class SketchBookSet implements GamePacketResponse
{
    private int pointer = 0;
    private int round;
    private List<SketchBook> books;

    public SketchBookSet(int round)
    {
        this.round = round;
        books = new ArrayList<>();
    }

    public void saveData(List<SketchBook> list)
    {
        books.addAll(list);
    }

    public void next()
    {
        pointer = ++pointer % books.size();
    }

    public void previous()
    {
        pointer = (--pointer < 0) ? 0 : pointer;
    }

    public SketchBook current()
    {
        return books.get(pointer);
    }

    public boolean equals(Object obj)
    {
        SketchBookSet set = (SketchBookSet) obj;
        return set.round == round;
    }
}
