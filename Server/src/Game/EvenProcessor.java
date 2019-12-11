package Game;

import DTO.Notification.GameRoom.GameInfoNotification;
import DTO.Notification.GameRoom.RewardNotification;
import Database.GameDB;
import Server.ChannelManager;
import Util.SketchBook;
import java.util.HashMap;
import java.util.List;


public class EvenProcessor implements GameProcessor
{
    public void checkAnswer(GameRoom room, Round round)
    {
        HashMap<String, SketchBook> result = round.getResult();
        History story = new History();
        story.round(round.getRoundNumber());
        result.forEach((story::saveSketchbook));
        check(room, story, result);
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
            ChannelManager.getChannels().get(NEXT).writeAndFlush(notification);
        });
    }

    public void rewardRounds(RewardNotification notification, GameRoom room, List<History> histories)
    {
        rewardRounds(false, notification, room, histories);
    }
}
