import Server.ServerManager;
import Database.DataManager;

public class Main
{
    public static void main(String[] args)
    {
        ServerManager sm  = new ServerManager();
        DataManager dm = DataManager.getInstance();
        dm.setPresenter(sm);
    }
}
