package kid.Listener.WaitRoom;

import kid.Controller.WaitRoomController;
import DTO.Response.User.UserResponse;
import kid.GameData.User;
import com.google.common.eventbus.Subscribe;

public class UserResponseListener
{
    @Subscribe
    public void handle(UserResponse response)
    {
        User.getInstance();
        User.getInstance().setResponse(response);

        System.out.println("ID: " + response.getID());
        System.out.println("Lv. " + response.getLevel());
        System.out.println("EXP (" + response.getExp() + " / " + response.getMaxExp() + ")");

        WaitRoomController con = WaitRoomController.getInstance();
        con.updateUserInfo();
    }
}
