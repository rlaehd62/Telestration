package Listener;

import DTO.Request.Users.AddUserRequest;
import DTO.Response.AccountResponse;
import DTO.Response.UserResponse;
import MVP.DataPresenter;
import DTO.Request.Account.LoginRequest;
import Database.DataManager;
import Server.ChannelManager;
import Util.State;
import io.netty.channel.ChannelFuture;

public class LoginListener extends ServerListener<LoginRequest>
{
    private DataPresenter presenter = DataManager.getInstance();

    public void handle(LoginRequest message)
    {
        String ID = message.getID();
        String PW = message.getPassword();

        // 가입 요청 또는 계정이 존재하지 않는 경우 추가한다.
        if(message.isSubscribable() || !presenter.hasAccount(ID)) presenter.InsertAccount(message);

        // 비밀번호의 일치에 따라서 접속을 허가
        AccountResponse response = presenter.getAccount(ID);
        response.setAccepted((response.getPassword().equals(PW)));

        // 접속이 허가되면 상태를 온라인으로 바꾼다.
        AddUserRequest request = new AddUserRequest(presenter.getUser(ID));
        request.setState(response.isAccepted() ? State.ONLINE : State.OFFLINE);
        request.setSender(message.getSender());

        presenter.UpdateUser(request);

        // 클라이언트에 대한 연결을 저장한다.
        if(response.isAccepted()) ChannelManager.getChannels().put(ID, request.getSender());
        request.getSender().writeAndFlush(response);
    }

}
