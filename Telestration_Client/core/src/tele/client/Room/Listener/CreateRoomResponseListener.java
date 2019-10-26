package tele.client.Room.Listener;

import DTO.Response.Room.CreateRoomResponse;
import com.badlogic.gdx.Gdx;
import com.google.common.eventbus.Subscribe;
import tele.client.GameRoom.Screen.GameRoomScreen;
import tele.client.Main;
import tele.client.Room.Data.RoomInfo;

public class CreateRoomResponseListener
{
    @Subscribe
    public void handle(CreateRoomResponse response)
    {
        if(!response.isAccepted()) return;

        RoomInfo info = RoomInfo.getInstance();
        if(response.getResponse() != null) info.setResponse(response.getResponse());

        Gdx.app.postRunnable(() ->
        {
            Main main = ((Main) Gdx.app.getApplicationListener());
            Gdx.app.postRunnable(() ->
            {
                main.dispose();
                main.setScreen(new GameRoomScreen());
            });
        });
    }
}
