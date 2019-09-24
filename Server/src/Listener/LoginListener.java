package Listener;

import Request.LoginRequest;
import Util.AccountFetcher;
import Util.DataController;
import Util.ServerDB;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class LoginListener extends ServerListener<LoginRequest>
{
    private DataController controller = DataController.getInstance();

    public void handle(LoginRequest message)
    {
        if(message.isSignUp()) controller.signup(message);
        else
        {
            // 로그인 상태로 바꾼다 (오프라인 to 온라인)
        }
    }

}
