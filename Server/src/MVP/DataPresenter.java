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

    void setOffline(String ID);
    void setOnline(String ID);
    boolean isOnline(String ID);

    boolean hasRoom(int ID);
    void setRoomID(String ID, int RoomID);

    void setPresenter(ServerPresenter presenter);

    interface DataModel
    {
        void initDB();
        Connection getConnection();
    }
}
