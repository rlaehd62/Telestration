package Database;

import Database.Manager.AccountManager;
import Database.Manager.RoomManager;
import Database.Manager.UserManager;
import MVP.DataPresenter;
import MVP.ServerPresenter;
import Request.LoginRequest;

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

        room = new RoomManager();
        room.setPresenter(this);

        user = new UserManager();
        user.setPresenter(this);

    }

    public void log(String tag, String text)
    {
        presenter.log(tag, text);
    }

    public boolean register(LoginRequest request)
    {
        return account.register(request);
    }

    public boolean isRegistered(String ID)
    {
        return account.isRegistered(ID);
    }


    public boolean setState(String ID, boolean online)
    {
        return user.setState(ID, online);
    }

    public boolean isOnline(String ID)
    {
        return user.isOnline(ID);
    }

    public boolean setRoomID(String ID, int RoomID)
    {
        return user.setRoomID(ID, RoomID);
    }

    public boolean removeRoomID(String ID)
    {
        return user.removeRoomID(ID);
    }

    public int getRoomID(String ID)
    {
        return user.getRoomID(ID);
    }


    public boolean createRoom(String title, String owner, int limit, int timeout)
    {
        return room.createRoom(title, owner, limit, timeout);
    }

    public boolean hasRoom(int RoomID)
    {
        return room.hasRoom(RoomID);
    }

    public boolean removeRoom(int RoomID)
    {
        return room.removeRoom(RoomID);
    }

    public boolean isOwner(String owner)
    {
        return room.isOwner(owner);
    }

    public String getOwner(int RoomID)
    {
        return room.getOwner(RoomID);
    }

    public int searchRoom(String owner)
    {
        return searchRoom(owner);
    }

    public String[] getMembers(int RoomID)
    {
        return room.getMembers(RoomID);
    }

    public void setPresenter(ServerPresenter presenter)
    {
        this.presenter = presenter;
    }
}
