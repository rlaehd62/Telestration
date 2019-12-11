package Game;

import DTO.Notification.GameRoom.GameInfoNotification;
import DTO.Notification.GameRoom.RewardNotification;
import Database.GameDB;
import Server.ChannelManager;
import Util.SketchBook;
import java.util.HashMap;
import java.util.List;
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
            result.keySet().forEach(name -> story.answer(0).setAnswerCound(name, 0));
            room.pushHistory(story);
            return;
        } else
        { check(room, story, result); }
    }

    public void process(GameRoom room)
    {
        final String OWNER = room.getOwner();
        final Round round = room.getCurrentRound();
        final HashMap<String, SketchBook> result = round.getResult();

        result.forEach((ID, book) ->
        {
            String NEXT = nextPlayer(ID, room);
            GameDB.getInstance().log("수신", ID + " → " + NEXT + " in " + OWNER + "'s ROOM");
            GameInfoNotification notification = new GameInfoNotification(book, book.getSecretWord(), !book.isPainter());
            notification.setOdd(true);
            ChannelManager.getChannels().get(NEXT).writeAndFlush(notification);
        });
    }

    public void rewardRounds(RewardNotification notification, GameRoom room, List<History> histories)
    {
        rewardRounds(true, notification, room, histories);
    }
}
