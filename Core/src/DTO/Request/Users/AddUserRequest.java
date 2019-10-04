package DTO.Request.Users;

import DTO.Request.GamePacket;
import DTO.Response.UserResponse;
import Util.State;

public class AddUserRequest extends GamePacket
{
    private int state;
    private int level;
    private int exp;
    private int max_exp;
    private int RoomID;
    private boolean isModifiable;

    public AddUserRequest(UserResponse response)
    {
        this(response.getState(), response.getLevel(), response.getExp(), response.getMaxExp(), response.getRoomID());
        setID(response.getID());
        setModifiable(false);
    }

    public AddUserRequest(String ID)
    {
        this(State.OFFLINE, 1, 0, 100, State.OUT_OF_ROOM);
        setID(ID);
        setModifiable(false);
    }

    public AddUserRequest(int state, int level, int exp, int max_exp, int RoomID)
    {
        setState(state);
        setLevel(level);
        setExp(exp);
        setMaxExp(max_exp);
        setRoomID(RoomID);
    }

    public AddUserRequest(String ID, int state, int level, int exp, int max_exp, int RoomID)
    {
        this(state, level, exp, max_exp, RoomID);
        setID(ID);
        setModifiable(false);
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

    public void setModifiable(boolean modifiable)
    {
        isModifiable = modifiable;
    }

    public boolean isModifiable()
    {
        return isModifiable;
    }
}
