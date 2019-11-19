package Listener.GameRoom;

import DTO.Request.GameRoom.GameStartRequest;
import DTO.Request.Room.GameRoom;
import Database.Manager.GameRoomManager;
import Database.Manager.WordPoolManager;
import Listener.ServerListener;
import com.google.common.eventbus.Subscribe;

import java.util.ArrayList;
import java.util.List;

public class GameStartListener extends ServerListener<GameStartRequest>
{
    private GameRoomManager gm = GameRoomManager.getInstance();
    private WordPoolManager wp = new WordPoolManager();

    @Subscribe
    public void handle(GameStartRequest message)
    {
        final String OWNER = message.getOwner();
        GameRoom room = gm.searchRoom(OWNER);
        if(room == null || room.isRunning()) return;

        room.start();
        String[] words = wp.getRandomWords(room.getUsers().size());
        List<String> users = new ArrayList<>(room.getUsers());
        for(int i = 0; i < words.length; i++) room.setSecretWord(users.get(i), words[i]);
    }
}
