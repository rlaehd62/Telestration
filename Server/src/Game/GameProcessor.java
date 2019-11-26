package Game;

import DTO.Notification.GameRoom.GameInfoNotification;
import Database.GameDB;
import Server.ChannelManager;
import Util.SketchBook;

import java.util.HashMap;
import java.util.List;

public interface GameProcessor
{
    void checkAnswer(GameRoom room, Round round);
    void process(GameRoom room);

    default boolean isFinal(GameRoom room, Round round)
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

}
