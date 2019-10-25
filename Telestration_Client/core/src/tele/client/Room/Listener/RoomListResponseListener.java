package tele.client.Room.Listener;

import DTO.Response.RoomListResponse;
import com.badlogic.gdx.Gdx;
import com.google.common.eventbus.Subscribe;
import tele.client.Room.Interface.RoomMVP;
import tele.client.Room.RoomPresenter;
import tele.client.Room.Screen.RoomScreen;

public class RoomListResponseListener
{
    @Subscribe
    public void handle(RoomListResponse response)
    {
        System.out.println("Something .. " + response.getRooms().size());
        RoomPresenter.getInstance().updateRoomList(response);
    }
}
