package tele.client.Login.Listener;

import DTO.Response.AccountResponse;
import com.google.common.eventbus.Subscribe;

public class LoginResponseListener
{
    @Subscribe
    public void handle(AccountResponse response)
    {
        System.out.printf("%3s\n", "ID: " + response.getID());
        System.out.printf("%3s\n", "ID: " + response.getPassword());
        System.out.printf("%3s\n", "RES: " + response.isAccepted());
        // Change to Another Screen if accepted
    }
}
