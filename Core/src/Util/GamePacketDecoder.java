package Util;

import DTO.Request.GamePacket;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

public class GamePacketDecoder extends MessageToMessageDecoder<ByteBuf>
{
    protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception
    {
        GamePacket packet = SerializationUtil.deserialize(msg);
        out.add(packet);
    }
}
