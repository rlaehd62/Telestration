package Util;

import DTO.Request.GamePacket;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.socket.DatagramPacket;
import io.netty.handler.codec.MessageToMessageDecoder;

import java.util.List;

// 수신한 UDP 패킷을 자동으로 GamePacket으로 변환해주는 역할을 한다.
public class GamePacketDecoder extends MessageToMessageDecoder<DatagramPacket>
{
    protected void decode(ChannelHandlerContext ctx, DatagramPacket msg, List<Object> out) throws Exception
    {
        GamePacket packet = SerializationUtil.deserialize(msg);
        out.add(packet);
    }
}
