package kid.Listener.WaitRoom;

import kid.Controller.GameRoomController;
import DTO.Response.Room.JoinRoomResponse;
import kid.GameData.RoomInfo;
import kid.TelestrationFX.ScreenManager;
import com.google.common.eventbus.Subscribe;

public class JoinRoomResponseListener
{
    @Subscribe
    public void handle(JoinRoomResponse response)
    {
        if(!response.isAccepted()) return;

        RoomInfo info = RoomInfo.getInstance();
        if(response.getRoom() != null) info.setRoom(response.getRoom());

        ScreenManager sm = ScreenManager.getInstance();
        sm.activate("GameRoom");

        GameRoomController con = GameRoomController.getInstance();
        con.UpdateRoomInfo();
        con.UpdateUserList();
        con.init();
    }
}
