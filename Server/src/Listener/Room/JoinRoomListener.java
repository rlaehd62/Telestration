package Listener.Room;

import DTO.Response.User.UserResponse;
import Database.GameDB;
import Game.GameRoom;
import DTO.Request.Room.JoinRoomRequest;
import DTO.Response.Room.JoinRoomResponse;
import DTO.Response.Room.RoomResponse;
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
        GameRoom room = gm.searchRoom(message.getOwner());
        UserResponse user = GameDB.getInstance().getUser(ID);
        System.out.println(ID + "(이)가 " + message.getOwner() + "의 방에 접속 요청!");

        boolean VALID = !gm.containsRoom(ID) || !gm.containsUser(ID);

        if(room != null && VALID && !room.getUsers().contains(ID))
        {
            if(user.getLevel() < room.getLevelLimit()) return;
            System.out.println(ID + "(이)가 접속 요청");
            room.addUser(ID);

            JoinRoomResponse response = new JoinRoomResponse(room, true);
            RoomResponse roomResponse = new RoomResponse(room);

            System.out.println("[ 접속하는 방 정보 ]");
            System.out.println("타이틀: " + room.getTitle());
            System.out.println("　방장: " + room.getOwner());
            System.out.println("　유저: " + room.getUsers());

            message.getSender().writeAndFlush(response);
            ChannelManager.sendBroadCast(room.getUsers().stream().toArray(String[]::new), roomResponse);
        }
    }
}
