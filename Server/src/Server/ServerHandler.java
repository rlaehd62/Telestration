package Server;

import DTO.Request.GamePacket;
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

    // GamePacketDecoder를 사용하여 수신한 UDP 패킷을 자동으로 GamePacket으로 바꿔준다.
    protected void channelRead0(ChannelHandlerContext ctx, GamePacket msg) throws Exception
    {
        msg.setSender(ctx);
        bus.post(msg);
    }
}
