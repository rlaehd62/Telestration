package DTO.Response;

public class RoomResponse
{
    private String owner;
    private String title;
    private int limit;
    private int timeout;

    public RoomResponse(String owner, String title, int limit, int timeout)
    {
        this.owner = owner;
        this.title = title;
        this.limit = limit;
        this.timeout = timeout;
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
