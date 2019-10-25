package tele.client.Room.Listener;

import DTO.Response.RoomResponse;
import com.google.common.eventbus.Subscribe;
import tele.client.Room.Data.RoomInfo;

public class RoomResponseListener
{
    @Subscribe
    public void handle(RoomResponse response)
    {
        RoomInfo room = RoomInfo.getInstance();
        room.setResponse(response);

        System.out.println("Title: " + room.getTitle());
        System.out.println("Owner: " + room.getOwner());
    }
}
