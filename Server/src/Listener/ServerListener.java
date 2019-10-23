package Listener;

import Database.GameDB;
import com.google.common.eventbus.Subscribe;

public abstract class ServerListener <T>
{
    protected GameDB presenter;

    public ServerListener()
    {
        setPresenter(GameDB.getInstance());
    }

    public abstract void handle(T message);
    public final void setPresenter(GameDB presenter)
    {
        this.presenter = presenter;
    }
}
