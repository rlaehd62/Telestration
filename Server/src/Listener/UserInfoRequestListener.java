package Listener;

import DTO.Request.Users.UserInfoRequest;
import DTO.Response.UserResponse;
import Database.GameDB;
import MVP.DataPresenter;
import com.google.common.eventbus.Subscribe;

public class UserInfoRequestListener extends ServerListener<UserInfoRequest>
{
    private DataPresenter presenter = GameDB.getInstance();

    @Subscribe
    public void handle(UserInfoRequest message)
    {
        UserResponse response = presenter.getUser(message.getID());
        if(response != null) message.getSender().writeAndFlush(response);
    }
}
