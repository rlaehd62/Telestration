package MVP;

import DTO.Request.Users.AddUserRequest;
import DTO.Request.Room.CreateRoomRequest;
import DTO.Request.Account.LoginRequest;
import DTO.Response.AccountResponse;
import DTO.Response.RoomResponse;
import DTO.Response.UserResponse;

import java.sql.Connection;

public interface DataPresenter
{
    void log(String tag, String text);
    void setPresenter(ServerPresenter presenter);

    void register(LoginRequest request);
    void login(LoginRequest request);

    void createRoom(CreateRoomRequest request);
    RoomResponse[] getRoomList();

    void UpdateUser(AddUserRequest request);
    UserResponse getUser(String ID);
    String[] getUsers(int RoomID);
    boolean isOnline(String ID);

    void InsertAccount(LoginRequest request);
    void UpdateAccount(LoginRequest request);
    AccountResponse getAccount(String ID);
    boolean hasAccount(String ID);

    void InsertRoom(CreateRoomRequest request);
    void UpdateRoom(CreateRoomRequest request);
    RoomResponse selectRoom(String owner);
    RoomResponse selectRoom(final int RoomID);
    boolean hasRoom(final int RoomID);

    interface DataModel
    {
        void initDB();
        Connection getConnection();
    }

    interface AccountModel
    {
        void InsertAccount(LoginRequest request);
        void UpdateAccount(LoginRequest request);
        AccountResponse getAccount(String ID);
        boolean hasAccount(String ID);
        void setPresenter(DataPresenter presenter);
    }

    interface RoomModel
    {
        void CreateRoom(CreateRoomRequest request);
        void RemoveRoom(int RoomID);
        void UpdateRoom(CreateRoomRequest request);
        RoomResponse getRoom(String owner);
        RoomResponse getRoom(final int RoomID);
        boolean hasRoom(final int RoomID);
        void setPresenter(DataPresenter presenter);
    }

    interface UserModel
    {
        void UpdateUser(AddUserRequest request);
        UserResponse getUser(String ID);
        String[] getUsers(int RoomID);
        String[] getUsers();
        void setPresenter(DataPresenter presenter);

    }
}
