package Game;

import DTO.Response.GamePacketResponse;
import Util.SketchBook;

import java.util.ArrayList;
import java.util.List;

public class RoundSet implements GamePacketResponse
{
    private int pointer = 0;
    private List<SketchBookSet> sets;

    public RoundSet()
    {
        sets = new ArrayList<>();
    }

    public void saveData(SketchBookSet set)
    {
        sets.add(set);
    }

    public void next()
    {
        if(++pointer <= 0) return;
        else pointer %= sets.size();
    }

    public SketchBookSet current()
    {
        return sets.get(pointer);
    }

    public void previous()
    {
        pointer = Math.max(--pointer, 0);
    }
}
