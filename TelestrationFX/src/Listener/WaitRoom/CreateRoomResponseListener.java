package Listener.WaitRoom;

import Controller.GameRoomController;
import DTO.Request.Room.CreateRoomRequest;
import DTO.Request.Room.RoomListRequest;
import DTO.Response.Room.CreateRoomResponse;
import GameData.Account;
import GameData.RoomInfo;
import Network.Client;
import TelestrationFX.ScreenManager;
import com.google.common.eventbus.Subscribe;

public class CreateRoomResponseListener
{
    @Subscribe
    public void handle(CreateRoomResponse response)
    {
        if(!response.isAccepted()) return;

        RoomInfo info = RoomInfo.getInstance();
        if(response.getResponse() != null) info.setResponse(response.getResponse());

        ScreenManager sm = ScreenManager.getInstance();
        sm.activate("GameRoom");

        GameRoomController con = GameRoomController.getInstance();
        con.UpdateRoomInfo();
        con.UpdateUserList();
    }
}
