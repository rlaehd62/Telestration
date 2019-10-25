import DTO.Request.Account.LoginRequest;
import DTO.Request.Room.CreateRoomRequest;
import DTO.Request.Room.GameRoom;
import DTO.Request.Users.AddUserRequest;
import DTO.Response.RoomResponse;
import Database.GameDB;
import Database.Manager.GameRoomManager;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoomManagerTest
{
    private GameRoomManager gm = GameRoomManager.getInstance();

    @Test
    void insertRoom()
    {
        GameRoom gameRoom = gm.CreateRoom("rlaehd62", "이 멀티룸은 테스트용 멀티룸");
        assertNotNull(gameRoom);
        assertEquals(gameRoom.getTitle(), "이 멀티룸은 테스트용 멀티룸");
        assertFalse(gameRoom.isRunning());
        System.out.println("Title: " + gameRoom.getTitle());
        System.out.println("Owner: " + gameRoom.getOwner());
        System.out.println("Started: " + gameRoom.isRunning());
        gameRoom.start();
        assertTrue(gameRoom.isRunning());
        System.out.println("\nTitle: " + gameRoom.getTitle());
        System.out.println("Owner: " + gameRoom.getOwner());
        System.out.println("Started: " + gameRoom.isRunning());
    }

}