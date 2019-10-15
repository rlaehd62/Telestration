package tele.client.Room;

import DTO.Request.Room.RoomListRequest;
import DTO.Response.RoomListResponse;
import tele.client.Room.Interface.RoomMVP;

public class RoomPresenter implements RoomMVP.Presenter
{
    private static RoomPresenter ins = null;
    private RoomView view;
    private RoomModel model;

    private RoomPresenter()
    {
        model = new RoomModel();
        model.setPresenter(this);

        view = new RoomView();
        view.setPresenter(this);
    }

    public static RoomPresenter getInstance()
    {
        return (ins == null) ? (ins = new RoomPresenter()) : ins;
    }

    public void updateRoomList()
    {
        RoomListRequest request = new RoomListRequest("", 10);
        model.send(request);
    }

    public void updateRoomList(RoomListResponse response)
    {
        view.setRoomList(response);
    }

    public void showCreationUI()
    {

    }

    public void updateActors()
    {
        view.updateActors();
        view.drawActors();
    }

    public void setupView()
    {
        view.load();
        model.init();
    }
}
