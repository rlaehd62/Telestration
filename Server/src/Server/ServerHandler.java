package Server;

import DTO.Request.GamePacket;
import DTO.Request.GameRoom.ExitRoomRequest;
import Database.Manager.GameRoomManager;
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
        if(ChannelManager.getChannels().size() <= 0) return;
        ChannelManager.getChannels().forEach((key, ct) ->
        {
            GameRoomManager gm = GameRoomManager.getInstance();
            if(ct.equals(ctx))
            {
                gm.getRoomList()
                        .stream()
                        .filter(room -> room.getUsers().contains(key))
                        .forEach(room ->
                        {
                            String OWNER = room.getOwner();
                            bus.post(new ExitRoomRequest(key, OWNER));
                        });

                System.out.println(key + "가 접속을 종료했습니다.");
                ChannelManager.getChannels().remove(key);
            }
        });
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
