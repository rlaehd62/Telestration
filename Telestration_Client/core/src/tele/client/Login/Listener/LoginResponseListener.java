package tele.client.Login.Listener;

import DTO.Response.Account.AccountResponse;
import com.badlogic.gdx.Gdx;
import com.google.common.eventbus.Subscribe;
import tele.client.Login.Data.Account;
import tele.client.Main;
import tele.client.Room.Screen.RoomScreen;

public class LoginResponseListener
{
    @Subscribe
    public void handle(AccountResponse response)
    {
        if(response.isAccepted())
        {
            Main main = ((Main) Gdx.app.getApplicationListener());
            Gdx.app.postRunnable(() ->
                    {
                        main.dispose();
                        main.setScreen(new RoomScreen());
                    });
        }

        System.out.printf("%3s\n", "ID: " + response.getID());
        System.out.printf("%3s\n", "ID: " + response.getPassword());
        System.out.printf("%3s\n", "RES: " + response.isAccepted());
        Account.getInstance().setResponse(response);
    }
}
