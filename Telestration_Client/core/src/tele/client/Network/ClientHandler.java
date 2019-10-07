package tele.client.Network;

import DTO.Request.GamePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

public class ClientHandler extends SimpleChannelInboundHandler<GamePacket>
{
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, GamePacket gamePacket) throws Exception
    {

    }
}
