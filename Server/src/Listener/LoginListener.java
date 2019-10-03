package Listener;

import MVP.DataPresenter;
import DTO.Request.Account.LoginRequest;
import Database.DataManager;

public class LoginListener extends ServerListener<LoginRequest>
{
    private DataPresenter presenter = DataManager.getInstance();

    public void handle(LoginRequest message)
    {
//        String ID = message.getID();
//        boolean accepted = false;   // 기본 = 접속 불가
//        if(message.isSubscribable() && !presenter.isRegistered(ID)) presenter.register(message);
//        if(presenter.isRegistered(ID) && !presenter.isOnline(ID))
//        {
//            presenter.setState(ID, true);
//            ChannelManager.getChannels().put(ID, message.getSender());
//            accepted = true;    // 접속 허가
//        }
//
//        LoginResponse response = new LoginResponse(message.getID(), message.getPassword(), accepted);
//        message.getSender().writeAndFlush(response);    // 허가 여부를 응답으로 전송함.
    }

}
