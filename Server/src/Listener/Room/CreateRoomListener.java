package Listener.Room;

import DTO.Request.Room.CreateRoomRequest;
import Database.GameDB;
import Listener.ServerListener;
import MVP.DataPresenter;
import com.google.common.eventbus.Subscribe;

public class CreateRoomListener extends ServerListener<CreateRoomRequest>
{
    @Subscribe
    public void handle(CreateRoomRequest message)
    {
        presenter.createRoom(message);
    }
}
