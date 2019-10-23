package tele.client.Room.Data;

import DTO.Response.RoomResponse;

public class RoomInfo
{
    private static RoomInfo ins = null;
    private RoomResponse response;

    private RoomInfo() {}
    public static RoomInfo getInstance()
    {
        return (ins != null) ? ins : (ins = new RoomInfo());
    }

    public void setResponse(RoomResponse response)
    {
        this.response = response;
    }

    public int getRoomID()
    {
        return response.getRoomID();
    }

    public String getTitle()
    {
        return response.getTitle();
    }

    public String getOwner()
    {
        return response.getOwner();
    }

    public int getState()
    {
        return response.getState();
    }

    public String[] getUserList()
    {
        return response.getUsers().toArray(new String[1]);
    }
}
