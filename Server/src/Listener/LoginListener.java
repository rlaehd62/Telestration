package Listener;

import DTO.Request.Users.AddUserRequest;
import DTO.Response.AccountResponse;
import DTO.Response.UserResponse;
import MVP.DataPresenter;
import DTO.Request.Account.LoginRequest;
import Database.DataManager;
import Server.ChannelManager;
import Util.State;

public class LoginListener extends ServerListener<LoginRequest>
{
    private DataPresenter presenter = DataManager.getInstance();

    public void handle(LoginRequest message)
    {
        String ID = message.getID();

        // 가입 요청 또는 계정이 존재하지 않는 경우 추가한다.
        if(message.isSubscribable() || !presenter.hasAccount(ID)) presenter.InsertAccount(message);

        // 계정의 상태를 접속 (Online)으로 바꿔준다.
        AddUserRequest request = new AddUserRequest(presenter.getUser(ID));
        request.setState(State.ONLINE);
        presenter.UpdateUser(request);

        // 클라이언트에 대한 연결을 저장한다.
        ChannelManager.getChannels().put(ID, request.getSender());

        // 로그인 허용 여부을 클라이언트에게 응답한다.
        AccountResponse response = new AccountResponse(message, true);
        request.getSender().writeAndFlush(response);
    }

}
