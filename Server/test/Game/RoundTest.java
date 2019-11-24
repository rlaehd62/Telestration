package Game;

import Util.SketchBook;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class RoundTest
{
    private final String OWNER = "Root";
    private final String USER = "TESTER";
    private final String TITLE = "Hello World!";
    private final GameRoom room = new GameRoom(OWNER, TITLE);

    @Test
    public void init()
    {
        Round round = new Round(1, 30);
        round.setRoom(room);

        room.addUser(OWNER);
        room.addUser(USER);
        room.pushRound(round);

        Round getter = room.getCurrentRound();
        assertNotNull(getter);

        assertFalse(getter.isDone());
        System.out.println(getter.isDone());
        getter.setResult(OWNER, new SketchBook("A", "TEST"));
        assertFalse(getter.isDone());
        System.out.println(getter.isDone());
        getter.setResult(USER, new SketchBook("B","고구마"));
        assertTrue(getter.isDone());
        System.out.println(getter.isDone());

        for(String key : getter.getResult().keySet())
        {
            System.out.println("유저: " + key);
            System.out.println("단어: " + getter.getResult().get(key).getSecretWord() + " \n");
        }

        assertFalse(getter.isExpired());
        System.out.println(getter.isExpired());
        for(int i = 0; i < 30; i++) getter.increaseTime();
        assertTrue(getter.isExpired());
        System.out.println(getter.isExpired());

        System.out.println(room.getCurrentRound());
        room.switchRound();
        System.out.println(room.getCurrentRound());
        assertNull(room.getCurrentRound());
    }
}