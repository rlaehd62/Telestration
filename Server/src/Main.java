import Database.ServerDB;
import Server.ServerManager;
import Database.GameDB;

public class Main
{
    public static void main(String[] args)
    {
        ServerDB.getInstance().initDB();
        ServerManager sm  = new ServerManager();
        GameDB dm = GameDB.getInstance();
        dm.setPresenter(sm);
    }
}
