package Server;

import Listener.LoginListener;
import Swing.ServerController;
import Util.GamePacketDecoder;
import com.google.common.eventbus.EventBus;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoop;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetAddress;

public class Server extends Thread
{
    private static Server ins = null;
    private boolean running;
    private int PORT;

    private EventBus eventBus;
    private EventLoopGroup boss;
    private EventLoopGroup work;
    private ServerController controller;

    private Server()
    {
        running = false;
        PORT = 9999;

        eventBus = new EventBus();
        eventBus.register(new LoginListener());

    }

    public static Server getInstance()
    {
        return (ins != null) ? ins : (ins = new Server());
    }

    public void run()
    {
        boss = new NioEventLoopGroup(1);
        work = new NioEventLoopGroup();

        try
        {
            ServerBootstrap sb = new ServerBootstrap();
            sb.group(boss, work)
                    .channel(NioServerSocketChannel.class)
                    .childHandler(initializer());

            ChannelFuture channelFuture = sb.bind(PORT).sync();
            channelFuture.channel().closeFuture().sync();

        } catch (InterruptedException e)
        {
            stopServer();
        } finally
        {
            stopServer();
        }

    }

    private ChannelInitializer<SocketChannel> initializer()
    {
        return new ChannelInitializer<SocketChannel>()
        {
            protected void initChannel(SocketChannel ch) throws Exception
            {
                ChannelPipeline cp = ch.pipeline();
                cp.addLast(new GamePacketDecoder());
                cp.addLast(new ServerHandler(eventBus));
            }
        };
    }

    public boolean isRunning()
    {
        return running;
    }

    public void startServer()
    {
        if(!isRunning())
        {
            running = true;
            controller.log("중요", "서버가 시작되었습니다!");
            start();
        }
    }

    public void stopServer()
    {
        if(isRunning())
        {
            running = false;
            controller.log("중요", "서버가 종료되었습니다!");
            controller.log("경고", "서버를 재시작 하려면, 프로그램을 다시 시작해주세요.");
            work.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }

    public void setController(ServerController controller)
    {
        this.controller = controller;
    }
}
