package Database.Manager;

import Game.GameRoom;
import org.junit.jupiter.api.Test;

import java.io.File;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;

class WordPoolManagerTest
{

    @Test
    void addWord()
    {
        WordPoolManager wm = new WordPoolManager();
        File file = new File(System.getProperty("user.home") + "./System/words.txt");
        wm.init(file);
        System.out.println(Arrays.toString(wm.getWords(30)));
        System.out.println(Arrays.toString(wm.getRandomWords(5)));
        System.out.println(Arrays.toString(wm.getRandomWords(5)));
        System.out.println(Arrays.toString(wm.getRandomWords(5)));
        System.out.println(Arrays.toString(wm.getRandomWords(5)));
    }

    @Test
    void testUser()
    {
        WordPoolManager wm = new WordPoolManager();
        String[] words = wm.getRandomWords(10);
        GameRoom room = new GameRoom("TESTER", "TEST");
        room.addUser("TESTER");

        for(int i = 0; i < words.length; i++)
        {
            room.setSecretWord("TESTER", words[i]);
            assertNotNull(room.getWord("TESTER"));
            System.out.println("테스터: " + room.getWord("TESTER"));
        }

        room.removeSecretWord("TESTER");
        assertNull(room.getWord("TESTER"));
    }
}