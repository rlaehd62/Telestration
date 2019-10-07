package Server;

import DTO.Request.GamePacket;
import DTO.Response.AccountResponse;
import com.google.common.eventbus.EventBus;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ServerHandler extends SimpleChannelInboundHandler<GamePacket>
{
    private EventBus bus;

    public ServerHandler(EventBus bus)
    {
        this.bus = bus;
    }

    public void channelRegistered(ChannelHandlerContext ctx) throws Exception
    {
        System.out.println("뭐가 접속함");
    }

    protected void channelRead0(ChannelHandlerContext ctx, GamePacket msg) throws Exception
    {
        System.out.println("뭐가 들어옴");
        msg.setSender(ctx);
        bus.post(msg);
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        cause.printStackTrace();
    }
}
