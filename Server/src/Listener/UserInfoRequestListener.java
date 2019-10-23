package Listener;

import DTO.Request.Users.UserInfoRequest;
import DTO.Response.UserResponse;
import com.google.common.eventbus.Subscribe;

public class UserInfoRequestListener extends ServerListener<UserInfoRequest>
{
    @Subscribe
    public void handle(UserInfoRequest message)
    {
        UserResponse response = presenter.getUser(message.getID());
        if(response != null) message.getSender().writeAndFlush(response);
    }
}
