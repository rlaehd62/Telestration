package DTO.Notification.GameRoom;

import DTO.Response.GamePacketResponse;

import java.util.HashMap;
import java.util.Map;

public class RewardNotification implements GamePacketResponse
{
    private Map<String, Integer> result;
    public RewardNotification()
    {
        result = new HashMap<>();
    }

    public void addReward(String tag, int exp)
    {
        result.put(tag, exp);
    }

    public void removeReward(String tag)
    {
        result.remove(tag);
    }

    public int getReward(String tag)
    {
        return result.getOrDefault(tag, 0);
    }

    public Map<String, Integer> getResult()
    {
        return result;
    }
}
