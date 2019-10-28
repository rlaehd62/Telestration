package Listener.WaitRoom;

import Controller.WaitRoomController;
import DTO.Response.Room.RoomListResponse;
import com.google.common.eventbus.Subscribe;

public class RoomListResponseListener
{
    @Subscribe
    public void handle(RoomListResponse response)
    {
        System.out.println("Something .. Room .. " + response.getRooms().size());
        WaitRoomController con = WaitRoomController.controller;
        con.updateRoomList(response);
    }
}
