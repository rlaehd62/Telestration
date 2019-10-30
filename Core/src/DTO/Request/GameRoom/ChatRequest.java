package DTO.Request.GameRoom;

import DTO.Request.GamePacket;

public class ChatRequest extends GamePacket
{
    private String owner, text;

    public ChatRequest(String ID, String owner, String text)
    {
        setID(ID);
        this.owner = owner;
        this.text = text;
    }

    public String getOwner()
    {
        return owner;
    }

    public String getText()
    {
        return text;
    }
}
