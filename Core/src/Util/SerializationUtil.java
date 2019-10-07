package Util;

import DTO.Request.GamePacket;
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
            return Unpooled.directBuffer();
        }

        catch (IOException e)
        {
            e.printStackTrace();
        }

        return null;
    }

    public static GamePacket deserialize(ByteBuf buf) throws IOException, ClassNotFoundException
    {
        byte[] data = new byte[buf.readableBytes()];
        for(int i = 0; i < data.length; i++) data[i] = buf.getByte(i);

        ByteArrayInputStream in = new ByteArrayInputStream(data);
        ObjectInputStream is = new ObjectInputStream(in);
        GamePacket packet = (GamePacket) is.readObject();
        return packet;
    }
}
