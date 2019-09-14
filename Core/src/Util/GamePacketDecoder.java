package Util;

import Request.GamePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class GamePacketDecoder extends MessageToMessageDecoder<DatagramPacket>
{
    protected void decode(ChannelHandlerContext ctx, DatagramPacket msg, List<Object> out) throws Exception
    {
        GamePacket packet = SerializationUtil.deserialize(msg);
        out.add(packet);
    }
}
