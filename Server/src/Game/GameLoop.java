package Game;

import DTO.Notification.GameRoom.CurrentTimeNotification;
import DTO.Notification.GameRoom.SendSketchBookNotification;
import Database.GameDB;
import Database.Manager.GameRoomManager;
import Server.ChannelManager;

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
        checkValidation();

        Round round = room.getCurrentRound();
        if(Objects.isNull(round)) return;
        else if(!round.isExpired()) round.increaseTime();

        if(round.isExpired() && !isWaiting)
        {
            String[] users = room.getUsers().toArray(new String[1]);
            ChannelManager.sendBroadCast(users, new SendSketchBookNotification());
            isWaiting = true;
        } else if(round.isDone())
        {
            if(processor.isFinal(room, round))
            {
                processor.checkAnswer(room, round);
                processor.giveReward(room, room.history());
                room.switchRound();

                showResult(room);
                room.clearHistory();

                room.stop();
                timer.cancel();
            } else
            {
                processor.checkAnswer(room, round);
                processor.process(room);
                room.switchRound();

                Round NEW = new Round(round.getRoundNumber()+1, (round.getRoundNumber() <= 1) ? room.getTimeOut() : round.getMaxSeconds());
                NEW.setRoom(room);
                room.pushRound(NEW);
                isWaiting = false;
            }

        } else if(!isWaiting)
        {
            String[] users = room.getUsers().toArray(new String[1]);
            CurrentTimeNotification noti = new CurrentTimeNotification(round.getCurrentSeconds() / 60, round.getCurrentSeconds() % 60);
            noti.setMax(round.getMaxSeconds() / 60, round.getMaxSeconds() % 60);
            ChannelManager.sendBroadCast(users, noti);
        }
    }

    private void showResult(GameRoom room)
    {
        AtomicInteger cnt = new AtomicInteger(1);
        GameDB.getInstance().log("종료", room.getOwner() + "님의 방의 게임이 종료되었습니다.");
        room.history()
                .forEach(history ->
                {
                    int answer = history.getAnswers();
                    HashMap<String, Integer> temp = history.getAnswerCount();
                    GameDB.getInstance().log("[" + cnt.getAndIncrement() + " 라운드]", String.format("총 %d개 정답", answer));
                    temp.keySet().forEach(name -> GameDB.getInstance().log("결과", "[" + name + "] " + temp.get(name) + "회 정답!"));

                });
    }

    private boolean isValid()
    {
        final String OWNER = room.getOwner();
        return gm.containsRoom(OWNER) || room.isRunning() || room.getUsers().size() > 0;
    }

    private void checkValidation()
    {
        if(!isValid() || !room.isRunning())
        {
            room.clearHistory();
            room.switchRound();
            timer.cancel();
            timer.purge();
            System.out.println("종료 시도");
        }
    }
}
