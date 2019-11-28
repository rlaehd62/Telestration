import DTO.Request.Users.AddUserRequest;
import DTO.Response.User.UserResponse;
import Database.GameDB;
import Util.State;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest
{
    private GameDB manager = GameDB.getInstance();
    private String ID = "root";

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
        response.setExp(3000);

        manager.UpdateUser(new AddUserRequest(response));
        UserResponse re = manager.getUser(ID);
        assertEquals(re.getExp(), 3000);
        System.out.println(re.getExp());;
    }
}