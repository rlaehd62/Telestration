package DTO.Request.GameRoom;

import DTO.Request.GamePacket;

public class ReportRequest extends GamePacket
{
    private String target;
    public ReportRequest(String target)
    {
        super();
        this.target = target;
    }

    public String getTarget()
    {
        return target;
    }
}
