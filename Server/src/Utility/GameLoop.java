package Utility;

import DTO.Notification.GameRoom.SendSketchBookNotification;
import DTO.Request.GameRoom.ChatRequest;
import Game.GameRoom;
import DTO.Response.GameRoom.ChatResponse;
import Database.Manager.GameRoomManager;
import Game.Round;
import Server.ChannelManager;

import java.util.Objects;
import java.util.Timer;
import java.util.TimerTask;

public class GameLoop extends TimerTask
{
    private Timer timer;
    private GameRoom room;
    private GameRoomManager gm;
    private boolean isWaiting;

    public GameLoop(Timer timer, GameRoom room)
    {
        this.timer = timer;
        this.room = room;
        this.gm = GameRoomManager.getInstance();
        this.isWaiting = false;

        Round first = new Round(1, room.getTimeOut());
        first.setRoom(room);
        room.pushRound(first);
    }

    public void run()
    {
        Round round = room.getCurrentRound();
        if(Objects.isNull(round)) return;
        else if(!round.isExpired()) round.increaseTime();

        if(!isValid()) timer.cancel();
        else if(round.isExpired() && !isWaiting)
        {
            String[] users = room.getUsers().toArray(new String[1]);
            ChannelManager.sendBroadCast(users, new SendSketchBookNotification());
            isWaiting = true;
        } else if(round.isDone())
        {
            room.switchRound(); // 라운드 교체
            // 각종 조건 체크
            room.pushRound(new Round(round.getRoundNumber()+1, round.getMaxSeconds())); // 임시
            isWaiting = false;
        } else if(!isWaiting)
        {
            String[] users = room.getUsers().toArray(new String[1]);
            System.out.println("GameLoop: " + round);
            ChatResponse response = new ChatResponse(new ChatRequest("시스템", room.getOwner(), round.toString()));
            ChannelManager.sendBroadCast(users, response);
        }
    }

    private boolean isValid()
    {
        final String OWNER = room.getOwner();
        return gm.containsRoom(OWNER) || room.isRunning();
    }
}
