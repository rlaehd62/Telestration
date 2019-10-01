package Database;

import Database.Manager.AccountManager;
import MVP.DataPresenter;
import Request.LoginRequest;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class AccountManagerTest
{
    private DataPresenter.AccountModel model = new AccountManager();

    private final String ID = "rlaehd62";
    private final String PW = "#3d%$1sd";
    private final LoginRequest request = new LoginRequest(ID, PW);

    @Test
    void register()
    {
        ServerDB.getInstance().initDB();
        assertEquals(model.register(request), true);
        assertEquals(model.isRegistered(ID), true);
    }
}