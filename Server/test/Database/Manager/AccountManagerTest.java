package Database.Manager;

import DTO.Request.Account.LoginRequest;
import DTO.Response.AccountResponse;
import Database.GameDB;
import Database.ServerDB;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountManagerTest
{
    private GameDB manager = GameDB.getInstance();
    private String ID = "rlaehd62";
    private String PW = "#5pt3$%f31";
    private String NEW_PW = "%421412%";

    @Test
    void insertAccount()
    {
        ServerDB.getInstance().initDB();
        manager.InsertAccount(new LoginRequest(ID, PW));
        AccountResponse response = manager.getAccount(ID);
        assertNotNull(response);
        assertEquals(response.getID(), ID);
        assertEquals(response.getPassword(), PW);

        manager.UpdateAccount(new LoginRequest(ID, NEW_PW));
        response = manager.getAccount(ID);
        assertNotNull(response);
        assertEquals(response.getID(), ID);
        assertEquals(response.getPassword(), NEW_PW);
    }
}