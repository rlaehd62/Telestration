package Listener.GameRoom;

import DTO.Request.GameRoom.SendSketchBookRequest;
import DTO.Request.Room.GameRoom;
import DTO.Response.GameRoom.SketchBookResponse;
import Database.Manager.GameRoomManager;
import Listener.ServerListener;
import Server.ChannelManager;
import Util.SketchBook;
import com.google.common.eventbus.Subscribe;

import java.util.List;

public class SendSketchBookListener extends ServerListener<SendSketchBookRequest>
{
    private GameRoomManager gm = GameRoomManager.getInstance();

    @Subscribe
    public void handle(SendSketchBookRequest message)
    {
        final String ID = message.getID();
        final String OWNER = message.getOwner();
        if(!gm.containsRoom(OWNER) || !gm.containsUser(ID)) return;

        GameRoom room = gm.searchRoom(OWNER);
        SketchBook book = message.getSketchBook();

        List<String> users = room.getUsers();
        int index = users.indexOf(ID);
        String NEXT = users.get((index + 1 % users.size()));

        if(index < 0 && NEXT.equals("")) return;

        SketchBookResponse response = new SketchBookResponse(book);
        ChannelManager.sendBroadCast(new String[] { NEXT }, response);
    }
}
