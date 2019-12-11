package Database.Manager;

import Database.ServerDB;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

public class ReportManager
{

    private ServerDB DB = ServerDB.getInstance();

    public void registerIP(String IP)
    {
        try (Connection conn = DB.getConnection())
        {
            PreparedStatement ps = conn.prepareStatement("INSERT INTO REPORT (IP) VALUES (?)");
            ps.setString(1, IP);
            ps.executeUpdate();
        } catch (Exception e)
        { e.printStackTrace(); }
    }

    public void increaseCnt(String IP)
    {
        if(!exists(IP)) registerIP(IP);
        try (Connection conn = DB.getConnection())
        {
            String query = "UPDATE REPORT SET CNT = ((SELECT CNT FROM REPORT WHERE IP = ?)+1)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, IP);
            ps.executeUpdate();
        } catch (Exception e)
        { e.printStackTrace(); }
    }

    public int getCnt(String IP)
    {
        if(!exists(IP)) return 0;
        try (Connection conn = DB.getConnection())
        {
            String query = "SELECT CNT FROM REPORT WHERE IP = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, IP);
            ResultSet set = ps.executeQuery();
            return set.getInt("cnt");
        } catch (Exception e)
        { e.printStackTrace(); }

        return 0;
    }

    public boolean exists(String IP)
    {
        try (Connection conn = DB.getConnection())
        {
            PreparedStatement ps = conn.prepareStatement("SELECT * FROM REPORT WHERE IP = ?");
            ps.setString(1, IP);
            return ps.executeQuery().next();
        } catch (Exception e)
        { e.printStackTrace(); }

        return false;
    }
}
