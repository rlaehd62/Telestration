package DTO.Request.GameRoom;

import DTO.Request.GamePacket;

public class StartTimerRequest extends GamePacket
{
    private int M;
    private int S;

    public StartTimerRequest(String ID, int M, int S)
    {
        setID(ID);
        this.M = M;
        this.S = S;
    }

    public int getM()
    {
        return M;
    }

    public int getS()
    {
        return S;
    }
}
