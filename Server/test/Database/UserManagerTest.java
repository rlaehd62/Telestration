package Database;

import MVP.DataPresenter;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class UserManagerTest
{
    private DataPresenter model = DataManager.getInstance();
    private final String ID = "rlaehd62";

    @Test
    void setState()
    {
        assertTrue(model.setState(ID, true));
        assertTrue(model.isOnline(ID));
    }
}