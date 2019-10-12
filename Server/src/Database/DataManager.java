package Database;

import DTO.Request.Room.CreateRoomRequest;
import DTO.Request.Users.AddUserRequest;
import DTO.Response.AccountResponse;
import DTO.Response.RoomResponse;
import DTO.Response.UserResponse;
import Database.Manager.AccountManager;
import Database.Manager.RoomManager;
import Database.Manager.UserManager;
import MVP.DataPresenter;
import MVP.ServerPresenter;
import DTO.Request.Account.LoginRequest;
import Util.State;

public class DataManager implements DataPresenter
{
    private static DataManager ins = null;
    private AccountModel account;
    private UserModel user;
    private RoomModel room;
    private ServerPresenter presenter;

    public static DataManager getInstance()
    {
        return ins != null ? ins : (ins = new DataManager());
    }

    private DataManager()
    {
        account = new AccountManager();
        account.setPresenter(this);

        user = new UserManager();
        user.setPresenter(this);

        room = new RoomManager();
        room.setPresenter(this);
    }

    public void log(String tag, String text)
    {
        System.out.printf("[%s] %s\n", tag, text);
        // presenter.log(tag, text);
    }

    public void setPresenter(ServerPresenter presenter)
    {
        this.presenter = presenter;
    }

    public void UpdateUser(AddUserRequest request)
    {
        user.UpdateUser(request);
    }

    public UserResponse getUser(String ID)
    {
        return user.getUser(ID);
    }

    public String[] getUsers(int RoomID)
    {
        return user.getUsers(RoomID);
    }

    public boolean isOnline(String ID)
    {
        UserResponse response = getUser(ID);
        if(response != null)
        {
            return response.getState() == State.ONLINE;
        }

        return false;
    }

    public void InsertAccount(LoginRequest request)
    {
        account.InsertAccount(request);
    }

    public void UpdateAccount(LoginRequest request)
    {
        account.UpdateAccount(request);
    }

    public AccountResponse getAccount(String ID)
    {
        return account.getAccount(ID);
    }

    public boolean hasAccount(String ID)
    {
        return account.hasAccount(ID);
    }

    public void InsertRoom(CreateRoomRequest request)
    {
        room.InsertRoom(request);
    }

    public void RemoveRoom(int RoomID)
    {
        room.RemoveRoom(RoomID);
    }

    public void UpdateRoom(CreateRoomRequest request)
    {
        room.UpdateRoom(request);
    }

    public RoomResponse selectRoom(String owner)
    {
        return room.selectRoom(owner);
    }

    public RoomResponse selectRoom(int RoomID)
    {
        return room.selectRoom(RoomID);
    }

    public boolean hasRoom(int RoomID)
    {
        return room.hasRoom(RoomID);
    }
}
