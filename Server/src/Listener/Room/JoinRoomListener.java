package Listener.Room;

import DTO.Request.Room.GameRoom;
import DTO.Request.Room.JoinRoomRequest;
import DTO.Response.Room.JoinRoomResponse;
import DTO.Response.Room.RoomResponse;
import Database.Manager.GameRoomManager;
import Listener.ServerListener;
import Server.ChannelManager;
import com.google.common.eventbus.Subscribe;
import io.netty.channel.ChannelFuture;

public class JoinRoomListener extends ServerListener<JoinRoomRequest>
{
    private GameRoomManager gm = GameRoomManager.getInstance();

    @Subscribe
    public void handle(JoinRoomRequest message)
    {
        String ID = message.getID();
        GameRoom room = gm.searchRoom(message.getOwner());
        System.out.println(ID + "(이)가 " + message.getOwner() + "의 방에 접속 요청!");

        boolean VALID = !gm.containsRoom(ID) || !gm.containsUser(ID);

        if(room != null && VALID)
        {
            System.out.println(ID + "(이)가 접속 요청");
            room.addUser(ID);

            JoinRoomResponse response = new JoinRoomResponse(room, true);
            RoomResponse roomResponse = new RoomResponse(room);

            System.out.println("[ 접속하는 방 정보 ]");
            System.out.println("타이틀: " + room.getTitle());
            System.out.println("　방장: " + room.getOwner());
            System.out.println("　유저: " + room.getUsers());

            message.getSender().writeAndFlush(response);
            for(String name : room.getUsers())
            {
                if(!name.equals(ID))
                    ChannelManager.getChannels().get(name).writeAndFlush(roomResponse);
            }
        }
    }
}
