package MVP;

import Database.DataManager;
import Request.LoginRequest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public interface DataPresenter
{

    void log(String tag, String text);

    boolean register(LoginRequest request);
    boolean setState(String ID, boolean online);
    boolean setRoomID(String ID, int RoomID);
    boolean removeRoomID(String ID);
    int getRoomID(String ID);

    boolean isRegistered(String ID);
    boolean isOnline(String ID);
    boolean isOwner(String owner);
    boolean hasRoom(int RoomID);
    void setPresenter(ServerPresenter presenter);

    boolean createRoom(String title, String owner, int limit, int timeout);
    boolean removeRoom(int RoomID);
    String getOwner(int RoomID);
    int searchRoom(String owner);
    String[] getMembers(int RoomID);

    interface DataModel
    {
        void initDB();
        Connection getConnection();
    }

    interface AccountModel
    {
        boolean register(LoginRequest request);
        boolean unregister(String ID);
        boolean isRegistered(String ID);
        void setPresenter(DataPresenter presenter);
    }

    interface UserModel
    {
        boolean setState(String ID, boolean online);
        boolean setRoomID(String ID, int RoomID);
        boolean removeRoomID(String ID);
        int getRoomID(String ID);
        boolean isOnline(String ID);
        void setPresenter(DataPresenter presenter);
    }

    interface RoomModel
    {
        boolean createRoom(String title, String owner, int limit, int timeout);
        boolean removeRoom(int RoomID);
        boolean hasRoom(int RoomID);
        String[] getMembers(int RoomID);
        String getOwner(int RoomID);
        boolean isOwner(String owner);
        int searchRoom(String owner);
        void setPresenter(DataPresenter presenter);
    }
}
