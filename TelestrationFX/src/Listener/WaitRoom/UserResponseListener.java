package Listener.WaitRoom;

import Controller.WaitRoomController;
import DTO.Response.User.UserResponse;
import GameData.User;
import TelestrationFX.ScreenManager;
import com.google.common.eventbus.Subscribe;
import javafx.scene.control.Label;

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
