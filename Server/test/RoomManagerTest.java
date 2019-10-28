import DTO.Request.Room.GameRoom;
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
        gameRoom.addUser("KID");
        gameRoom.addUser("root");

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

        assertTrue(gm.containsRoom("rlaehd62"));
        assertTrue(gm.containsUser("KID"));
        assertTrue(gm.containsUser("root"));

        gameRoom.removeUser("KID");
        assertFalse(gm.containsUser("KID"));

    }

}