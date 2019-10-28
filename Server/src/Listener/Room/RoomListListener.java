package Listener.Room;

import DTO.Request.Room.RoomListRequest;
import DTO.Response.Room.RoomListResponse;
import DTO.Response.Room.RoomResponse;
import Listener.ServerListener;
import com.google.common.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.Arrays;

public class RoomListListener extends ServerListener<RoomListRequest>
{
    @Subscribe
    public void handle(RoomListRequest message)
    {
        System.out.println("룸 생성 요청 받았음");
        RoomListResponse response = new RoomListResponse();
        RoomResponse[] list = presenter.getRoomList();
        if(list != null)
        {
            response.add(new ArrayList<>(Arrays.asList(list)));
            message.getSender().writeAndFlush(response);
        }
    }
}
