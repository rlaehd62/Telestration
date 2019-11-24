package kid.Listener.WaitRoom;

import DTO.Response.Room.RoomResponse;
import kid.Controller.TestController;
import kid.GameData.RoomInfo;
import com.google.common.eventbus.Subscribe;

public class RoomResponseListener
{
    @Subscribe
    public void handle(RoomResponse response)
    {
        System.out.println("Room 정보 왔음?");
        TestController con = TestController.getController();
        if(response.getRoom() != null)
        {
            System.out.println("받긴 받음?");
            System.out.println(response.getRoom().getTitle());
            System.out.println(response.getRoom().getOwner());

            RoomInfo info = RoomInfo.getInstance();
            info.setResponse(response);
            con.UpdateUserList();
            con.UpdateRoomInfo();
        }
    }
}
