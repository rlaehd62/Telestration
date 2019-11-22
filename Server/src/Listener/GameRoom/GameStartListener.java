package Listener.GameRoom;

import DTO.Notification.GameRoom.GameInfoNotification;
import DTO.Request.GameRoom.GameStartRequest;
import Game.GameRoom;
import Database.Manager.GameRoomManager;
import Database.Manager.WordPoolManager;
import Listener.ServerListener;
import Server.ChannelManager;
import Utility.GameLoop;
import com.google.common.eventbus.Subscribe;
import java.util.*;

public class GameStartListener extends ServerListener<GameStartRequest>
{
    private GameRoomManager gm = GameRoomManager.getInstance();
    private WordPoolManager wp = new WordPoolManager();

    @Subscribe
    public void handle(GameStartRequest message)
    {
        final String OWNER = message.getOwner();
        GameRoom room = gm.searchRoom(OWNER);
        if(!gm.containsRoom(OWNER) || room.isRunning()) return;

        room.start();
        String[] words = wp.getRandomWords(room.getUsers().size());
        List<String> users = new ArrayList<>(room.getUsers());
        for(int i = 0; i < words.length; i++) room.setSecretWord(users.get(i), words[i]);
        for(int i = 0; i < users.size(); i++)
        {
            final String ID = users.get(i);
            final String WORD = room.getWord(ID);
            GameInfoNotification notification = new GameInfoNotification(WORD, true);
            ChannelManager.sendBroadCast(new String[] { ID }, notification);
        }

        Timer timer = new Timer(room.getTitle());
        timer.schedule(new GameLoop(timer, room), 1000, 1000);
    }
}
