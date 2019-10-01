package Listener;

import MVP.DataPresenter;
import Request.LoginRequest;
import Database.DataManager;
import Response.LoginResponse;
import Server.ChannelManager;

public class LoginListener extends ServerListener<LoginRequest>
{
    private DataPresenter presenter = DataManager.getInstance();

    public void handle(LoginRequest message)
    {
        String ID = message.getID();
        boolean accepted = false;   // 기본 = 접속 불가
        if(message.isSignUp() && !presenter.isRegistered(ID)) presenter.register(message);
        if(presenter.isRegistered(ID) && !presenter.isOnline(ID))
        {
            presenter.setState(ID, true);
            ChannelManager.getChannels().put(ID, message.getSender());
            accepted = true;    // 접속 허가
        }

        LoginResponse response = new LoginResponse(message.getID(), message.getPW(), accepted);
        message.getSender().writeAndFlush(response);    // 허가 여부를 응답으로 전송함.
    }

}
