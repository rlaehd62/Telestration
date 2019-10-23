package Database;

import DTO.Request.Room.CreateRoomRequest;
import DTO.Request.Users.AddUserRequest;
import DTO.Response.AccountResponse;
import DTO.Response.CreateRoomResponse;
import DTO.Response.RoomResponse;
import DTO.Response.UserResponse;
import Database.Manager.AccountManager;
import Database.Manager.RoomManager;
import Database.Manager.UserManager;
import MVP.DataPresenter;
import MVP.ServerPresenter;
import DTO.Request.Account.LoginRequest;
import Server.ChannelManager;
import Util.State;

import java.util.ArrayList;
import java.util.List;

public class GameDB implements DataPresenter
{
    private static GameDB ins = null;
    private AccountModel account;
    private UserModel user;
    private RoomModel room;
    private ServerPresenter presenter;

    public static GameDB getInstance()
    {
        return ins != null ? ins : (ins = new GameDB());
    }

    private GameDB()
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
        presenter.log(tag, text);
    }

    public void setPresenter(ServerPresenter presenter)
    {
        this.presenter = presenter;
    }

    public void register(LoginRequest request)
    {
        if(account.hasAccount(request.getID())) return;
        account.InsertAccount(request);
        login(request);
    }

    public void login(LoginRequest request)
    {
        String ID = request.getID();
        String PW = request.getPassword();

        AccountResponse ac_info = account.getAccount(ID);
        AddUserRequest update = new AddUserRequest(user.getUser(ID));
        ac_info.setAccepted(ac_info.getPassword().equals(PW));

        update.setState(ac_info.isAccepted() ? State.ONLINE : State.OFFLINE);
        update.setSender(request.getSender());
        user.UpdateUser(update);

        if(ac_info.isAccepted()) ChannelManager.getChannels().put(ID, request.getSender());
        request.getSender().writeAndFlush(ac_info);
    }

    public void createRoom(CreateRoomRequest request)
    {
        String ID = request.getID();
        boolean NOT_OWNER = room.getRoom(ID) == null;

        if(isOnline(ID) && NOT_OWNER)
        {
            if(request.isModifiable()) room.UpdateRoom(request);
            else room.CreateRoom(request);
            boolean isAccepted = (room.getRoom(ID) != null);
            if(!isAccepted) return;

            RoomResponse response = room.getRoom(ID);
            request.getSender().writeAndFlush(response);
        }
    }

    public RoomResponse[] getRoomList()
    {
        List<RoomResponse> list = new ArrayList<>();
        for(String name : user.getUsers())
        {
            RoomResponse response = room.getRoom(name);
            if(response != null) list.add(response);
        }

        return list.toArray(new RoomResponse[1]);
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
        room.CreateRoom(request);
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
        return room.getRoom(owner);
    }

    public RoomResponse selectRoom(int RoomID)
    {
        return room.getRoom(RoomID);
    }

    public boolean hasRoom(int RoomID)
    {
        return room.hasRoom(RoomID);
    }
}
