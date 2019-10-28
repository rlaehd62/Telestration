package Listener.Room;

import DTO.Request.Room.GameRoom;
import DTO.Request.Room.JoinRoomRequest;
import DTO.Response.Room.JoinRoomResponse;
import Database.Manager.GameRoomManager;
import Listener.ServerListener;
import Server.ChannelManager;
import com.google.common.eventbus.Subscribe;

public class JoinRoomListener extends ServerListener<JoinRoomRequest>
{
    private GameRoomManager gm = GameRoomManager.getInstance();

    @Subscribe
    public void handle(JoinRoomRequest message)
    {
        String ID = message.getID();
        GameRoom room = gm.searchRoom(ID);
        boolean VALID = !gm.containsRoom(ID) || !gm.containsUser(ID);

        if(room != null && VALID)
        {
            room.addUser(ID);

            JoinRoomResponse response = new JoinRoomResponse(room, true);
            String[] users = room.getUsers().toArray(new String[1]);
            ChannelManager.sendBroadCast(users, response);
        }
    }
}
