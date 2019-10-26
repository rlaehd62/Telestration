package Listener.Account;

import DTO.Request.Account.LoginRequest;
import Listener.ServerListener;
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
