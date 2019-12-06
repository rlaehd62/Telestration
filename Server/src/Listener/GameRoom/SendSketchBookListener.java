package Listener.GameRoom;

import DTO.Request.GameRoom.SendSketchBookRequest;
import Game.GameRoom;
import DTO.Response.GameRoom.SketchBookResponse;
import Database.GameDB;
import Database.Manager.GameRoomManager;
import Game.Round;
import Listener.ServerListener;
import Server.ChannelManager;
import Util.SketchBook;
import com.google.common.eventbus.Subscribe;

import java.util.List;
import java.util.Objects;

public class SendSketchBookListener extends ServerListener<SendSketchBookRequest>
{
    private GameRoomManager gm = GameRoomManager.getInstance();

    @Subscribe
    public void handle(SendSketchBookRequest message)
    {
        System.out.println("그림 받음");
        final String ID = message.getID();
        final String OWNER = message.getOwner();
        if(!gm.containsRoom(OWNER) || !gm.containsUser(ID)) return;

        GameRoom room = gm.searchRoom(OWNER);
        Round round = room.getCurrentRound();
        SketchBook book = message.getSketchBook();
        System.out.println("그림? " + Objects.isNull(book.toImage()));
        round.setResult(ID, book);
    }
}
