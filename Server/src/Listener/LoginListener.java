package Listener;

import DTO.Request.Users.AddUserRequest;
import DTO.Response.AccountResponse;
import MVP.DataPresenter;
import DTO.Request.Account.LoginRequest;
import Database.GameDB;
import Server.ChannelManager;
import Util.State;
import com.google.common.eventbus.Subscribe;

public class LoginListener extends ServerListener<LoginRequest>
{
    @Subscribe
    public void handle(LoginRequest message)
    {
        if(message.isSubscribable()) presenter.register(message);
        presenter.login(message);
    }
}
