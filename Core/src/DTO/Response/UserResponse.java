package DTO.Response;

public class UserResponse implements GamePacketResponse
{
    private String ID;
    private int state;
    private int level;
    private int exp;
    private int max_exp;
    private int RoomID;

    public UserResponse(String ID)
    {
        this(0, 1, 0, 100, 0);
        setID(ID);
    }

    private UserResponse(int state, int level, int exp, int max_exp, int RoomID)
    {
        setState(state);
        setLevel(level);
        setExp(exp);
        setMaxExp(max_exp);
        setRoomID(RoomID);
    }

    public UserResponse(String ID, int state, int level, int exp, int max_exp, int RoomID)
    {
        this(state, level, exp, max_exp, RoomID);
        setID(ID);
    }

    public void setID(String ID)
    {
        this.ID = ID;
    }

    public String getID()
    {
        return ID;
    }

    public void setState(int state)
    {
        this.state = state;
    }

    public int getState()
    {
        return state;
    }

    public void setLevel(int level)
    {
        this.level = level;
    }

    public int getLevel()
    {
        return level;
    }

    public void setExp(int exp)
    {
        this.exp = exp;
    }

    public int getExp()
    {
        return exp;
    }

    public void setMaxExp(int max_exp)
    {
        this.max_exp = max_exp;
    }

    public int getMaxExp()
    {
        return max_exp;
    }

    public void setRoomID(int roomID)
    {
        RoomID = roomID;
    }

    public int getRoomID()
    {
        return RoomID;
    }
}

