package Util;

import Request.LoginRequest;
import Swing.ServerController;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataController
{
    private static DataController ins = null;
    private ServerDB DB;

    public static DataController getInstance()
    {
        return ins != null ? ins : (ins = new DataController());
    }

    private DataController()
    {
        DB = ServerDB.getInstance();
    }

    public synchronized void signup(LoginRequest message)
    {
        if(!hasAccount(message.getID(), message.getPW()))
        {
            try
            {
                String QUERY = "INSERT INTO ACCOUNT(ID, PW) VALUES(?, ?)";
                PreparedStatement INSERT = DB.getConnection().prepareStatement(QUERY);
                INSERT.setString(1, message.getID());
                INSERT.setString(2, message.getPW());
                INSERT.execute();
                INSERT.close();
            }
            catch (SQLException e)
            {
                e.printStackTrace();
            }
        }
    }

    public synchronized boolean hasAccount(String ID, String PW)
    {
        try
        {
            Connection conn = DB.getConnection();

            String SEARCH = "SELECT * FROM ACCOUNT where ID=? AND PW=?";
            PreparedStatement query = conn.prepareStatement(SEARCH);
            query.setString(1, ID);
            query.setString(2, PW);
            ResultSet result = query.executeQuery();
            query.close();
            return result.next();
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }
}
