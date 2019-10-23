package Listener;

import DTO.Request.Room.RoomListRequest;
import DTO.Response.RoomListResponse;
import Database.GameDB;
import MVP.DataPresenter;
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
