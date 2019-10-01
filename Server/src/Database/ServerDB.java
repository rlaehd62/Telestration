package Database;

import MVP.DataPresenter;

import java.io.File;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

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
                            "ID INTEGER PRIMARY KEY NOT NULL," +
                            "TITLE VARCHAR (45) NOT NULL," +
                            "OWNER VARCHAR(45) NOT NULL," +
                            "LMT INTEGER NOT NULL," +
                            "AMOUNT INTEGER NOT NULL," +
                            "TIMEOUT INTEGER NOT NULL," +
                            "FOREIGN KEY (OWNER) REFERENCES ACCOUNT(ID)" +
                            ")";

            String USERS =
                    "CREATE TABLE IF NOT EXISTS USERS" +
                            "(" +
                            "ID VARCHAR(45) PRIMARY KEY NOT NULL," +
                            "STATE INT NULLABLE," +
                            "LV INTEGER NOT NULL," +
                            "EXP INTEGER NOT NULL," +
                            "MAX_EXP INTEGER NOT NULL," +
                            "ROOM_ID INTEGER NULLABLE," +
                            "FOREIGN KEY (ID) REFERENCES ACCOUNT(ID) ON DELETE CASCADE" +
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
            String path = System.getProperty("user.home") + "/Telestration";
            File file = new File(path);
            if(!file.exists())
            {
                file.mkdirs();
            }

            Properties properties = new Properties();
            properties.setProperty("foreign_keys", "ON");
            return DriverManager.getConnection("jdbc:sqlite:" + path + "/GAME.db", properties);
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }
}
