package Server;

import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;

public class ChannelManager
{
    private static HashMap<String, ChannelHandlerContext> channels;

    static
    {
        channels = new HashMap<>();
    }

    public static HashMap<String, ChannelHandlerContext> getChannels()
    {
        return channels;
    }
}
