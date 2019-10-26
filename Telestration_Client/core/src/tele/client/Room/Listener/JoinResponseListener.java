package tele.client.Room.Listener;

import DTO.Response.Room.JoinRoomResponse;
import com.badlogic.gdx.Gdx;
import com.google.common.eventbus.Subscribe;
import tele.client.GameRoom.Screen.GameRoomScreen;
import tele.client.Main;
import tele.client.Room.Data.RoomInfo;

public class JoinResponseListener
{
    @Subscribe
    public void handle(JoinRoomResponse response)
    {
        if(!response.isAccepted()) return;

        RoomInfo info = RoomInfo.getInstance();
        if(response.getRoom() != null) info.setRoom(response.getRoom());

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
