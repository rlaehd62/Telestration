package MVP;

import Request.LoginRequest;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public interface DataPresenter
{
    void register(LoginRequest request);
    boolean isRegistered(String ID);

    void setOnline(String ID, boolean isOnline);
    boolean isOnline(String ID);

    boolean hasRoom(int ID);
    void setRoomID(String ID, int RoomID);
    void createRoom(String OWNER, String TITLE, int LIMIT);
    boolean isOwner(String ID);

    void setPresenter(ServerPresenter presenter);

    interface DataModel
    {
        void initDB();
        Connection getConnection();
    }
}
