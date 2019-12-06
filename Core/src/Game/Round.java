package Game;

import DTO.Response.GameRoom.SketchBookResponse;
import Util.SketchBook;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class Round
{
    private int number;
    private int cur_seconds;
    private int max_seconds;

    private GameRoom room;
    private HashMap<String, SketchBook> result;

    public Round(int number, int max_seconds)
    {
        this.number = number;
        this.cur_seconds = 0;
        this.max_seconds = max_seconds;
        this.result = new HashMap<>();
    }

    public void setRoom(GameRoom room)
    {
        this.room = room;
    }

    public int getRoundNumber()
    {
        return number;
    }

    public void reset()
    {
        cur_seconds = 0;
    }

    public void increaseTime()
    {
        cur_seconds++;
    }

    public boolean isExpired()
    {
        return cur_seconds >= max_seconds;
    }

    public boolean isDone()
    {
        System.out.println(Objects.isNull(room));
        System.out.println(Objects.isNull(getResult()));
        return (room.getUsers().size() <= getResult().size());
    }

    public int getCurrentSeconds()
    {
        return cur_seconds;
    }

    public int getMaxSeconds()
    {
        return max_seconds;
    }

    public void setResult(String name, SketchBook sketchBook)
    {
        result.put(name, sketchBook);
    }

    public HashMap<String, SketchBook> getResult()
    {
        return result;
    }

    public String toString()
    {
        return String.format("[%d 라운드] %d분 %d초", getRoundNumber(), cur_seconds / 60, cur_seconds % 60);
    }
}
