package Database.Manager;

import DTO.Request.Users.AddUserRequest;
import DTO.Response.UserResponse;
import Database.ServerDB;
import MVP.DataPresenter;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserManager implements DataPresenter.UserModel
{
    private ServerDB DB;
    private DataPresenter presenter;

    public UserManager() { DB = ServerDB.getInstance(); }

    public void UpdateUser(AddUserRequest request)
    {
        if(request == null) return;
        Connection conn = DB.getConnection();
        try
        {
            conn.setAutoCommit(false);

            String query = "UPDATE USERS SET STATE = ?, LV = ?, EXP = ?, MAX_EXP = ?, ROOM_ID = ? WHERE ID = ? AND EXISTS (SELECT * FROM USERS WHERE ID = ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, request.getState());
            ps.setInt(2, request.getLevel());
            ps.setInt(3, request.getExp());
            ps.setInt(4, request.getMaxExp());
            ps.setInt(5, request.getRoomID());

            ps.setString(6, request.getID());
            ps.setString(7, request.getID());
            ps.executeUpdate();

            conn.commit();
            conn.close();
            presenter.log("유저 정보", request.getID() + " 유저의 정보가 User Table 내에서 수정됨.");
        } catch (SQLException e)
        {
            try { conn.rollback(); }
            catch (SQLException ex) { e.printStackTrace(); }
        }
    }

    public UserResponse getUser(String ID)
    {
        try (Connection conn = DB.getConnection())
        {
            String query = "SELECT STATE, LV, EXP, MAX_EXP, ROOM_ID FROM USERS WHERE EXISTS (SELECT * FROM USERS WHERE ID = ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, ID);

            ResultSet result = ps.executeQuery();
            if(result.isBeforeFirst())
            {
                UserResponse response = new UserResponse(ID);
                response.setState(result.getInt("STATE"));
                response.setLevel(result.getInt("LV"));
                response.setExp(result.getInt("EXP"));
                response.setMaxExp(result.getInt("MAX_EXP"));
                response.setRoomID(result.getInt("ROOM_ID"));
                return response;
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public String[] getUsers(int RoomID)
    {
        List<String> list = new ArrayList<>();
        try (Connection conn = DB.getConnection())
        {
            String query = "SELECT ID FROM USERS WHERE EXISTS (SELECT * FROM USERS WHERE ROOM_ID = ?)";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, RoomID);

            ResultSet result = ps.executeQuery();
            while(result.next())
                list.add(result.getString(1));

            return list.toArray(new String[1]);
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return list.toArray(new String[1]);
    }

    public String[] getUsers()
    {
        List<String> list = new ArrayList<>();
        try (Connection conn = DB.getConnection())
        {
            String query = "SELECT ID FROM USERS";
            PreparedStatement ps = conn.prepareStatement(query);

            ResultSet result = ps.executeQuery();
            while(result.next())
                list.add(result.getString(1));
            return list.toArray(new String[1]);

        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return list.toArray(new String[1]);
    }

    public void setPresenter(DataPresenter presenter)
    {
        this.presenter = presenter;
    }
}
