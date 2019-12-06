package Game;

import java.io.Serializable;
import java.util.*;

public class GameRoom implements Serializable
{
    private static final long serialVersionUID = -2663665186518595996L;
    private String title;
    private String owner;

    private List<String> users;
    private Queue<Round> roundQueue;
    private List<History> history;

    private int level_limit;
    private int timeout;
    private boolean running;

    private HashMap<String, String> words;

    public GameRoom(String owner, String title)
    {
        this(owner, title, 1, 60 * 5);
    }

    private GameRoom(String owner, String title, int level_limit, int timeout)
    {
        this.owner = owner;
        this.title = title;
        this.level_limit = level_limit;
        this.timeout = timeout;
        this.running = false;
        this.users = new ArrayList<>();
        this.words = new HashMap<>();
        this.roundQueue = new LinkedList<>();
        this.history = new ArrayList<>();
    }

    public void start()
    {
        this.running = true;
    }

    public void stop()
    {
        this.running = false;
    }

    public void pushRound(Round round)
    {
        roundQueue.offer(round);
    }

    public Round switchRound()
    {
        return roundQueue.poll();
    }

    public void pushHistory(History story)
    {
        this.history.add(story);
    }

    public List<History> history()
    {
        return history;
    }

    public void clearHistory()
    {
        history.clear();
    }

    public Round getCurrentRound()
    {
        return roundQueue.peek();
    }

    public void addUser(String name)
    {
        users.add(name);
    }

    public void removeUser(String name)
    {
        users.remove(name);
    }

    public List<String> getUsers()
    {
        return users;
    }

    public void changeTimeout(int minute, int second)
    {
        this.timeout = (60 * minute) + second;
    }

    public int getTimeOut()
    {
        return timeout;
    }

    public void setOwner(String owner)
    {
        this.owner = new String(owner);
    }

    public String getOwner()
    {
        return owner;
    }

    public void setTitle(String title)
    {
        this.title = title;
    }

    public String getTitle()
    {
        return title;
    }

    public void setLevelLimit(int level_limit)
    {
        this.level_limit = level_limit;
    }

    public int getLevelLimit()
    {
        return level_limit;
    }

    public boolean isRunning()
    {
        return running;
    }

    public void setSecretWord(String name, String word)
    {
        words.put(name, word);
    }

    public void removeSecretWord(String name)
    {
        words.remove(name);
    }

    public String getWord(String name)
    {
        return words.getOrDefault(name, null);
    }

    public boolean isEmpty()
    {
        return users.size() <= 0;
    }

    public boolean equals(Object obj)
    {
        GameRoom room = (GameRoom) obj;
        if(owner.equals(room.owner)) return true;
        return false;
    }

    public int hashCode()
    {
        return owner.hashCode();
    }
}
