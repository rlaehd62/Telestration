package Request;

import io.netty.channel.ChannelHandlerContext;

public class GamePacket
{
    private String ID;
    private ChannelHandlerContext sender;

    public void setID(String ID)
    {
        this.ID = ID;
    }

    public void setSender(ChannelHandlerContext sender)
    {
        this.sender = sender;
    }

    public String getID()
    {
        return ID;
    }
}
