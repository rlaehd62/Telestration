package Database.Manager;

import DTO.Request.Room.GameRoom;
import com.sun.xml.internal.bind.v2.model.core.ID;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.atomic.AtomicBoolean;

public class GameRoomManager
{
    private static GameRoomManager ins = null;
    private HashMap<String, GameRoom> rooms;

    private GameRoomManager() { rooms = new HashMap<>(); }
    public static GameRoomManager getInstance()
    {
        return ins != null ? (ins) : (ins = new GameRoomManager());
    }

    public GameRoom CreateRoom(String owner, String title)
    {
        if(!rooms.containsKey(owner))
        {
            GameRoom gr = new GameRoom(owner, title);
            gr.addUser(owner);
            rooms.put(owner, gr);
            return gr;
        }

        else return null;
    }

    public boolean RemoveRoom(String owner)
    {
        if(!rooms.containsKey(owner)) return false;
        rooms.remove(owner);
        return true;
    }

    public void UpdateRoom()
    {
        List<GameRoom> backup = new ArrayList<>(rooms.values());
        rooms.clear();
        backup.forEach(room -> rooms.put(room.getOwner(), room));
    }

    public GameRoom searchRoom(String owner)
    {
        return rooms.get(owner);
    }

    public boolean containsRoom(String owner)
    {
        return rooms.containsKey(owner);
    }

    public boolean containsUser(String ID)
    {
        AtomicBoolean RESULT = new AtomicBoolean(false);
        for(GameRoom room : getRoomList())
        {
            room.getUsers().forEach(username ->
            {
                if(!RESULT.get()) RESULT.set(username.equals(ID));
            });
        }

        return RESULT.get();
    }

    public List<GameRoom> getRoomList()
    {
        return new ArrayList<GameRoom>(rooms.values());
    }
}
