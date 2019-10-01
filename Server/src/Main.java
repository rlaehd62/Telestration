import Database.ServerDB;
import Server.ServerManager;
import Database.DataManager;

public class Main
{
    public static void main(String[] args)
    {
        ServerDB.getInstance().initDB();
        ServerManager sm  = new ServerManager();
        DataManager dm = DataManager.getInstance();
        dm.setPresenter(sm);
    }
}
