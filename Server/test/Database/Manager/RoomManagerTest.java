package Database.Manager;

import DTO.Request.Account.LoginRequest;
import DTO.Request.Room.CreateRoomRequest;
import DTO.Request.Users.AddUserRequest;
import DTO.Response.RoomResponse;
import DTO.Response.UserResponse;
import Database.DataManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomManagerTest
{
    private DataManager manager = DataManager.getInstance();

    @Test
    void insertRoom()
    {
        manager.InsertRoom(new CreateRoomRequest("rlaehd62", "TEST"));
        RoomResponse response = manager.selectRoom("rlaehd62");

        assertNotNull(response);
        System.out.println(response.getTitle());
        System.out.println(response.getOwner());
        System.out.println(response.getUsers());

        CreateRoomRequest request = new CreateRoomRequest(manager.selectRoom("rlaehd62"));
        assertNotNull(request);

        request.setModifiable(true);
        request.setTitle("NEW TITLE");

        manager.UpdateRoom(request);
        response = manager.selectRoom("rlaehd62");
        assertEquals(response.getTitle(), "NEW TITLE");
        System.out.println(response.getTitle());

        manager.InsertAccount(new LoginRequest("HOON", "AWEY"));
        AddUserRequest userRequest = new AddUserRequest(manager.getUser("HOON"));
        userRequest.setModifiable(true);
        userRequest.setRoomID(response.getRoomID());

        response = manager.selectRoom("rlaehd62");
        System.out.println(response.getTitle());
        System.out.println(response.getUsers());
    }

}