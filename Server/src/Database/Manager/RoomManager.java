package Database.Manager;

import DTO.Request.Room.CreateRoomRequest;
import DTO.Response.RoomResponse;
import Database.ServerDB;
import MVP.DataPresenter;

public class RoomManager implements DataPresenter.RoomModel
{
    private ServerDB DB = ServerDB.getInstance();
    private DataPresenter presenter;

    @Override
    public void InsertRoom(CreateRoomRequest request)
    {

    }

    @Override
    public void UpdateRoom(CreateRoomRequest request)
    {

    }

    @Override
    public RoomResponse selectRoom(int RoomID)
    {
        return null;
    }

    @Override
    public boolean hasRoom(int RoomID)
    {
        return false;
    }

    public void setPresenter(DataPresenter presenter)
    {
        this.presenter = presenter;
    }
}
