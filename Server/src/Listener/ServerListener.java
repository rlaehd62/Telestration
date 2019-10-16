package Listener;

import com.google.common.eventbus.Subscribe;

public abstract class ServerListener <T>
{
    public abstract void handle(T message);
}
