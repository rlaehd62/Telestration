package Server;

import DTO.Request.GamePacket;
import com.google.common.eventbus.EventBus;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Map;

public class ServerHandler extends SimpleChannelInboundHandler<GamePacket>
{
    private EventBus bus;

    public ServerHandler(EventBus bus)
    {
        this.bus = bus;
    }

    public void channelUnregistered(ChannelHandlerContext ctx) throws Exception
    {
        System.out.println("뭐가 끊김");

        // 어떤 유저가 접속을 끊었는지 알 수 있음.
        // 유저가 접속 끊었을 때 룸 사람 없으면 삭제 하거나 다른 애한테 방장 넘겨야됨
        for(Map.Entry entry : ChannelManager.getChannels().entrySet())
        {
            if(entry.getValue().equals(ctx))
                System.out.println(entry.getKey());
        }
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
