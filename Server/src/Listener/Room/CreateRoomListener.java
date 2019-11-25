package Listener.Room;

import DTO.Request.Room.CreateRoomRequest;
import Database.Manager.GameRoomManager;
import Listener.ServerListener;
import com.google.common.eventbus.Subscribe;

public class CreateRoomListener extends ServerListener<CreateRoomRequest>
{
    @Subscribe
    public void handle(CreateRoomRequest message)
    {
        message.setTimeout(50);
        System.out.println(GameRoomManager.getInstance().getRoomList().size());
        presenter.createRoom(message);
    }
}
