package Database.Manager;

import Database.ServerDB;
import MVP.DataPresenter;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class RoomManager implements DataPresenter.RoomModel
{
    private ServerDB DB = ServerDB.getInstance();
    private DataPresenter presenter;

    public boolean createRoom(String title, String owner, int limit, int timeout)
    {
        Connection conn = DB.getConnection();
        if(isOwner(owner)) return false;

        try
        {
            conn.setAutoCommit(false);

            String Q = "INSERT INTO ROOM(TITLE, OWNER, LMT, TIMEOUT) VALUES (?, ?, ?, ?)";
            PreparedStatement st = conn.prepareStatement(Q);
            st.setString(1, title);
            st.setString(2, owner);
            st.setInt(3, limit);
            st.setInt(4, timeout);
            st.executeUpdate();

            final int RoomID = searchRoom(owner);
            if(RoomID > 0) presenter.setRoomID(owner, RoomID);

            conn.commit();
            st.close();
            conn.close();
            return true;
        } catch (SQLException e)
        {
            try { conn.rollback(); }
            catch (SQLException ex) { e.printStackTrace(); }
            return false;
        }
    }

    public boolean removeRoom(int RoomID)
    {
        Connection conn = DB.getConnection();

        try
        {
            conn.setAutoCommit(false);

            // if(!presenter.removeRoomID(getOwner(RoomID))) return false;
            // String temp = "SELECT ID FROM USERS WHERE ROOM_ID=?";
            String R = "UPDATE USERS SET ROOM_ID=NULL WHERE ROOM_ID=?";
            PreparedStatement rs = conn.prepareStatement(R);
            rs.setInt(1, RoomID);
            rs.executeUpdate();

            String Q = "DELETE FROM ROOM WHERE ID=?";
            PreparedStatement st = conn.prepareStatement(Q);
            st.setInt(1, RoomID);
            st.executeUpdate();

            conn.commit();
            rs.close();
            st.close();
            conn.close();
            return true;
        } catch (SQLException e)
        {
            try { conn.rollback(); }
            catch (SQLException ex) { e.printStackTrace(); }
            return false;
        }
    }

    public boolean hasRoom(int RoomID)
    {
        try (Connection conn = DB.getConnection())
        {
            String Q = "select * from ROOM where ID=?";
            PreparedStatement st = conn.prepareStatement(Q);
            st.setInt(1, RoomID);

            ResultSet result = st.executeQuery();
            boolean RETURN = result.isBeforeFirst();

            result.close();
            st.close();
            return RETURN;
        } catch (SQLException e)
        {
            return false;
        }
    }

    public String[] getMembers(int RoomID)
    {
        if(!hasRoom(RoomID)) return new String[] {};
        try (Connection conn = DB.getConnection())
        {
            List<String> list = new ArrayList<>();
            String R = "SELECT ID FROM USERS WHERE ROOM_ID=? AND STATE=1";
            PreparedStatement rs = conn.prepareStatement(R);
            rs.setInt(1, RoomID);

            ResultSet result = rs.executeQuery();
            for(int i = 1; result.next(); i++) list.add(result.getString(i));

            result.close();
            rs.close();
            return list.toArray(new String[1]);
        } catch (SQLException e)
        {
            return new String[] {};
        }
    }

    public String getOwner(int RoomID)
    {
        try (Connection conn = DB.getConnection())
        {
            String Q = "select OWNER from ROOM where ID=?";
            PreparedStatement st = conn.prepareStatement(Q);
            st.setInt(1, RoomID);

            ResultSet result = st.executeQuery();
            String RETURN = result.getString(1);

            result.close();
            st.close();
            return RETURN;
        } catch (SQLException e)
        {
            return "NULL$#FDS";
        }
    }

    public boolean isOwner(String owner)
    {
        try (Connection conn = DB.getConnection())
        {
            String Q = "select * from ROOM where OWNER=?";
            PreparedStatement st = conn.prepareStatement(Q);
            st.setString(1, owner);

            ResultSet result = st.executeQuery();
            boolean RETURN = result.isBeforeFirst();

            result.close();
            st.close();
            return RETURN;
        } catch (SQLException e)
        {
            return false;
        }
    }

    public int searchRoom(String owner)
    {
        try (Connection conn = DB.getConnection())
        {
            String Q = "select ID from ROOM where exists (select * from ROOM where OWNER=?)";
            PreparedStatement st = conn.prepareStatement(Q);
            st.setString(1, owner);

            ResultSet result = st.executeQuery();
            int index = result.getInt(1);

            result.close();
            st.close();
            return index;
        } catch (SQLException e)
        {
            return 0;
        }
    }

    public void setPresenter(DataPresenter presenter)
    {
        this.presenter = presenter;
    }
}
