package Database;

import MVP.DataPresenter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class ServerDB implements DataPresenter.DataModel
{
    private static ServerDB ins = null;

    private ServerDB() { }
    public static ServerDB getInstance()
    {
        return (ins != null) ? ins : (ins = new ServerDB());
    }

    public void initDB()
    {
        try
        {
            Statement state = getConnection().createStatement();

            String ACCOUNT =
                    "CREATE TABLE IF NOT EXISTS ACCOUNT" +
                            "(" +
                            "ID VARCHAR (45) PRIMARY KEY NOT NULL," +
                            "PW VARCHAR (45) NOT NULL" +
                            ")";

            String ROOM =
                    "CREATE TABLE IF NOT EXISTS ROOM" +
                            "(" +
                            "ID INT PRIMARY KEY NOT NULL," +
                            "TITLE VARCHAR (45) NOT NULL," +
                            "OWNER VARCHAR(45) NOT NULL," +
                            "LMT INT NOT NULL," +
                            "AMOUNT INT NOT NULL," +
                            "FOREIGN KEY (OWNER) REFERENCES ACCOUNT(ID)" +
                            ")";

            String USERS =
                    "CREATE TABLE IF NOT EXISTS USERS" +
                            "(" +
                            "ID INT PRIMARY KEY NOT NULL," +
                            "STATE INT NULLABLE," +
                            "LV INT NOT NULL," +
                            "EXP INT NOT NULL," +
                            "MAX_EXP INT NOT NULL," +
                            "ROOM_ID INT NULLABLE," +
                            "FOREIGN KEY (ID) REFERENCES ACCOUNT(ID)" +
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

    public synchronized Connection getConnection()
    {
        try
        {
            return DriverManager.getConnection("jdbc:sqlite:GAME.db");
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
