package Database.Manager;

import DTO.Request.Room.CreateRoomRequest;
import DTO.Response.RoomResponse;
import Database.ServerDB;
import MVP.DataPresenter;
import Util.State;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RoomManager implements DataPresenter.RoomModel
{
    private ServerDB DB = ServerDB.getInstance();
    private DataPresenter presenter;

    public void InsertRoom(CreateRoomRequest request)
    {
        String ID = request.getID();
        if(presenter.getUser(ID) == null || selectRoom(ID) != null) return;
        Connection conn = DB.getConnection();

        try
        {
            conn.setAutoCommit(false);

            String query = "INSERT INTO ROOM (STATE, TITLE, OWNER, LMT, TIMEOUT) " +
                    "SELECT ?, ?, ?, ?, ? WHERE EXISTS (SELECT * FROM USERS WHERE ID = ?) AND NOT EXISTS (SELECT * FROM ROOM WHERE OWNER = ?)";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, State.READY);
            ps.setString(2, request.getTitle());
            ps.setString(3, ID);
            ps.setInt(4, request.getLimit());
            ps.setInt(5, request.getTimeout());
            ps.setString(6, ID);
            ps.setString(7, ID);
            ps.executeUpdate();
            conn.commit();

            query = "UPDATE USERS SET ROOM_ID = (SELECT ID FROM ROOM WHERE OWNER = ?) " +
                    "WHERE ID = ? AND EXISTS (SELECT * FROM ROOM WHERE OWNER = ?)";
            ps = conn.prepareStatement(query);
            ps.setString(1, request.getID());
            ps.setString(2, request.getID());
            ps.setString(3, request.getID());
            ps.executeUpdate();

            conn.commit();
            conn.close();
            presenter.log("계정", ID + "의 새로운 방이 Room Table에 추가됨.");
        } catch (SQLException e)
        {
            try
            { conn.rollback(); }
            catch (SQLException ex)
            { ex.printStackTrace(); }
        }
    }

    public void RemoveRoom(int RoomID)
    {
        Connection conn = DB.getConnection();

        try
        {
            String  query = "UPDATE USERS SET ROOM_ID = ? WHERE ROOM_ID = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, State.OUT_OF_ROOM);
            ps.setInt(2, RoomID);
            ps.executeUpdate();

            query = "DELETE FROM ROOM WHERE ID = ?";
            ps = conn.prepareStatement(query);
            ps.setInt(1, RoomID);
            ps.executeUpdate();

            conn.commit();
            conn.close();
        } catch (SQLException e)
        {
            try
            { conn.rollback(); }
            catch (SQLException ex)
            { ex.printStackTrace(); }
        }
    }

    public void UpdateRoom(CreateRoomRequest request)
    {
        if(!request.isModifiable() || !presenter.isOnline(request.getID())) return;
        Connection conn = DB.getConnection();
        try
        {
            conn.setAutoCommit(false);
            String query = "UPDATE ROOM SET STATE = ?, TITLE = ?, OWNER = ?, LMT = ?, TIMEOUT = ? " +
                    "WHERE EXISTS (SELECT * FROM ROOM WHERE ID = (SELECT ID FROM ROOM WHERE OWNER = ?))";

            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, request.getState());
            ps.setString(2, request.getTitle());
            ps.setString(3, request.getID());
            ps.setInt(4, request.getLimit());
            ps.setInt(5, request.getTimeout());
            ps.setString(6, request.getID());
            ps.executeUpdate();

            conn.commit();
            conn.close();
            presenter.log("계정", request.getID() + "의 방이  Room Table 내에서 수정됨.");
        } catch (SQLException e)
        {
            try
            { conn.rollback(); }
            catch (SQLException ex)
            { ex.printStackTrace(); }
        }
    }

    public RoomResponse selectRoom(String owner)
    {
        if(presenter.getUser(owner) == null || !presenter.isOnline(owner)) return null;
        try (Connection conn = DB.getConnection())
        {
            String query = "SELECT ID, STATE, TITLE, OWNER, LMT, TIMEOUT FROM ROOM WHERE OWNER = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setString(1, owner);

            ResultSet result = ps.executeQuery();
            if(result.isBeforeFirst())
            {
                int RoomID = result.getInt("ID");
                RoomResponse response = new RoomResponse
                        (
                                result.getString("OWNER"),
                                result.getString("TITLE"),
                                result.getInt("STATE"),
                                result.getInt("LMT"),
                                result.getInt("TIMEOUT")
                        );

                response.setRoomID(RoomID);
                for(String name : presenter.getUsers(RoomID))
                    response.addUser(name);

                return response;
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public RoomResponse selectRoom(int RoomID)
    {
        if(RoomID <= State.OUT_OF_ROOM) return null;
        try (Connection conn = DB.getConnection())
        {
            String query = "SELECT ID, STATE, TITLE, OWNER, LMT, TIMEOUT FROM ROOM WHERE ID = ?";
            PreparedStatement ps = conn.prepareStatement(query);
            ps.setInt(1, RoomID);

            ResultSet result = ps.executeQuery();
            if(result.isBeforeFirst())
            {
                RoomResponse response = new RoomResponse
                        (
                                result.getString("owner"),
                                result.getString("title"),
                                result.getInt("state"),
                                result.getInt("lmt"),
                                result.getInt("timeout")
                        );

                response.setRoomID(RoomID);
                for(String name : presenter.getUsers(RoomID))
                    response.addUser(name);

                return response;
            }
        } catch (SQLException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public boolean hasRoom(int RoomID)
    {
        return (selectRoom(RoomID) != null);
    }

    public void setPresenter(DataPresenter presenter)
    {
        this.presenter = presenter;
    }
}
