package Listener.GameRoom;

import DTO.Request.GameRoom.ExitRoomRequest;
import Game.GameRoom;
import DTO.Response.Room.RoomResponse;
import Database.Manager.GameRoomManager;
import Listener.ServerListener;
import Server.ChannelManager;
import com.google.common.eventbus.Subscribe;
import io.netty.channel.ChannelFuture;

import java.util.Arrays;

public class ExitRoomRequestListener extends ServerListener<ExitRoomRequest>
{
    private GameRoomManager gm = GameRoomManager.getInstance();

    @Subscribe
    public void handle(ExitRoomRequest message)
    {
        String OWNER = message.getOwner();
        String ID = message.getID();
        System.out.println("R1: " + !gm.containsRoom(OWNER));
        System.out.println("R2: " + !gm.containsUser(ID));

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
            room.stop();
            room.switchRound();
            room.clearHistory();
            gm.UpdateRoom();
        }

        System.out.println("목록" + Arrays.asList(room.getUsers().toArray(new String[1])));
        RoomResponse response = new RoomResponse(room);
        for(String name : room.getUsers())
        {
            ChannelFuture future = ChannelManager.getChannels().get(name).writeAndFlush(response);
            future.awaitUninterruptibly();
            System.out.println("isDone? " + future.isDone());
            System.out.println("isSuccess? " + future.isSuccess());

        }
    }
}
