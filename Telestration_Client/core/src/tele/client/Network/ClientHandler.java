package tele.client.Network;

import DTO.Request.GamePacket;
import com.google.common.eventbus.EventBus;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<Object>
{
    private EventBus bus;

    public ClientHandler(EventBus bus)
    {
        this.bus = bus;
    }

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object packet) throws Exception
    {
        System.out.println("Received Something");
        bus.post(packet);
    }
}
