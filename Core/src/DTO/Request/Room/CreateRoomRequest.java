package DTO.Request.Room;

import DTO.Request.GamePacket;

public class CreateRoomRequest extends GamePacket
{
    private String title;
    private int limit;
    private int timeout;
    private boolean isModifiable;

    public CreateRoomRequest(String ID, String title)
    {
        setID(ID);
        this.title = title;
        this.limit = 0;
        this.timeout = 300;
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

    public void setModifiable(boolean modifiable)
    {
        isModifiable = modifiable;
    }

    public boolean isModifiable()
    {
        return isModifiable;
    }
}
