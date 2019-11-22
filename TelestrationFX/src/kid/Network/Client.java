package kid.Network;

import DTO.Request.GamePacket;
import io.netty.channel.*;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import kid.Listener.GameRoom.ChatResponseListener;
import kid.Listener.GameRoom.GameNotificationListener;
import kid.Listener.GameRoom.SendSketchBookListener;
import kid.Listener.GameRoom.SketchBookResponseListener;
import kid.Listener.Login.LoginResponseListener;
import kid.Listener.WaitRoom.*;
import kid.TelestrationFX.MainFX;
import com.google.common.eventbus.EventBus;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.serialization.ClassResolvers;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
import javafx.application.Platform;

public class Client extends Thread
{
    private static Client ins = null;
    private boolean running;

    private EventLoopGroup worker;
    private Channel channel;
    private EventBus eventBus;

    private Client()
    {
        running = false;
        eventBus = new EventBus();
        eventBus.register(new LoginResponseListener());
        eventBus.register(new UserResponseListener());

        eventBus.register(new RoomListResponseListener());
        eventBus.register(new JoinRoomResponseListener());
        eventBus.register(new CreateRoomResponseListener());

        eventBus.register(new RoomResponseListener());
        eventBus.register(new ChatResponseListener());
        eventBus.register(new SketchBookResponseListener());

        eventBus.register(new GameNotificationListener());
        eventBus.register(new SendSketchBookListener());
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
                    .handler(initializer())
                    .option(ChannelOption.TCP_NODELAY, true)
                    .option(ChannelOption.SO_KEEPALIVE, true);

            ChannelFuture f = boot.connect(MainFX.IP, Integer.parseInt(MainFX.PORT)).sync();
            channel = f.channel();
        }catch (Exception e)
        {
            e.printStackTrace();
            worker.shutdownGracefully();
            Platform.exit();
        }
    }

    public void startServer()
    {
        if(!isRunning())
        {
            start();
            running = true;
        }
    }

    public void stopServer()
    {
        if(isRunning())
        {
            running = false;
            worker.shutdownGracefully();
            stop();
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
            try
            {
                ChannelFuture future = channel.writeAndFlush(packet);
                future.awaitUninterruptibly();
                System.out.println(future.isDone() + " | " + future.isSuccess());
                if(!future.isSuccess()) channel.writeAndFlush(packet);
            }
            catch (Exception e)
            { stopServer(); }
        }
    }

    public ChannelInitializer<SocketChannel> initializer()
    {
        return new ChannelInitializer<SocketChannel>()
        {
            protected void initChannel(SocketChannel ch) throws Exception
            {
                ChannelPipeline cp = ch.pipeline();
                cp.addLast("Logging", new LoggingHandler(LogLevel.DEBUG));
                cp.addLast("Encoder", new ObjectEncoder());
                cp.addLast("Decoder", new ObjectDecoder(ClassResolvers.cacheDisabled(getClass().getClassLoader())));
                cp.addLast("Handler", new ClientHandler(eventBus));
            }
        };
    }
}
