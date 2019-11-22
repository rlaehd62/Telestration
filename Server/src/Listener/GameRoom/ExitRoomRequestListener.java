package Listener.GameRoom;

import DTO.Request.GameRoom.ExitRoomRequest;
import Game.GameRoom;
import DTO.Response.Room.RoomResponse;
import Database.Manager.GameRoomManager;
import Listener.ServerListener;
import Server.ChannelManager;
import com.google.common.eventbus.Subscribe;

public class ExitRoomRequestListener extends ServerListener<ExitRoomRequest>
{
    private GameRoomManager gm = GameRoomManager.getInstance();

    @Subscribe
    public void handle(ExitRoomRequest message)
    {
        String OWNER = message.getOwner();
        String ID = message.getID();
        if(!gm.containsRoom(OWNER) || !gm.containsUser(ID)) return;

        GameRoom room = gm.searchRoom(OWNER);
        room.removeUser(ID);
        System.out.println(room.getUsers());

        if(room.isEmpty())
        {
            room.stop();
            gm.RemoveRoom(OWNER);
            return;
        }
        else if(ID.equals(OWNER))
        {
            room.setOwner(room.getUsers().get(0));
            gm.UpdateRoom();
        }

        RoomResponse response = new RoomResponse(room);
        ChannelManager.sendBroadCast(room.getUsers().stream().toArray(String[]::new), response);
    }
}
