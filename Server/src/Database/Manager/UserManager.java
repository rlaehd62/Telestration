package Database.Manager;

import Database.ServerDB;
import MVP.DataPresenter;

import java.sql.*;

public class UserManager implements DataPresenter.UserModel
{
    private ServerDB DB;
    private DataPresenter presenter;

    public UserManager() { DB = ServerDB.getInstance(); }
    public boolean setState(String ID, boolean online)
    {
        Connection conn = DB.getConnection();

        try
        {
            final int state = (online) ? 1 : 0;

            String Q = "UPDATE USERS SET STATE=? WHERE ID=?";
            PreparedStatement st = conn.prepareStatement(Q);
            st.setInt(1, state);
            st.setString(2, ID);
            st.executeUpdate();

            st.close();
            conn.close();
            presenter.log("DB", "상태를 변경했음.");
            return true;
        } catch (SQLException e)
        {
            try { conn.rollback(); }
            catch (SQLException ex) { e.printStackTrace(); }
            presenter.log("DB", "상태 변경 실패");
            return false;
        }
    }

    public boolean isOnline(String ID)
    {
        try (Connection conn = DB.getConnection())
        {
            String Q = "SELECT * FROM USERS WHERE ID=? AND STATE=1";
            PreparedStatement st = conn.prepareStatement(Q);
            st.setString(1, ID);

            ResultSet result = st.executeQuery();
            boolean RETURN = result.isBeforeFirst();

            result.close();
            return RETURN;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public boolean setRoomID(String ID, int RoomID)
    {
        Connection conn = DB.getConnection();

        try
        {
            String Q = "UPDATE USERS SET ROOM_ID=? WHERE ID=? AND EXISTS (SELECT * FROM ROOM WHERE ID=?)";
            PreparedStatement st = conn.prepareStatement(Q);
            st.setInt(1, RoomID);
            st.setString(2, ID);
            st.setInt(3, RoomID);
            st.executeUpdate();

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

    public boolean removeRoomID(String ID)
    {
        Connection conn = DB.getConnection();

        try
        {
            String Q = "UPDATE USERS SET ROOM_ID=? WHERE ID=?";
            PreparedStatement st = conn.prepareStatement(Q);
            st.setNull(1, Types.INTEGER);
            st.setString(2, ID);
            st.executeUpdate();

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

    public int getRoomID(String ID)
    {
        try (Connection conn = DB.getConnection())
        {
            String Q = "select ROOM_ID from USERS where ID=?";
            PreparedStatement st = conn.prepareStatement(Q);
            st.setString(1, ID);

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
