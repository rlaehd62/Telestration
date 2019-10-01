package Database.Manager;

import Database.DataManager;
import MVP.DataPresenter;
import org.junit.jupiter.api.Test;

import javax.xml.crypto.Data;

import static org.junit.jupiter.api.Assertions.*;

class RoomManagerTest
{
    private DataPresenter.RoomModel model = new RoomManager();
    private final String ID = "rlaehd62";

    @Test
    void createRoom()
    {
        model.setPresenter(DataManager.getInstance());
        assertTrue(model.createRoom("테스트", ID, 1, 300));
        assertTrue(model.hasRoom(model.searchRoom(ID)));
        assertEquals(model.getOwner(model.searchRoom(ID)), ID);
        assertTrue(model.removeRoom(model.searchRoom(ID)));
    }
}