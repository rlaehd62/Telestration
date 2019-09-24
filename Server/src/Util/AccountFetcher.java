package Util;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;

public class AccountFetcher
{
    public static boolean isAvailable()
    {
        try
        {
            ServerDB DB = ServerDB.getInstance();
            Connection conn = DB.getConnection();
            String SEARCH = "SELECT * FROM ACCOUNT";
            ResultSet result = conn.createStatement().executeQuery(SEARCH);
            return result.next();
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }
}
