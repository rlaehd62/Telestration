package kid.Listener.WaitRoom;

import kid.Controller.GameRoomController;
import DTO.Response.Room.CreateRoomResponse;
import kid.Controller.TestController;
import kid.GameData.RoomInfo;
import kid.TelestrationFX.ScreenManager;
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
        sm.activate("Test");
//        sm.activate("GameRoom");

//        GameRoomController con = GameRoomController.getInstance();
//        con.UpdateRoomInfo();
//        con.UpdateUserList();
//        con.init();

        TestController con = TestController.getController();
        con.UpdateRoomInfo();
        con.UpdateUserList();
        con.init();
    }
}
