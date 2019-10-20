package tele.client.Room;

import DTO.Request.Room.RoomListRequest;
import DTO.Request.Users.UserInfoRequest;
import DTO.Response.RoomListResponse;
import DTO.Response.UserResponse;
import tele.client.Login.Data.Account;
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

    public void updateUserInfo(UserResponse response)
    {
        view.hideInfo();
        view.showInfo(response);
    }
    public void updateActors()
    {
        view.updateActors();
        view.drawActors();
    }

    public void setupView()
    {
        System.out.println(Account.getInstance().getID());
        model.init();
        model.send(new UserInfoRequest(Account.getInstance().getID()));
        view.load();
    }
}
