package tele.client.Network;

import DTO.Request.GamePacket;
import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.nio.NioSocketChannel;

public interface GameClient
{
    void startServer();
    void stopServer();
    boolean isRunning();
    void send(GamePacket packet);
    ChannelInitializer<NioSocketChannel> initializer();
}
