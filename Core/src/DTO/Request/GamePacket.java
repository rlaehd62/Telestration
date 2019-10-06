package DTO.Request;

import io.netty.channel.ChannelHandlerContext;

import java.io.Serializable;

public class GamePacket implements Serializable
{
    private static final long serialVersionUID = 6136204657514831377L;

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

    public ChannelHandlerContext getSender()
    {
        return sender;
    }
}
