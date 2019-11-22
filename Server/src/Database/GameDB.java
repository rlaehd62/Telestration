package Database;

import DTO.Request.Room.CreateRoomRequest;
import Game.GameRoom;
import DTO.Request.Users.AddUserRequest;
import DTO.Response.Account.AccountResponse;
import DTO.Response.Room.CreateRoomResponse;
import DTO.Response.Room.RoomResponse;
import DTO.Response.User.UserResponse;
import Database.Manager.AccountManager;
import Database.Manager.GameRoomManager;
import Database.Manager.UserManager;
import Database.Manager.WordPoolManager;
import MVP.DataPresenter;
import MVP.ServerPresenter;
import DTO.Request.Account.LoginRequest;
import Server.ChannelManager;
import Util.State;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GameDB implements DataPresenter
{
    private static GameDB ins = null;
    private AccountModel account;
    private UserModel user;
    private WordPoolManager words;
    private GameRoomManager roomManager;
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


        roomManager = GameRoomManager.getInstance();
        words = new WordPoolManager();

        File file = new File(System.getProperty("user.home") + "./System/words.txt");
        words.init(file);
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
        boolean NOT_OWNER = !roomManager.containsRoom(ID) || !roomManager.containsUser(ID);

        if(isOnline(ID) && NOT_OWNER)
        {
            GameRoom r = roomManager.CreateRoom(ID, request.getTitle());
            r.setOwner(ID);
            r.setLevelLimit(request.getLimit());
            r.changeTimeout(request.getTimeout() / 60, request.getTimeout() % 60);

            CreateRoomResponse response = new CreateRoomResponse(new RoomResponse(r));
            response.setAccepted(true);
            request.getSender().writeAndFlush(response);
        }
    }

    public RoomResponse[] getRoomList()
    {
        List<RoomResponse> list = new ArrayList<>();
        for(GameRoom gr : GameRoomManager.getInstance().getRoomList())
        {
            if(gr.isRunning()) continue;
            RoomResponse response = new RoomResponse(gr);
            list.add(response);
        }

        if(list.size() > 0) return list.toArray(new RoomResponse[1]);
        return null;
    }

    public void UpdateUser(AddUserRequest request)
    {
        user.UpdateUser(request);
    }

    public UserResponse getUser(String ID)
    {
        return user.getUser(ID);
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
}
