package Listener.GameRoom;

import DTO.Request.GameRoom.ReportRequest;
import Database.Manager.ReportManager;
import Listener.ServerListener;
import com.google.common.eventbus.Subscribe;
import io.netty.channel.ChannelHandlerContext;

import java.net.InetSocketAddress;
import java.net.SocketAddress;

public class ReportListener extends ServerListener<ReportRequest>
{
    private ReportManager rm = new ReportManager();

    @Subscribe
    public void handle(ReportRequest message)
    {
        ChannelHandlerContext ctx = message.getSender();
        InetSocketAddress socket = (InetSocketAddress) ctx.channel().remoteAddress();
        String IP = socket.getAddress().getHostAddress();
        rm.increaseCnt(IP);
    }
}
