package Game;

import DTO.Notification.GameRoom.GameInfoNotification;
import DTO.Notification.GameRoom.RewardNotification;
import Database.GameDB;
import Server.ChannelManager;
import Util.SketchBook;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public class OddProcessor implements GameProcessor
{
    public void checkAnswer(GameRoom room, Round round)
    {
        AtomicInteger count = new AtomicInteger();
        HashMap<String, SketchBook> result = round.getResult();
        History story = new History();
        story.round(round.getRoundNumber());
        result.forEach((story::saveSketchbook));

        if(story.getRound() <= 1)
        {
            result.keySet().forEach(name ->
            {
                story.answer(0).setAnswerCound(name, 0);
            });
            room.pushHistory(story);
            return;
        }

        result.keySet()
                .forEach(key ->
                {
                    SketchBook book = result.get(key);
                    String owner = book.getOwner();
                    String real = room.getWord(owner).replaceAll(" ", "");
                    String temp = book.getSecretWord().replaceAll(" ", "");

                    if(temp.equals(real))
                    {
                        story
                                .answer(count.incrementAndGet())
                                .setAnswerCound(key, 1);
                    }
                });
        room.pushHistory(story);
    }

    public void process(GameRoom room)
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
            notification.setOdd(true);
            ChannelManager.getChannels().get(NEXT).writeAndFlush(notification);
        }
    }

    public void rewardRounds(RewardNotification notification, GameRoom room, List<History> histories)
    {
        histories.stream()
                .filter(history -> history.getRound() > 2)
                .forEach(history ->
                {
                    Map<String, Integer> list = history.getAnswerCount();
                    list.forEach((ID, COUNT) ->
                    {
                        addEXP(ID, COUNT * 15);
                        levelUp(ID);
                        notification.setUser(ID, notification.getUser(ID) + (COUNT * 15));
                    });
                });
    }
}
