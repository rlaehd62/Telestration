package Listener.Account;

import DTO.Request.Account.LoginRequest;
import Database.Manager.ReportManager;
import Listener.ServerListener;
import com.google.common.eventbus.Subscribe;

public class LoginListener extends ServerListener<LoginRequest>
{
    @Subscribe
    public void handle(LoginRequest message)
    {
        ReportManager rm = new ReportManager();
        String IP = message.getSender().channel().remoteAddress().toString();
        int count = rm.getCnt(IP);

        if(message.isSubscribable()) presenter.register(message);
        if((count / 4) < 3) presenter.login(message);
    }
}
