package tele.client.Network;

import DTO.Request.GamePacket;
import Util.GamePacketDecoder;
import Util.GamePacketEncoder;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import tele.client.Main;

import java.net.InetSocketAddress;

public class Client extends Thread implements GameClient
{
    private static Client ins = null;
    private boolean running;

    private EventLoopGroup worker;
    private Channel channel;
    private InetSocketAddress remoteAddress;

    private Client()
    {
        running = false;
        this.remoteAddress = new InetSocketAddress(Main.IP, Integer.parseInt(Main.PORT));
    }
    public static Client getInstance()
    {
        return (ins != null) ? ins : (ins = new Client());
    }

    public void run()
    {
        try
        {
            worker = new NioEventLoopGroup();
            Bootstrap boot = new Bootstrap();
            boot.group(worker)
                    .channel(NioSocketChannel.class)
                    .handler(initializer());

            ChannelFuture f = boot.connect(Main.IP, Integer.parseInt(Main.PORT)).sync();
            channel = f.channel();
            f.channel().closeFuture().sync();
        } catch (Exception e)
        {
            worker.shutdownGracefully();
        } finally
        {
            worker.shutdownGracefully();
        }
    }

    public void startServer()
    {
        if(!isRunning())
        {
            this.start();
            running = true;
        }
    }

    public void stopServer()
    {
        if(isRunning())
        {
            running = false;
            worker.shutdownGracefully();
        }
    }

    public boolean isRunning()
    {
        return running;
    }

    public void send(GamePacket packet)
    {
        if(isRunning())
        {
            channel.writeAndFlush(packet);
        }
    }

    public ChannelInitializer<NioSocketChannel> initializer()
    {
        return new ChannelInitializer<NioSocketChannel>()
        {
            protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception
            {
                ChannelPipeline pipeline = nioSocketChannel.pipeline();
                pipeline.addLast(new ObjectEncoder());
                pipeline.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
                pipeline.addLast(new ClientHandler());
            }
        };
    }
}
