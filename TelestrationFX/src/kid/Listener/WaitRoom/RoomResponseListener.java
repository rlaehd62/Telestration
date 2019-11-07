package kid.Listener.WaitRoom;

import kid.Controller.GameRoomController;
import DTO.Response.Room.RoomResponse;
import kid.GameData.RoomInfo;
import com.google.common.eventbus.Subscribe;

public class RoomResponseListener
{
    @Subscribe
    public void handle(RoomResponse response)
    {
        GameRoomController con = GameRoomController.getInstance();
        if(response.getRoom() != null)
        {
            System.out.println(response.getRoom().getTitle());
            System.out.println(response.getRoom().getOwner());

            RoomInfo info = RoomInfo.getInstance();
            info.setResponse(response);
            con.UpdateUserList();
            con.UpdateRoomInfo();
        }
    }
}
