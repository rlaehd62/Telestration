package kid.Listener.Login;

import DTO.Request.Room.RoomListRequest;
import DTO.Request.Users.UserInfoRequest;
import DTO.Response.Account.AccountResponse;
import kid.GameData.Account;
import kid.Network.Client;
import kid.TelestrationFX.ScreenManager;
import com.google.common.eventbus.Subscribe;

public class LoginResponseListener
{
    @Subscribe
    public void handle(AccountResponse response)
    {
        if(!response.isAccepted()) return;

        System.out.printf("%3s\n", "ID: " + response.getID());
        System.out.printf("%3s\n", "PW: " + response.getPassword());
        System.out.printf("%3s\n", "RES: " + response.isAccepted());

        Account.getInstance().setResponse(response);
        ScreenManager.getInstance().activate("WaitRoom");
        Client.getInstance().send(new UserInfoRequest(Account.getInstance().getID()));
        Client.getInstance().send(new RoomListRequest(Account.getInstance().getID(), 10));
    }
}
