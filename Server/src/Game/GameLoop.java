package Game;

import DTO.Notification.GameRoom.CurrentTimeNotification;
import DTO.Notification.GameRoom.GameInfoNotification;
import DTO.Notification.GameRoom.SendSketchBookNotification;
import Database.GameDB;
import Database.Manager.GameRoomManager;
import Server.ChannelManager;
import Util.SketchBook;

import java.util.*;
import java.util.concurrent.atomic.AtomicInteger;

public class GameLoop extends TimerTask
{
    private Timer timer;
    private GameRoom room;
    private GameRoomManager gm;
    private GameProcessor processor;
    private boolean isWaiting;

    public GameLoop(Timer timer, GameRoom room)
    {
        this.timer = timer;
        this.room = room;
        this.gm = GameRoomManager.getInstance();
        this.isWaiting = false;
        this.processor = (room.getUsers().size() % 2 == 0) ? (new EvenProcessor()) : (new OddProcessor());

        Round first = new Round(1, (room.getUsers().size() % 2 == 0) ? room.getTimeOut() : 2);
        first.setRoom(room);
        room.clearHistory();
        room.pushRound(first);
    }

    public void run()
    {
        Round round = room.getCurrentRound();
        if(Objects.isNull(round)) return;
        else if(!round.isExpired()) round.increaseTime();

        if(!isValid())
        {
            room.stop();
            timer.cancel();
        } else if(round.isExpired() && !isWaiting)
        {
            String[] users = room.getUsers().toArray(new String[1]);
            ChannelManager.sendBroadCast(users, new SendSketchBookNotification());
            isWaiting = true;
        } else if(round.isDone())
        {
            if(processor.isFinal(room, round))
            {
                AtomicInteger cnt = new AtomicInteger(1);
                System.out.println(room.getOwner() + "의 방이 Final 도달");

                processor.checkAnswer(room, round);
                processor.process(room);

                System.out.println("< 게임 결과 >");
                room.switchRound();
                room.history()
                        .forEach(history ->
                        {
                            int answer = history.getAnswers();
                            HashMap<String, Integer> temp = history.getAnswerCount();
                            System.out.printf("[%d 라운드] 총 %d개 정답\n", cnt.getAndIncrement(), answer);
                            temp.keySet().forEach(name -> System.out.println("[" + name + "] " + temp.get(name) + "회 정답!"));

                        });

                room.clearHistory();
                room.stop();
                timer.cancel();
            } else
            {
                processor.checkAnswer(room, round);
                processor.process(room);
                room.switchRound();

                Round NEW = new Round(round.getRoundNumber()+1, (round.getRoundNumber() > 1) ? round.getMaxSeconds() : room.getTimeOut());
                NEW.setRoom(room);
                room.pushRound(NEW);
                isWaiting = false;
            }

        } else if(!isWaiting)
        {
            String[] users = room.getUsers().toArray(new String[1]);
            System.out.println("GameLoop: " + round);

            CurrentTimeNotification noti = new CurrentTimeNotification(round.getCurrentSeconds() / 60, round.getCurrentSeconds() % 60);
            noti.setMax(round.getMaxSeconds() / 60, round.getMaxSeconds() % 60);
            ChannelManager.sendBroadCast(users, noti);
        }
    }

    private boolean isValid()
    {
        final String OWNER = room.getOwner();
        return gm.containsRoom(OWNER) || room.isRunning() || room.getUsers().size() > 0;
    }
}
