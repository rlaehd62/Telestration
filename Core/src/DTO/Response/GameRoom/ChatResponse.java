package DTO.Response.GameRoom;

import DTO.Request.GameRoom.ChatRequest;
import DTO.Response.GamePacketResponse;

public class ChatResponse implements GamePacketResponse
{
    private String source;
    private String text;

    public ChatResponse(ChatRequest chat)
    {
        this.source = chat.getID();
        this.text = chat.getText();
    }

    public String toString()
    {
        return source + ": " + text;
    }
}
