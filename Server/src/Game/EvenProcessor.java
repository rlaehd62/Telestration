package Game;

import DTO.Notification.GameRoom.GameInfoNotification;
import Database.GameDB;
import Server.ChannelManager;
import Util.SketchBook;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;

public class EvenProcessor implements GameProcessor
{
    public void checkAnswer(GameRoom room, Round round)
    {
        AtomicInteger count = new AtomicInteger();
        HashMap<String, SketchBook> result = round.getResult();
        History story = new History();
        story.round(round.getRoundNumber());

        result.keySet().stream()
                .filter(key -> !result.get(key).isPainter())
                .forEach(key ->
                {
                    SketchBook book = result.get(key);
                    String owner = book.getOwner();
                    String real = room.getWord(owner);
                    story.saveSketchbook(owner, book);
                    if(book.getSecretWord().equals(real))
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
            ChannelManager.getChannels().get(NEXT).writeAndFlush(notification);
        }
    }
}
