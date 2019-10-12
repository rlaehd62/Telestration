package DTO.Response;

import java.util.ArrayList;
import java.util.List;

public class RoomResponse implements GamePacketResponse
{
    private int RoomID;
    private String owner;
    private String title;
    private int state;
    private int limit;
    private int timeout;
    private List<String> users;

    public RoomResponse(String owner, String title, int state, int limit, int timeout)
    {
        this.owner = owner;
        this.title = title;
        this.state = state;
        this.limit = limit;
        this.timeout = timeout;
        this.users = new ArrayList<>();
    }

    public void addUser(String name)
    {
        users.add(name);
    }

    public void removeUsers(String name)
    {
        users.remove(name);
    }

    public List<String> getUsers()
    {
        return users;
    }

    public void setRoomID(int roomID)
    {
        RoomID = roomID;
    }

    public int getRoomID()
    {
        return RoomID;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    public int getState()
    {
        return state;
    }

    public void setOwner(String owner)
    {
        this.owner = owner;
    }

    public String getOwner()
    {
        return owner;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }

    public void setLimit(int limit)
    {
        this.limit = limit;
    }

    public int getLimit()
    {
        return limit;
    }

    public void setTimeout(int timeout)
    {
        this.timeout = timeout;
    }

    public int getTimeout()
    {
        return timeout;
    }
}
