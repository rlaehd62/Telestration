package Server;

import Listener.Room.CreateRoomListener;
import Listener.Account.LoginListener;
import Listener.Room.JoinRoomListener;
import Listener.Room.RoomListListener;
import Listener.User.UserInfoRequestListener;
import MVP.ServerPresenter;
import com.google.common.eventbus.EventBus;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;

public class Server extends Thread implements ServerPresenter.ServerModel
{
    private ServerPresenter presenter;
    private static Server ins = null;

    private boolean running;
    private int PORT;

    private EventBus eventBus;
    private EventLoopGroup boss;
    private EventLoopGroup work;

    private Server()
    {
        running = false;
        PORT = 9999;

        eventBus = new EventBus();
        eventBus.register(new LoginListener());
        eventBus.register(new CreateRoomListener());
        eventBus.register(new RoomListListener());
        eventBus.register(new UserInfoRequestListener());
        eventBus.register(new JoinRoomListener());
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
                    .childHandler(initializer())
                    .option(ChannelOption.SO_KEEPALIVE, true);

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
                cp.addLast(new ObjectEncoder());
                cp.addLast(new ObjectDecoder(ClassResolvers.cacheDisabled(null)));
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
            presenter.log("중요", "서버가 시작되었습니다!");
            start();
        }
    }

    public void stopServer()
    {
        if(isRunning())
        {
            running = false;
            presenter.log("중요", "서버가 종료되었습니다!");
            presenter.log("경고", "서버를 재시작 하려면, 프로그램을 다시 시작해주세요.");
            work.shutdownGracefully();
            boss.shutdownGracefully();
        }
    }

    public void setPresenter(ServerPresenter presenter)
    {
        this.presenter = presenter;
    }
}
