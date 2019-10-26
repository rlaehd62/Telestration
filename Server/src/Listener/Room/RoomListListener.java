package Listener.Room;

import DTO.Request.Room.RoomListRequest;
import DTO.Response.Room.RoomListResponse;
import Listener.ServerListener;
import com.google.common.eventbus.Subscribe;

import java.util.Arrays;

public class RoomListListener extends ServerListener<RoomListRequest>
{
    @Subscribe
    public void handle(RoomListRequest message)
    {
        System.out.println("요청 받았음");
        RoomListResponse response = new RoomListResponse();
        response.add(Arrays.asList(presenter.getRoomList()));
        message.getSender().writeAndFlush(response);
    }
}
