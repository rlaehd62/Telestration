package Database.Manager;

import Database.ServerDB;
import MVP.DataPresenter;
import Request.LoginRequest;

import java.sql.*;

public class AccountManager implements DataPresenter.AccountModel
{
    private ServerDB DB;
    private DataPresenter presenter;

    public AccountManager() { DB = ServerDB.getInstance(); }

    public boolean register(LoginRequest request)
    {
        Connection conn = DB.getConnection();
        if(isRegistered(request.getID())) return false;

        try
        {
            conn.setAutoCommit(false);

            String QUERY1 = "INSERT INTO ACCOUNT(ID, PW) VALUES(?, ?)";
            PreparedStatement INSERT1 = conn.prepareStatement(QUERY1);
            INSERT1.setString(1, request.getID());
            INSERT1.setString(2, request.getPW());
            INSERT1.executeUpdate();

            String QUERY2 = "INSERT INTO USERS (ID, STATE, LV, EXP, MAX_EXP, ROOM_ID) VALUES (?, ?, ?, ?, ?, ?)";
            PreparedStatement INSERT2 = conn.prepareStatement(QUERY2);

            INSERT2.setString(1, request.getID());
            INSERT2.setInt(2, 0);
            INSERT2.setInt(3, 1);
            INSERT2.setInt(4, 0);
            INSERT2.setInt(5, 100);
            INSERT2.setNull(6, Types.INTEGER);
            INSERT2.executeUpdate();

            conn.commit();
            INSERT1.close();
            INSERT2.close();
            conn.close();
            presenter.log("DB", "계정 " + request.getID() + "가 추가됨.");
            return true;
        } catch (SQLException e)
        {
            try { conn.rollback(); }
            catch (SQLException ex) { ex.printStackTrace(); }
            presenter.log("DB", "계정을 추가 중 오류로 인하여 롤백함.");
            return false;
        }

    }

    public boolean unregister(String ID)
    {
        if(!isRegistered(ID)) return false;
        Connection conn = DB.getConnection();

        try
        {
            conn.setAutoCommit(false);

            String Q1 = "DELETE FROM ACCOUNT WHERE ID=?";
            PreparedStatement st1 = conn.prepareStatement(Q1);
            st1.setString(1, ID);
            st1.executeUpdate();

            conn.commit();
            conn.close();
            presenter.log("DB", "계정 " + ID + "를 삭제함.");
            return true;
        } catch (SQLException e)
        {
            try { conn.rollback(); }
            catch (SQLException ex) { ex.printStackTrace(); }
            presenter.log("DB", "계정 " + ID + "를 삭제하던 중 오류로 인하여 롤백함.");
            return false;
        }
    }

    public boolean isRegistered(String ID)
    {
        try (Connection conn = DB.getConnection())
        {
            String SEARCH = "SELECT * FROM ACCOUNT where ID=?";
            PreparedStatement statement = conn.prepareStatement(SEARCH);
            statement.setString(1, ID);

            ResultSet result = statement.executeQuery();
            boolean RETURN = result.isBeforeFirst();

            result.close();
            return RETURN;
        } catch (SQLException e)
        {
            e.printStackTrace();
            return false;
        }
    }

    public void setPresenter(DataPresenter presenter)
    {
        this.presenter = presenter;
    }
}
