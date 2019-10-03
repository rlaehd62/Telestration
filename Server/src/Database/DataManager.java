package Database;

import DTO.Request.Users.AddUserRequest;
import DTO.Response.AccountResponse;
import DTO.Response.UserResponse;
import Database.Manager.AccountManager;
import Database.Manager.RoomManager;
import Database.Manager.UserManager;
import MVP.DataPresenter;
import MVP.ServerPresenter;
import DTO.Request.Account.LoginRequest;

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
        presenter.log(tag, text);
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
}
