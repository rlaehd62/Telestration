package DTO.Notification.GameRoom;

import DTO.Response.GamePacketResponse;
import Game.GameRoom;
import Game.History;
import Game.RoundSet;
import Game.SketchBookSet;
import Util.SketchBook;

import java.util.*;

public class RewardNotification implements GamePacketResponse
{
    private Map<String, Integer> users;
    private Map<String, Integer> words;
    private RoundSet roundSet;

    public RewardNotification()
    {
        users = new HashMap<>();
        words = new HashMap<>();
        roundSet = new RoundSet();
    }

    public void setUser(String name, int exp)
    {
        users.put(name, exp);
    }

    public int getUser(String name)
    {
        return users.getOrDefault(name, 0);
    }

    public Map<String, Integer> getUsers()
    {
        return users;
    }

    public void setWord(String name, int exp)
    {
        words.put(name, exp);
    }

    public int getWord(String name)
    {
        return words.getOrDefault(name, 0);
    }

    public Map<String, Integer> getWords()
    {
        return words;
    }

    public void setSketchBooks(int roundNumber, List<SketchBook> list)
    {
        SketchBookSet set = new SketchBookSet(roundNumber);
        set.saveData(list);
        roundSet.saveData(set);
    }

    public RoundSet getRoundSet()
    {
        return roundSet;
    }
}
