package Listener.GameRoom;

import DTO.Request.GameRoom.ChatRequest;
import DTO.Request.Room.GameRoom;
import DTO.Response.GameRoom.ChatResponse;
import Database.Manager.GameRoomManager;
import Listener.ServerListener;
import Server.ChannelManager;
import com.google.common.eventbus.Subscribe;

public class ChatRequestListener extends ServerListener<ChatRequest>
{
    private GameRoomManager gm = GameRoomManager.getInstance();

    @Subscribe
    public void handle(ChatRequest message)
    {
        String ID = message.getID();
        GameRoom room = gm.searchRoom(message.getOwner());
        System.out.println(ID + "(이)가 " + room.getOwner() + "의 방에 메세지를 보내고 싶음 ..");

        boolean VALID = gm.containsRoom(ID) || gm.containsUser(ID);

        if(room != null && VALID && room.getUsers().contains(ID))
        {
            System.out.println(ID + ": " + message.getText());
            ChatResponse response = new ChatResponse(message);
            System.out.println("[ChatResponse] " + response.toString());

            message.getSender().writeAndFlush(response);
            for(String name : room.getUsers())
            {
                if(!name.equals(ID))
                    ChannelManager.getChannels().get(name).writeAndFlush(response);
            }
        }
    }
}
