package kid.Network;

import com.google.common.eventbus.EventBus;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import javafx.application.Platform;

public class ClientHandler extends SimpleChannelInboundHandler<Object>
{
    private EventBus bus;

    public ClientHandler(EventBus bus)
    {
        this.bus = bus;
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception
    {
        Platform.exit();
    }

    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object packet) throws Exception
    {
        System.out.println("Received Something");
        bus.post(packet);
    }

    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception
    {
        cause.printStackTrace();
    }
}
