package Database;

import Request.LoginRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class DataManagerTest
{
    private DataManager DM = DataManager.getInstance();
    private String ID = "rlaehd62";


    @Test
    void isRegistered()
    {
        ServerDB.getInstance().initDB();
        DM.register(new LoginRequest(ID, "#12%31fd#!@"));
        assertEquals(DM.isRegistered(ID), true);
    }

    @Test
    void setOnline()
    {
        assertEquals(DM.isOnline(ID), false);
        DM.setOnline(ID, true);
        assertEquals(DM.isOnline(ID), true);
    }

    @Test
    void createRoom()
    {
        DM.createRoom("rlaehd62", "테스트", 1);
        assertEquals(DM.isOwner(ID), true);
        assertEquals(DM.hasRoom(1), true);
    }
}