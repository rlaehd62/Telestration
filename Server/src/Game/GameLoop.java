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
    private boolean isWaiting;

    public GameLoop(Timer timer, GameRoom room)
    {
        this.timer = timer;
        this.room = room;
        this.gm = GameRoomManager.getInstance();
        this.isWaiting = false;

        Round first = new Round(1, room.getTimeOut());
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
            if(isFinal(round))
            {
                AtomicInteger cnt = new AtomicInteger(1);
                System.out.println(room.getOwner() + "의 방이 Final 도달");
                checkAnswer(round);
                room.switchRound();
                System.out.println("< 게임 결과 >");
                room.history()
                        .forEach(history ->
                        {
                            int answer = history.getAnswers();
                            System.out.printf("[%d 라운드] 총 %d개 정답\n", cnt.getAndIncrement(), answer);
                        });
                room.clearHistory();

                room.stop();
                timer.cancel();
            } else
            {
                checkAnswer(round);
                process();
                room.switchRound(); // 라운드 교체

                Round NEW = new Round(round.getRoundNumber()+1, round.getMaxSeconds());
                NEW.setRoom(room);
                room.pushRound(NEW); // 임시
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

    private void checkAnswer(Round round)
    {
        AtomicInteger count = new AtomicInteger();
        HashMap<String, SketchBook> result = round.getResult();
        result.keySet().stream()
                .filter(key -> !result.get(key).isPainter())
                .forEach(key ->
                {
                    SketchBook book = result.get(key);
                    String owner = book.getOwner();
                    String real = room.getWord(owner);
                    if(book.getSecretWord().equals(real)) count.getAndIncrement();
                });
        History story = new History().answer(count.get());
        room.pushHistory(story);
    }

    private void process()
    {
        final String OWNER = room.getOwner();
        final Round round = room.getCurrentRound();
        final HashMap<String, SketchBook> result = round.getResult();

        for(String ID : result.keySet())
        {
            SketchBook book = result.get(ID);
            List<String> users = room.getUsers();
            int index = users.indexOf(ID);
            String NEXT = users.get(((index + 1) % users.size()));
            if(index < 0 || NEXT.equals("")) return;

            GameDB.getInstance().log("수신", ID + " → " + NEXT + " in " + OWNER + "'s ROOM");
            GameInfoNotification notification = new GameInfoNotification(book, book.getSecretWord(), !book.isPainter());
            ChannelManager.getChannels().get(NEXT).writeAndFlush(notification);
        }
    }

    private boolean isFinal(Round round)
    {
        boolean IS_VALID = round.getRoundNumber() > 1;
        final HashMap<String, SketchBook> result = round.getResult();
        final List<String> users = room.getUsers();

        if(!IS_VALID) return false;
        for(String ID : result.keySet())
        {
            int index = users.indexOf(ID);
            String NEXT = users.get((index + 1) % users.size());
            SketchBook book = result.get(ID);
            if(index >= 0 && NEXT.equals(book.getOwner())) return true;
        }

        return false;
    }

    private boolean isValid()
    {
        final String OWNER = room.getOwner();
        return gm.containsRoom(OWNER) || room.isRunning() || room.getUsers().size() > 0;
    }
}
