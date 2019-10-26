package tele.client.Room.Listener;

import DTO.Response.User.UserResponse;
import com.google.common.eventbus.Subscribe;
import tele.client.Room.Data.User;
import tele.client.Room.RoomPresenter;

public class UserResponseListener
{
    @Subscribe
    public void handle(UserResponse response)
    {
        User.getInstance();
        User.getInstance().setResponse(response);
        RoomPresenter.getInstance().updateUserInfo(response);
    }
}
