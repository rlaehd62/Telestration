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
        DM.register(new LoginRequest(ID, "1234"));
        assertEquals(DM.isRegistered(ID), true);
    }

    @Test
    void setOnline()
    {
        assertEquals(DM.isOnline(ID), false);
        DM.setOnline(ID);
        assertEquals(DM.isOnline(ID), true);
    }
}