package Listener.GameRoom;

import DTO.Request.GameRoom.ChatRequest;
import DTO.Request.GameRoom.StartTimerRequest;
import DTO.Request.Room.GameRoom;
import DTO.Response.GameRoom.ChatResponse;
import Database.Manager.GameRoomManager;
import Listener.ServerListener;
import Server.ChannelManager;
import com.google.common.eventbus.Subscribe;

import java.util.Timer;
import java.util.TimerTask;

public class StartTimerListener extends ServerListener<StartTimerRequest>
{
    @Subscribe
    public void handle(StartTimerRequest message)
    {
        Timer timer = new Timer(message.getID() + "'s Room");
        timer.schedule(new TimerTask()
        {
            String owner = message.getID();
            int count = 0;
            public void run()
            {
                GameRoom room = GameRoomManager.getInstance().searchRoom(owner);
                ChatResponse response = new ChatResponse(new ChatRequest("Timer", owner, String.format("타이머 %d초", ++count)));
                ChannelManager.sendBroadCast(room.getUsers().stream().toArray(java.lang.String[]::new), response);
                if(count >= (message.getM() * 60) + message.getS()) timer.cancel();
            }
        }, 1000, 1000);
    }
}
