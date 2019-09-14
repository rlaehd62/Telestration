package Util;

import Request.GamePacket;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.socket.DatagramPacket;

import java.io.*;

public class SerializationUtil
{
    public static ByteBuf serialize(GamePacket packet)
    {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try(ObjectOutputStream oos = new ObjectOutputStream(bos))
        {
            oos.writeObject(packet);
            byte[] bytes = bos.toByteArray();
            return Unpooled.copiedBuffer(bytes);
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static GamePacket deserialize(DatagramPacket packet) throws IOException, ClassNotFoundException
    {
        ByteBuf buf = packet.content();
        byte[] data = new byte[buf.readableBytes()];
        buf.readBytes(data);

        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        return (GamePacket) is.readObject();
    }
}
