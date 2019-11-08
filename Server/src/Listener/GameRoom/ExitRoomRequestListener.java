package Listener.GameRoom;

import DTO.Request.GameRoom.ExitRoomRequest;
import DTO.Request.Room.GameRoom;
import DTO.Response.Room.RoomResponse;
import Database.Manager.GameRoomManager;
import Listener.ServerListener;
import Server.ChannelManager;
import com.google.common.eventbus.Subscribe;

import java.util.Arrays;
import java.util.List;

public class ExitRoomRequestListener extends ServerListener<ExitRoomRequest>
{
    private GameRoomManager gm = GameRoomManager.getInstance();

    @Subscribe
    public void handle(ExitRoomRequest message)
    {
        String OWNER = message.getOwner();
        String ID = message.getID();

        GameRoom room = gm.searchRoom(OWNER);
        if(!gm.containsRoom(OWNER) || !gm.containsUser(ID)) return;

        List<String> users = room.getUsers();
        users.remove(ID);

        RoomResponse response = new RoomResponse(room);
        ChannelManager.sendBroadCast(Arrays.stream(users.toArray()).toArray(String[]::new), response);
    }
}
