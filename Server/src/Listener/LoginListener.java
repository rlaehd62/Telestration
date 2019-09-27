package Listener;

import MVP.DataPresenter;
import Request.LoginRequest;
import Database.DataManager;

public class LoginListener extends ServerListener<LoginRequest>
{
    private DataPresenter presenter = DataManager.getInstance();

    public void handle(LoginRequest message)
    {
        String ID = message.getID();
        if(message.isSignUp() && !presenter.isRegistered(ID)) presenter.register(message);
        if(presenter.isRegistered(ID) && presenter.isOnline(ID))
        {
            presenter.setOnline(ID);
            // 커넥션 풀에 등록한다.
        }
    }

}
