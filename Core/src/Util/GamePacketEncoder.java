package Util;

import Request.GamePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.List;

// 송신하는 GamePacket을 자동으로 UDP 패킷으로 변환해주는 역할을 한다.
public class GamePacketEncoder extends MessageToMessageEncoder<GamePacket>
{
    protected void encode(ChannelHandlerContext ctx, GamePacket msg, List<Object> out) throws Exception
    {
        if(msg != null)
        {
            InetSocketAddress address = new InetSocketAddress(InetAddress.getLocalHost().getHostAddress(), 9999);
            ByteBuf byteBuf = SerializationUtil.serialize(msg);
            DatagramPacket packet = new DatagramPacket(byteBuf, address);
            out.add(packet);
        }
    }
}
