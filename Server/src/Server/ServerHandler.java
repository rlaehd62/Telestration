package Server;

import Request.GamePacket;
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

    protected void channelRead0(ChannelHandlerContext ctx, GamePacket msg) throws Exception
    {
        msg.setSender(ctx);
        bus.post(msg);
    }
}
