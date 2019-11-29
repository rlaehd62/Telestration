package Game;

import DTO.Notification.GameRoom.GameInfoNotification;
import DTO.Notification.GameRoom.RewardNotification;
import DTO.Request.Users.AddUserRequest;
import DTO.Response.User.UserResponse;
import Database.GameDB;
import Server.ChannelManager;
import Util.SketchBook;
import com.sun.xml.internal.bind.v2.model.core.ID;
import io.netty.channel.ChannelHandlerContext;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface GameProcessor
{
    void checkAnswer(GameRoom room, Round round);
    void process(GameRoom room);
    void rewardRounds(RewardNotification notification, GameRoom room, List<History> histories);
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

    default void giveReward(GameRoom room, List<History> histories)
    {
        GameDB db = GameDB.getInstance();
        RewardNotification notification = new RewardNotification();
        rewardSketchBooks(notification, room, histories);
        rewardRounds(notification, room, histories);

        String[] arr = room.getUsers().toArray(new String[1]);
        ChannelManager.sendBroadCast(arr, notification);
        for(String ID : arr)
        {
            ChannelHandlerContext ct = ChannelManager.getChannels().get(ID);
            ct.writeAndFlush(db.getUser(ID));
        }
    }

    default void rewardSketchBooks(RewardNotification notification, GameRoom room,  List<History> histories)
    {
        histories.get(histories.size()-1).getSketchbooks()
                .forEach((s, sketchBook) ->
                {
                    String CURR = sketchBook.getSecretWord();
                    String REAL = room.getWord(s);
                    boolean IS_VALID = REAL.equals(CURR);
                    notification.addReward(REAL, IS_VALID ? 100 : 0);

                    if(IS_VALID)
                    {
                        String[] arr = room.getUsers().toArray(new String[1]);
                        for(String ID : arr) addEXP(ID, notification.getReward(ID) + 100);
                        levelUp(arr);
                    }
                });
    }

    default void addEXP(String ID, int amount)
    {
        GameDB db = GameDB.getInstance();
        UserResponse info = db.getUser(ID);
        info.setExp(info.getExp() + amount);
        db.UpdateUser(new AddUserRequest(info));
    }

    default void levelUp(String ID)
    {
        GameDB db = GameDB.getInstance();
        UserResponse info = db.getUser(ID);
        if(info.getExp() < info.getMaxExp()) return;

        int ADD_LEV = 0;
        int EXP = info.getExp();
        int MAX_EXP = info.getMaxExp();
        int LEV = info.getLevel();

        ADD_LEV = (EXP / MAX_EXP);
        LEV += ADD_LEV;
        EXP = (EXP % MAX_EXP);
        MAX_EXP *= (ADD_LEV * 2);


        info.setLevel(LEV);
        info.setExp(EXP);
        info.setMaxExp(MAX_EXP);
        db.UpdateUser(new AddUserRequest(info));
    }

    default void levelUp(String[] users)
    {
        for(String ID : users) levelUp(ID);
    }

}
