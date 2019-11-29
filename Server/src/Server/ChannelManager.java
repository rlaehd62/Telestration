package Server;

import DTO.Request.GamePacket;
import DTO.Response.GamePacketResponse;
import io.netty.channel.ChannelFuture;
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

    public static synchronized void sendBroadCast(String[] users, GamePacket packet)
    {
        for(String name : users)
        {
            ChannelHandlerContext ctx = channels.get(name);
            ChannelFuture future = ctx.writeAndFlush(packet);
            future.awaitUninterruptibly();
            if(!future.isSuccess()) ctx.writeAndFlush(packet);
        }
    }

    public static synchronized void sendBroadCast(String[] users, GamePacketResponse packet)
    {
        for(String name : users)
        {
            ChannelHandlerContext ctx = channels.get(name);
            ChannelFuture future = ctx.writeAndFlush(packet);
            future.awaitUninterruptibly();
            if(!future.isSuccess()) ctx.writeAndFlush(packet);
        }
    }

    public static synchronized void sendBroadCast(GamePacket packet)
    {
        channels.values().forEach(name ->
        {
            ChannelHandlerContext ctx = channels.get(name);
            ChannelFuture future = ctx.writeAndFlush(packet);
            future.awaitUninterruptibly();
            if(!future.isSuccess()) ctx.writeAndFlush(packet);
        });
    }

    public static synchronized void sendBroadCast(GamePacketResponse packet)
    {
        channels.values().forEach(name ->
        {
            ChannelHandlerContext ctx = channels.get(name);
            ChannelFuture future = ctx.writeAndFlush(packet);
            future.awaitUninterruptibly();
            if(!future.isSuccess()) ctx.writeAndFlush(packet);
        });
    }
}
