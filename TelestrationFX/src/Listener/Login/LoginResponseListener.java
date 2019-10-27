package Listener.Login;

import Controller.LoginController;
import DTO.Request.Room.RoomListRequest;
import DTO.Request.Users.UserInfoRequest;
import DTO.Response.Account.AccountResponse;
import GameData.Account;
import Network.Client;
import TelestrationFX.ScreenManager;
import com.google.common.eventbus.Subscribe;

public class LoginResponseListener
{
    @Subscribe
    public void handle(AccountResponse response)
    {
        System.out.printf("%3s\n", "ID: " + response.getID());
        System.out.printf("%3s\n", "PW: " + response.getPassword());
        System.out.printf("%3s\n", "RES: " + response.isAccepted());

        if(!response.isAccepted()) return;
        Account.getInstance().setResponse(response);
        ScreenManager.getInstance().activate("WaitRoom");
        Client.getInstance().send(new UserInfoRequest(Account.getInstance().getID()));
        Client.getInstance().send(new RoomListRequest(Account.getInstance().getID(), 10));
    }
}
