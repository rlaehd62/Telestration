package Database.Manager;

import DTO.Response.Account.AccountResponse;
import Database.ServerDB;
import MVP.DataPresenter;
import DTO.Request.Account.LoginRequest;

import java.sql.*;

public class AccountManager implements DataPresenter.AccountModel
{
    private ServerDB DB;
    private DataPresenter presenter;

    public AccountManager() { DB = ServerDB.getInstance(); }

    public void InsertAccount(LoginRequest request)
    {
        Connection conn = DB.getConnection();
        try
        {
            conn.setAutoCommit(false);

            String query = "INSERT INTO ACCOUNT (ID, PW) " +
                    "SELECT ?, ? WHERE NOT EXISTS (SELECT * FROM ACCOUNT WHERE ID = ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, request.getID());
            ps.setString(2, request.getPassword());
            ps.setString(3, request.getID());
            ps.executeUpdate();

            query = "INSERT INTO USERS (ID, STATE, LV, EXP, MAX_EXP, ROOM_ID)  " +
                    "SELECT ?, 0, 1, 0, 100, 0 WHERE NOT EXISTS (SELECT * FROM USERS WHERE ID = ?)";
            ps = conn.prepareStatement(query);
            ps.setString(1, request.getID());
            ps.setString(2, request.getID());
            ps.executeUpdate();

            conn.commit();
            conn.close();

            presenter.log("계정", request.getID() + " 계정이 Account Table에 추가됨.");
        } catch (SQLException e)
        {
            try { conn.rollback(); }
            catch (SQLException ex) { e.printStackTrace(); }
        }
    }

    public void UpdateAccount(LoginRequest request)
    {
        Connection conn = DB.getConnection();
        try
        {
            conn.setAutoCommit(false);

            String query = "UPDATE ACCOUNT SET PW = ? WHERE ID = ? AND EXISTS (SELECT * FROM ACCOUNT WHERE ID = ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, request.getPassword());
            ps.setString(2, request.getID());
            ps.setString(3, request.getID());
            ps.executeUpdate();

            conn.commit();
            conn.close();
            presenter.log("계정", request.getID() + " 계정이 Account Table 내에서 수정됨.");
        } catch (SQLException e)
        {
            try { conn.rollback(); }
            catch (SQLException ex) { e.printStackTrace(); }
        }
    }

    public AccountResponse getAccount(String ID)
    {
        try (Connection conn = DB.getConnection())
        {
            String query = "SELECT * FROM ACCOUNT WHERE ID = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, ID);

            ResultSet result = ps.executeQuery();
            if(result.isBeforeFirst()) return new AccountResponse(result.getString("ID"), result.getString("PW"), false);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public boolean hasAccount(String ID)
    {
        try (Connection conn = DB.getConnection())
        {
            String query = "SELECT * FROM ACCOUNT WHERE ID=?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, ID);

            ResultSet result = ps.executeQuery();
            boolean RETURN = result.isBeforeFirst();

            ps.close();
            return RETURN;
        } catch (SQLException e)
        {
            e.printStackTrace();
        }
        return false;
    }

    public void setPresenter(DataPresenter presenter)
    {
        this.presenter = presenter;
    }
}
