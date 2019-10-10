package tele.client.Login;

import DTO.Request.GamePacket;
import tele.client.Login.Interface.LoginMVP;
import tele.client.Network.Client;
import tele.client.Network.GameClient;

public class LoginModel implements LoginMVP.Model
{
    private GameClient client;
    private LoginMVP.Presenter presenter;

    public LoginModel()
    {
        client = Client.getInstance();
    }

    public void init()
    {
        if(!client.isRunning())
            client.startServer();
    }

    public void send(GamePacket packet)
    {
        GameClient client = Client.getInstance();
        if(client.isRunning()) client.send(packet);
        presenter.showLogin();
    }

    public void setPresenter(LoginMVP.Presenter presenter)
    {
        this.presenter = presenter;
    }
}
