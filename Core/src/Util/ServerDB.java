package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ServerDB
{
    private static ServerDB ins = null;
    private Connection conn = null;

    private ServerDB() { }
    public static ServerDB getInstance()
    {
        return (ins != null) ? ins : (ins = new ServerDB());
    }

    public void initDB()
    {
        try
        {
            Statement state = conn.createStatement();

            String ACCOUNT =
                    "CREATE TABLE IF NOT EXISTS ACCOUNT" +
                            "(" +
                            "ID INT PRIMARY KEY NOT NULL," +
                            "PW VARCHAR (45) NOT NULL" +
                            ")";

            String ROOM =
                    "CREATE TABLE IF NOT EXISTS ROOM" +
                            "(" +
                            "ID INT PRIMARY KEY NOT NULL," +
                            "TITLE VARCHAR (45) NOT NULL," +
                            "OWNER INT NOT NULL," +
                            "LMT INT NOT NULL," +
                            "AMOUNT INT NOT NULL," +
                            "FOREIGN KEY (OWNER) REFERENCES ACCOUNT(ID)" +
                            ")";

            String USERS =
                    "CREATE TABLE IF NOT EXISTS USERS" +
                            "(" +
                            "ID INT PRIMARY KEY NOT NULL," +
                            "STATE INT NOT NULL," +
                            "LV INT NOT NULL," +
                            "EXP INT NOT NULL," +
                            "MAX_EXP INT NOT NULL," +
                            "ROOM_ID INT NOT NULL," +
                            "FOREIGN KEY (ROOM_ID) REFERENCES ROOM(ID)" +
                            ")";

            state.execute(ACCOUNT);
            state.execute(ROOM);
            state.execute(USERS);
            state.close();
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }
    }

    public synchronized boolean open()
    {
        try
        {
            Class.forName("org.sqlite.JDBC");
            conn = DriverManager.getConnection("jdbc:sqlite:GAME.db");
            initDB();
            return true;
        }

        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public synchronized boolean close()
    {
        try
        {
            if(!conn.isClosed())
            {
                conn.close();
                return true;
            }
        }

        catch (Exception e)
        {
            e.printStackTrace();
            return false;
        }

        return false;
    }

    public synchronized Connection getConnection()
    {
        return conn;
    }
}
