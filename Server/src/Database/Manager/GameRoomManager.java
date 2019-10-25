package Database.Manager;

import DTO.Request.Room.GameRoom;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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

    public GameRoom searchRoom(String owner)
    {
        return rooms.get(owner);
    }

    public boolean containsRoom(String owner)
    {
        return rooms.containsKey(owner);
    }

    public List<GameRoom> getRoomList()
    {
        return new ArrayList<GameRoom>(rooms.values());
    }
}
