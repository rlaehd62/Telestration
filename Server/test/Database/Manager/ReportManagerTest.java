package Database.Manager;

import Database.ServerDB;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ReportManagerTest
{
    private ServerDB DB = ServerDB.getInstance();

    @Test
    void registerIP()
    {
        final String IP = "192.168.1.10";
        ReportManager rm = new ReportManager();
        if(! rm.exists(IP)) rm.registerIP(IP);

        assertTrue(rm.exists(IP));

        assertEquals(rm.getCnt(IP), 2);
        System.out.println(rm.getCnt(IP));

        rm.increaseCnt(IP);

        assertEquals(rm.getCnt(IP), 3);
        System.out.println(rm.getCnt(IP));
    }
}