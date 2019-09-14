package Listener;

import com.google.common.eventbus.Subscribe;

public abstract class ServerListener <T>
{
    @Subscribe
    public abstract void handle(T message);
}
