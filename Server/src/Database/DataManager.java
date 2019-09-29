package Database;

import MVP.DataPresenter;
import MVP.ServerPresenter;
import Request.LoginRequest;
import com.sun.xml.internal.bind.v2.model.core.ID;

import java.sql.*;

public class DataManager implements DataPresenter
{
    private static DataManager ins = null;
    private DataModel model;
    private ServerPresenter presenter;

    private DataManager()
    {
        model = ServerDB.getInstance();
    }
    public static DataManager getInstance()
    {
        return ins != null ? ins : (ins = new DataManager());
    }

    public synchronized void register(LoginRequest request)
    {
        if(!isRegistered(request.getID()))
        {
            Connection conn = model.getConnection();
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
                presenter.log("중요", request.getID() + " 계정이 추가되었습니다.");
            }
            catch (SQLException e)
            {
                try
                {
                    conn.rollback();
                    presenter.log("오류", "사용자의 계정을 추가하는 과정에서 오류가 발생했습니다.");
                    presenter.log("복구", "데이터를 입력하기 이전으로 롤백합니다.");
                } catch (SQLException ex) { ex.printStackTrace(); }
                e.printStackTrace();
            }
        }
    }

    public synchronized boolean isRegistered(String ID)
    {
        try (Connection conn = model.getConnection())
        {
            String SEARCH = "SELECT * FROM ACCOUNT where ID=?";
            PreparedStatement query = conn.prepareStatement(SEARCH);
            query.setString(1, ID);
            ResultSet result = query.executeQuery();
            boolean RETURN = result.isBeforeFirst();
            result.close();

            return RETURN;
        }

        catch (SQLException e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public synchronized void setRoomID(String ID, int RoomID)
    {
        if(isRegistered(ID) && isOnline(ID) && hasRoom(RoomID))
            try (Connection conn = model.getConnection())
            {
                String UPDATE = "UPDATE USERS SET ROOM_ID=?";
                PreparedStatement UPD  = conn.prepareStatement(UPDATE);
                UPD.setInt(1, RoomID);
                UPD.executeUpdate();
                UPD.close();

            } catch (Exception e)
            {
                e.printStackTrace();
            }
    }

    public void createRoom(String OWNER, String TITLE, int LIMIT)
    {
        if(isRegistered(OWNER) && isOnline(OWNER) && !isOwner(OWNER))
        {
            Connection conn = model.getConnection();
            try
            {
                conn.setAutoCommit(false);

                String QUERY = "INSERT INTO ROOM(ID, TITLE, OWNER, LMT, AMOUNT) VALUES(NULL, ?, ?, ?, ?)";
                PreparedStatement ps = conn.prepareStatement(QUERY);
                ps.setString(1, TITLE);
                ps.setString(2, OWNER);
                ps.setInt(3, LIMIT);
                ps.setInt(4, 0);
                ps.executeUpdate();

                conn.commit();
                ps.close();
                conn.close();
            } catch (Exception e)
            {
                try
                {
                    conn.rollback();
                    presenter.log("오류", "사용자의 계정을 추가하는 과정에서 오류가 발생했습니다.");
                    presenter.log("복구", "데이터를 입력하기 이전으로 롤백합니다.");
                } catch (SQLException ex) { ex.printStackTrace(); }
                e.printStackTrace();
            }
        }
    }

    public synchronized boolean hasRoom(int RoomID)
    {
        try (Connection conn = model.getConnection())
        {
            String QUERY1 = "SELECT * FROM ROOM WHERE ID=?";
            PreparedStatement SEARCH = conn.prepareStatement(QUERY1);
            SEARCH.setInt(1, RoomID);

            ResultSet result = SEARCH.executeQuery();
            boolean RETURN = result.isBeforeFirst();
            result.close();

            return RETURN;
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public synchronized boolean isOwner(String ID)
    {
        try (Connection conn = model.getConnection())
        {
            String QUERY1 = "SELECT * FROM ROOM WHERE OWNER=?";
            PreparedStatement SEARCH = conn.prepareStatement(QUERY1);
            SEARCH.setString(1, ID);
            ResultSet result = SEARCH.executeQuery();
            boolean RETURN = result.isBeforeFirst();
            result.close();

            return RETURN;
        } catch (Exception e)
        {
            e.printStackTrace();
        }

        return false;
    }

    public synchronized void setOnline(String ID, boolean isOnline)
    {
        if(!isRegistered(ID)) return;
        try (Connection conn = model.getConnection())
        {
            final int STATE = isOnline ? 1 : 0;
            String QUERY1 = "UPDATE USERS SET STATE=?";
            PreparedStatement INSERT = conn.prepareStatement(QUERY1);
            INSERT.setInt(1, STATE);
            INSERT.executeUpdate();
            INSERT.close();
        } catch (Exception e)
        {
            e.printStackTrace();
        }
    }

    public synchronized boolean isOnline(String ID)
    {
        if(!isRegistered(ID)) return false;
        else
        {
            try (Connection conn = model.getConnection())
            {
                String SEARCH = "SELECT * FROM USERS WHERE ID=? AND STATE=1";
                PreparedStatement query = conn.prepareStatement(SEARCH);
                query.setString(1, ID);
                ResultSet result = query.executeQuery();

                boolean RETURN = result.isBeforeFirst();
                result.close();

                return RETURN;
            } catch (Exception e)
            {
                e.printStackTrace();
            }
        }

        return false;
    }

    public void setPresenter(ServerPresenter presenter)
    {
        this.presenter = presenter;
    }
}
