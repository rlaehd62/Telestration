package Utility;

import DTO.Notification.GameRoom.CurrentTimeNotification;
import DTO.Notification.GameRoom.SendSketchBookNotification;
import DTO.Request.GameRoom.ChatRequest;
import DTO.Request.Room.GameRoom;
import DTO.Response.GameRoom.ChatResponse;
import Database.Manager.GameRoomManager;
import Server.ChannelManager;

import java.util.Timer;
import java.util.TimerTask;

public class GameLoop extends TimerTask
{
    private Timer timer;
    private GameRoom room;
    private GameRoomManager gm;

    private int MAX_SECOND;
    private int CUR_SECOND;

    public GameLoop(Timer timer, GameRoom room)
    {
        this.timer = timer;
        this.room = room;
        this.gm = GameRoomManager.getInstance();

        this.MAX_SECOND = room.getTimeOut();
        this.CUR_SECOND = 0;
    }

    public void run()
    {
        if(!isValid()) timer.cancel();
        else if(CUR_SECOND < MAX_SECOND)
        {
            CUR_SECOND++;
            ChatResponse response = new ChatResponse(new ChatRequest("Timer", room.getOwner(), String.format("%d초", CUR_SECOND)));
            CurrentTimeNotification notification = new CurrentTimeNotification(CUR_SECOND / 60, CUR_SECOND % 60);
            notification.setMax(MAX_SECOND / 60, MAX_SECOND % 60);

            String[] arr = room.getUsers().toArray(new String[1]);
            ChannelManager.sendBroadCast(arr, response);
        } else if(CUR_SECOND == MAX_SECOND)
        {
            ChannelManager.sendBroadCast(room.getUsers().toArray(new String[1]), new SendSketchBookNotification());
            CUR_SECOND = 0;
        }

        MAX_SECOND = room.getTimeOut();
    }

    private boolean isValid()
    {
        final String OWNER = room.getOwner();
        return gm.containsRoom(OWNER) || room.isRunning();
    }
}
