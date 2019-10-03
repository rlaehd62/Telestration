package Database.Manager;

import DTO.Request.Users.AddUserRequest;
import DTO.Response.UserResponse;
import Database.DataManager;
import Util.State;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest
{
    private DataManager manager = DataManager.getInstance();
    private String ID = "rlaehd62";

    @Test
    void insertUser()
    {
        UserResponse response = manager.getUser(ID);
        assertNotNull(response);
        assertEquals(response.getID(), ID);
    }

    @Test
    void updateUser()
    {
        AddUserRequest request = new AddUserRequest(ID);
        request.setState(State.OFFLINE);
        manager.UpdateUser(request);

        UserResponse response = manager.getUser(ID);
        assertEquals(response.getState(), State.OFFLINE);

        request.setState(State.ONLINE);
        request.setRoomID(State.OUT_OF_ROOM);
        manager.UpdateUser(request);

        response = manager.getUser(ID);
        assertEquals(response.getState(), State.ONLINE);
        assertEquals(response.getRoomID(), State.OUT_OF_ROOM);
    }
}